package com.chirag.spring.experiment.service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

import com.ezdi.kpmg.utility.rest.template.CustomRestTemplate;
import com.ezdi.kpmg.utility.rest.template.RestTemplateUtility;

@Service
public class TestService {

	@Autowired
    private RestTemplateUtility restUtil;

	
	public String performOcrRequest(Integer clientId, Integer projectId, String filePath, String licenseToken, String ocrEngineName) {
		String url = "https://intcodingplatformocrservice.healthcarenlp.com/client/" + clientId + "/project/" + projectId + "/ocr";
		UriComponentsBuilder builder = UriComponentsBuilder.fromUriString(url);
		URI uri = builder.build().encode().toUri();
		final MultiValueMap<String, Object> multipartRequest = new LinkedMultiValueMap<>();
		multipartRequest.add("file", new FileSystemResource(filePath));
		multipartRequest.add("ocrEngineName", multipartRequest);
		
		HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.MULTIPART_FORM_DATA);
        headers.add("license-token", licenseToken);
        HttpEntity entity = new HttpEntity(multipartRequest, headers);
        CustomRestTemplate customRestTemplate = restUtil.getRestTemplateWOSecurity();
        customRestTemplate.setAllowSelfSignedSSLCertificate(true);
        customRestTemplate.setAllowCompressRequest(false);
        ResponseEntity<Object> response2 = customRestTemplate.exchange(uri, HttpMethod.POST, entity, new ParameterizedTypeReference<Object>() {
		});
        return "Done";
	}
}