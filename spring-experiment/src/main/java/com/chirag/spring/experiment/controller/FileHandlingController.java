package com.chirag.spring.experiment.controller;

import java.io.File;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

import org.apache.commons.io.FileUtils;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import com.chirag.spring.experiment.dto.FileWithDataRequest;

@RestController
public class FileHandlingController {

	@PostMapping(name = "/uploadFileData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(FileWithDataRequest request) {
		System.out.println(request.getData());
			System.out.println(request.getFile().getOriginalFilename());
		
		return "Done";
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile() throws IOException {
		FileSystemResource fileResource = new FileSystemResource(
				"/home/chiragj/git/javaExp/spring-experiment/src/main/resources/test.txt");

		ResponseEntity<Resource> response = ResponseEntity.ok().contentLength(fileResource.contentLength())
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileResource);

		/* OR */

		final MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(fileResource.contentLength()));
		// headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" +
		// fileResource.getFilename());
		response = new ResponseEntity<Resource>(fileResource, headers, HttpStatus.OK);
		return response;

	}
	
	@GetMapping("/internalDownloadFile")
	public String internalDownloadFile() throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		File loadedFile = restTemplate.execute(new URI("http://localhost:8080/download"), HttpMethod.GET, null, new ResponseExtractor<File>() {

			@Override
			public File extractData(ClientHttpResponse response) throws IOException {
				response.getBody();
				File file = File.createTempFile("temp", ".txt");
				FileUtils.copyInputStreamToFile(response.getBody(), file);
				
				return file;
			}
		});
		return "Done : " + loadedFile.getAbsolutePath();
	}
	
	@GetMapping("/internalUploadFileData")
	public String internalUploadFileData() throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("data", "TestData");
		formData.add("file", new FileSystemResource("/home/chiragj/git/javaExp/spring-experiment/src/main/resources/test.txt"));
		
		ResponseEntity<String> response = restTemplate.exchange(new URI("http://localhost:8080/uploadFileData"), HttpMethod.POST, new HttpEntity<MultiValueMap>(formData, headers), new ParameterizedTypeReference<String>() {
		});
		return "Done : " + response.getBody();
	}
}
