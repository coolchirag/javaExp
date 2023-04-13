package com.chirag.spring.experiment.controller;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.net.ssl.SSLContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import com.azure.core.credential.TokenRequestContext;
import com.azure.core.util.IterableStream;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.chirag.spring.experiment.dto.FileWithDataRequest;
import com.chirag.spring.experiment.dto.TempDto;
import com.chirag.spring.experiment.dto.TestRequest;
import com.chirag.spring.experiment.service.TestService;
import com.ezdi.kpmg.utility.rest.template.CustomRestTemplate;
import com.ezdi.kpmg.utility.rest.template.RestTemplateUtility;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import reactor.core.publisher.Flux;

@RestController
public class TestController {
	
	@Autowired
	private FileWithDataRequest request1;
	
	@Autowired
	private TestRequest request2;
	
	@Autowired
	private TestService testService;
	
	@Autowired
    private RestTemplateUtility restUtil;
	
	@GetMapping("/t")
	public String getTestData() {
		return "Hi";
	}
	
	@GetMapping(value =  "/flux", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getTestDataFlux() {
		Flux<Long> interval = Flux.interval(Duration.ofSeconds(10));
		Flux<String> response = interval.map(sequence -> "Flux - "+sequence.toString());
		return response;
	}
	
	@GetMapping(value =  "/fluxData", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
	public Flux<String> getTestDataFluxData() throws JsonProcessingException {
		ObjectMapper objMapper = new ObjectMapper();
		List<String> datas = new ArrayList<>();
		Flux<String> lowerDataStr = Flux.fromIterable(datas).doOnNext(String::toLowerCase);
		Flux<String> response = Flux.just(objMapper.writeValueAsString(new TempDto("data1", 1l)), objMapper.writeValueAsString(new TempDto("data2", 2l)));
		return response;
	}
	
	/*
	 * @Value("${trust.store}") private Resource trustStore;
	 * 
	 * @Value("${trust.store.password}") private String trustStorePassword;
	 */
	@GetMapping("/test")
	public String getData() {
		try {
		String url = "https://intkpmgsaaslicenseservice.healthcarenlp.com/client/KPMG/license/token:generate?apiName=API-ICD10CM-TEXT&requestId=API-ICD10CM-TEXT";
		RestTemplate rt = new RestTemplate(getRequestFacctoryForSSL());
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
		CustomRestTemplate customRestTemplate = restUtil.getRestTemplateWOSecurity();
		customRestTemplate.setAllowSelfSignedSSLCertificate(true);
		ResponseEntity<Map> responseEntity = customRestTemplate.exchange(url, HttpMethod.POST, new HttpEntity<>(headers), Map.class);
		System.out.println(responseEntity.getBody());
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
	
	private HttpComponentsClientHttpRequestFactory getRequestFacctoryForSSL() {
		SSLContext sslContext;
		try {
				sslContext = new SSLContextBuilder()
					      .loadTrustMaterial(new TrustSelfSignedStrategy())
					      .build();
			
		} catch (KeyManagementException | NoSuchAlgorithmException | KeyStoreException e) {
			throw new RuntimeException(e.getMessage());
		}
			    SSLConnectionSocketFactory socketFactory = new SSLConnectionSocketFactory(sslContext, NoopHostnameVerifier.INSTANCE);
			    HttpClient httpClient = HttpClients.custom()
			      .setSSLSocketFactory(socketFactory)
			      .build();
			   return new HttpComponentsClientHttpRequestFactory(httpClient);
	}
	private static final String[] IP_HEADERS = {
	        "X-Forwarded-For",
	        "Proxy-Client-IP",
	        "WL-Proxy-Client-IP",
	        "HTTP_X_FORWARDED_FOR",
	        "HTTP_X_FORWARDED",
	        "HTTP_X_CLUSTER_CLIENT_IP",
	        "HTTP_CLIENT_IP",
	        "HTTP_FORWARDED_FOR",
	        "HTTP_FORWARDED",
	        "HTTP_VIA",
	        "REMOTE_ADDR"

	        // you can add more matching headers here ...
	    };
	
	@GetMapping("/ipadd")
	public String getIp(HttpServletRequest request) {
		Enumeration<String> headerNames = request.getHeaderNames();
		String respone = "";
		Set<String> headerNameSet = new HashSet<>();
		headerNameSet.addAll(Arrays.asList(IP_HEADERS));
		while(headerNames.hasMoreElements()) {
			headerNameSet.add(headerNames.nextElement());
		}
		
		for (String header: headerNameSet) {
            String value = request.getHeader(header);
            respone = respone + header+" : "+value+"\n"; 
        }
        return respone;
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
						testService.getServiceBusReceiverClient();
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
	
	private void receiveMessage(String queueName) {
		ServiceBusReceiverClient client = getReceiverClient(queueName);
		IterableStream<ServiceBusReceivedMessage> receiveMessages = client.receiveMessages(1, Duration.ofSeconds(5));
		
        for (ServiceBusReceivedMessage message : receiveMessages) {
        	System.out.println("Received msg : "+message.getBody().toString());
            
        }
        client.close();
	}
	private ServiceBusReceiverClient getReceiverClient(String queueName) {
		//if(!allowCacheClient || client == null) {
		String managedIdentityClientId = "c5065f48-3d48-4afa-a4be-acc2069b116e";
		String fullyQualifiedNameSpace = "int-coding-platform-service-bus.servicebus.usgovcloudapi.net";
		String[] scops = {"https://storage.microsoft.com/.default", "https://storage.azure.com/.default", "https://storage.azure.com/", "https://storage.azure.us/.default", "https://storage.azure.us/", "https://ossrdbms-aad.database.usgovcloudapi.net"};
		for(int i = 0; i<scops.length; i++) {
			String scop = scops[i];
			try {
				
				ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().clientId(managedIdentityClientId).build();
				TokenRequestContext ctx = new TokenRequestContext().addScopes(scop);
				System.out.println("-----------------Token : "+ scop + " : "+managedIdentityCredential.getToken(ctx).block().getToken());
			} catch (Exception e) {
				System.out.println("Error ocuured for scope : "+scop);
			}
		}
		ManagedIdentityCredential managedIdentityCredential = new ManagedIdentityCredentialBuilder().clientId(managedIdentityClientId).build();
		TokenRequestContext ctx = new TokenRequestContext().addScopes("https://storage.microsoft.com/.default");
		System.out.println("-----------------Token : "+managedIdentityCredential.getToken(ctx).block().getToken());
		ServiceBusReceiverClient client = new ServiceBusClientBuilder()
		.credential(fullyQualifiedNameSpace, managedIdentityCredential)
		.receiver().queueName(queueName)
        .buildClient();
		//}
		return client;
	}
}
