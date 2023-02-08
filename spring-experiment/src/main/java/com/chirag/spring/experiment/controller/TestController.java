package com.chirag.spring.experiment.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.chirag.spring.experiment.config.PropertyConfig;
import com.chirag.spring.experiment.dto.FileWithDataRequest;
import com.chirag.spring.experiment.dto.TestRequest;
import com.chirag.spring.experiment.service.TestService;

@RestController
public class TestController {
	
	//@Autowired
	private FileWithDataRequest request1;
	
	//@Autowired
	private TestRequest request2;
	
	//@Autowired
	private TestService testService;
	
	//@Autowired
   // private RestTemplateUtility restUtil;
	
	//@Autowired
	private PropertyConfig pc;
	
	//@Value("${local.folder.path2}")
	public String localPath;
	
	
	
	public String getLocalPath() {
		return localPath;
	}

	public void setLocalPath(String localPath) {
		this.localPath = localPath;
	}

	@GetMapping("/t")
	public String getTestData() {
		return "Hi : "+localPath+" : "+pc.getStr1();
	}
	
	/*
	 * @GetMapping(value = "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	 * public Flux<String> getTestDataFlux() { Flux<Long> interval =
	 * Flux.interval(Duration.ofSeconds(10)); Flux<String> response =
	 * interval.map(sequence -> "Flux - "+sequence.toString()); return response; }
	 * 
	 * @GetMapping(value = "/fluxData", produces =
	 * MediaType.TEXT_EVENT_STREAM_VALUE) public Flux<String> getTestDataFluxData()
	 * throws JsonProcessingException { ObjectMapper objMapper = new ObjectMapper();
	 * List<String> datas = new ArrayList<>(); Flux<String> lowerDataStr =
	 * Flux.fromIterable(datas).doOnNext(String::toLowerCase); Flux<String> response
	 * = Flux.just(objMapper.writeValueAsString(new TempDto("data1", 1l)),
	 * objMapper.writeValueAsString(new TempDto("data2", 2l))); return response; }
	 */
	
	/*
	 * @Value("${trust.store}") private Resource trustStore;
	 * 
	 * @Value("${trust.store.password}") private String trustStorePassword;
	 */
	@GetMapping("/test")
	public String getData() {
		try {
		String url = "https://intkpmgsaaslicenseservice.healthcarenlp.com/client/KPMG/license/token:generate?apiName=API-ICD10CM-TEXT&requestId=API-ICD10CM-TEXT";
		RestTemplate rt = new RestTemplate();
		HttpHeaders headers = new HttpHeaders();
		headers.add("x-subscription-key", "kpmg-subkey1"); //For saas license service
		//headers.add("Accept", "application/json");
		headers.add("Accept", "*/*");
		/*
		 * rt.setErrorHandler(new ResponseErrorHandler() {
		 * 
		 * @Override public boolean hasError(ClientHttpResponse response) throws
		 * IOException {
		 * 
		 * return true; }
		 * 
		 * @Override public void handleError(ClientHttpResponse response) throws
		 * IOException { System.out.println(response);
		 * 
		 * } });
		 */
		/*
		 * CustomRestTemplate customRestTemplate = restUtil.getRestTemplateWOSecurity();
		 * customRestTemplate.setAllowSelfSignedSSLCertificate(true);
		 * ResponseEntity<Map> responseEntity = customRestTemplate.exchange(url,
		 * HttpMethod.POST, new HttpEntity<>(headers), Map.class);
		 * System.out.println(responseEntity.getBody());
		 */
		} catch (HttpClientErrorException e) {
			URI uri = null;
			try {
				uri = new URI("http://localhost/testurl");
			} catch (URISyntaxException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			String error = "HttpClientErrorException occured for url : "+uri+", method : "+HttpMethod.GET+" : "+e.getMessage()+" : "+e.getCause()+" : "+e.getResponseBodyAsString();
			throw e;
		}
		
		return "hi";
	}
	
	@PostMapping("/testList")
	public String loadDataList(@RequestBody(required = false) List<TestRequest> data) {
		return "hi";
	}
	
	@PostMapping("/test")
	public String loadData(@RequestBody(required = false) TestRequest data) {
		return "hi";
	}
	
	@GetMapping("/testRest")
	public String testRest() {
		return testService.performOcrRequest(1, 1, "/home/chiragjivani/data/kpmg/2020_doc.pdf", "test1"	, "AZURE_COMPUTERVISION");
	}
	
	@GetMapping("/testThread")
	public String testThread() {
		for(int i = 0; i<10 ;i++) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						//testService.getServiceBusReceiverClient();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					
				}
			});
			t.start();
		}
		return "Done";
	}
	
	@GetMapping("/testQueue/{threadCount}")
	public ResponseEntity<String> testQueue(@PathVariable("threadCount") Integer threadCount, @RequestParam(value = "queueName") String queueName) {
		String response = "ThreadCount : " + threadCount+", queueName : "+queueName;
		for(int i=0;i<threadCount;i++) {
			Thread t = new Thread(new Runnable() {
				
				@Override
				public void run() {
					try {
						receiveMessage(queueName);
						
					} catch (Exception e) {
						System.out.println("Tempc error : "+e.getMessage());
						e.printStackTrace();
					}
				}
			});
			t.start();
		}
		return new ResponseEntity<String>(response, HttpStatus.OK);
	}
	
	private void receiveMessage(String queueName) {}
}
