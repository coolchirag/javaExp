package com.chirag.spring.experiment.controller;

import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.time.Duration;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.azure.core.credential.TokenRequestContext;
import com.azure.core.util.IterableStream;
import com.azure.identity.ManagedIdentityCredential;
import com.azure.identity.ManagedIdentityCredentialBuilder;
import com.azure.messaging.servicebus.ServiceBusClientBuilder;
import com.azure.messaging.servicebus.ServiceBusReceivedMessage;
import com.azure.messaging.servicebus.ServiceBusReceiverClient;
import com.chirag.spring.experiment.dto.FileWithDataRequest;
import com.chirag.spring.experiment.dto.TestRequest;
import com.chirag.spring.experiment.service.TestService;

@RestController
public class TestController {
	
	@Autowired
	private FileWithDataRequest request1;
	
	@Autowired
	private TestRequest request2;
	
	@Autowired
	private TestService testService;
	
	/*
	 * @Value("${trust.store}") private Resource trustStore;
	 * 
	 * @Value("${trust.store.password}") private String trustStorePassword;
	 */
	@GetMapping("/test")
	public String getData() {
		String url = "https://localhost:8082/health";
		RestTemplate rt = new RestTemplate(getRequestFacctoryForSSL());
		ResponseEntity<Map> responseEntity = rt.exchange(url, HttpMethod.GET	, null, Map.class);
		System.out.println(responseEntity.getBody());
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
