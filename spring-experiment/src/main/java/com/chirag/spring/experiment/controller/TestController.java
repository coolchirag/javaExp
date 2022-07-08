package com.chirag.spring.experiment.controller;

import java.io.IOException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;

import org.apache.http.client.HttpClient;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.SSLContextBuilder;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

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
	@PostMapping("/test")
	public String loadData(@RequestBody(required = false) List<TestRequest> data) {
		return "hi";
	}
	
	@GetMapping("/testRest")
	public String testRest() {
		return testService.performOcrRequest(1, 1, "/home/chiragjivani/data/kpmg/2020_doc.pdf", "test1"	, "AZURE_COMPUTERVISION");
	}
}
