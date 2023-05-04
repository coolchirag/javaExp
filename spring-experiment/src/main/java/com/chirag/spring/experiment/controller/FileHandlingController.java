package com.chirag.spring.experiment.controller;

import java.io.BufferedReader;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.zip.GZIPOutputStream;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.InputStreamResource;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.ResponseExtractor;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.multipart.MultipartFile;

import com.chirag.spring.experiment.dto.FileWithDataRequest;
import com.chirag.spring.experiment.dto.FileWithDataResponse;
import com.chirag.spring.experiment.dto.TestRequest;

@RestController
public class FileHandlingController {

	@PostMapping(value = "/uploadFileData", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
	public String uploadFile(FileWithDataRequest request) throws InterruptedException {
		System.out.println(request.getData());
		Thread.sleep(1*60*1000);
		MultipartFile file = request.getFile();
			System.out.println(request.getFile().getOriginalFilename());
		
		return "Done";
	}
	@PostMapping(value = "/uploadFileWIthJson")
	public String uploadFileWIthJson(@RequestPart("file") MultipartFile file1, @RequestPart("file2") MultipartFile file2, @RequestPart("data") TestRequest tempDto) {
		return "received";
	}
	
	@PostMapping(value = "/uploadFileDataPlainTest")
	public String uploadFilePlain(@Autowired HttpServletRequest request) {
		try {
		BufferedReader reader = request.getReader();
		int data;
		while((data = reader.read()) != -1) {
			System.out.println((char) data);
		}
		}catch (Exception e) {
			e.printStackTrace();
		}
		/*
		 * System.out.println(request.getData()); MultipartFile file =
		 * request.getFile();
		 * System.out.println(request.getFile().getOriginalFilename());
		 */
		
		return "Done";
	}

	@GetMapping("/download")
	public ResponseEntity<Resource> downloadFile() throws IOException {
		String filePath = "/home/chiragjivani/git/javaExp/spring-experiment/src/main/resources/test.txt";
		FileSystemResource fileResource = new FileSystemResource(filePath);
		File tempFIle = new File(filePath);
		InputStreamResource resource = new InputStreamResource(new FileInputStream(tempFIle));
		ResponseEntity<Resource> response = null;
		/*
		 * ResponseEntity<Resource> response =
		 * ResponseEntity.ok().contentLength(fileResource.contentLength())
		 * .contentType(MediaType.APPLICATION_OCTET_STREAM).body(fileResource);
		 */

		/* OR */

		final MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_OCTET_STREAM_VALUE);
		headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(tempFIle.length()));
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=content.txt");
		// headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; fileName=" +
		// fileResource.getFilename());
		response = new ResponseEntity<Resource>(resource, headers, HttpStatus.OK);
		return response;

	}
	
	@GetMapping(value = "/downloadWithData"/* , produces = MediaType.MULTIPART_FORM_DATA_VALUE */)
	public ResponseEntity<FileWithDataResponse> downloadWithData(HttpServletResponse httpResponse, HttpServletRequest httpRequest) throws IOException {
		//httpResponse.getWriter().wri
		//httpRequest.getReader().r
		httpRequest.getParameter("data");
		FileWithDataResponse response = new FileWithDataResponse();
		response.setData("TestData");
		//org.apache.tomcat.util.http.fileupload.FileItem fi = new DiskFileItem("file", "text", false, "/home/chiragjivani/git/javaExp/spring-experiment/src/main/resources/test.txt", 10, new File("/home/chiragjivani/git/javaExp/spring-experiment/src/main/resources/ctest.txt"));
		
		//org.apache.commons.fileupload.FileItem fi2 = new org.apache.commons.fileupload.disk.DiskFileItem("file", "text", false, "/home/chiragjivani/git/javaExp/spring-experiment/src/main/resources/test.txt", 10, new File("/home/chiragjivani/git/javaExp/spring-experiment/src/main/resources/ctest.txt"));
		//fi2.getOutputStream();
		/*CommonsMultipartFile cmf = new CommonsMultipartFile(fi2);
		response.setmFile(cmf);*/
		FileSystemResource fileResource = new FileSystemResource(
				"/home/chiragjivani/git/javaExp/spring-experiment/src/main/resources/test.txt");
		response.setFile(fileResource);
		
		final MultiValueMap<String, String> headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "multipart/mixed" /* "multipart/form-data"*/ /*MediaType.APPLICATION_OCTET_STREAM_VALUE*/);
		headers.add(HttpHeaders.CONTENT_LENGTH, Long.toString(fileResource.contentLength()));
		
		
		return new ResponseEntity<FileWithDataResponse>(response, headers, HttpStatus.OK);
	}
	
	@GetMapping("/internalDownloadFile")
	public String internalDownloadFile() throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		File loadedFile = restTemplate.execute(new URI("http://localhost:8080/download"), HttpMethod.GET, null, new ResponseExtractor<File>() {

			@Override
			public File extractData(ClientHttpResponse response) throws IOException {
				response.getBody();
				File file = File.createTempFile("temp", ".txt");
				//FileUtils.copyInputStreamToFile(response.getBody(), file);
				
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
	
	@GetMapping("/internalUploadFilesWithJson")
	public String internalUploadFilesWIthJson() throws RestClientException, URISyntaxException {
		RestTemplate restTemplate = new RestTemplate();
		
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("data", new TestRequest());
		formData.add("file", new FileSystemResource("/home/chiragj/git/javaExp/spring-experiment/src/main/resources/test.txt"));
		formData.add("file2", new FileSystemResource("/home/chiragj/git/javaExp/spring-experiment/src/main/resources/test.txt"));
		
		ResponseEntity<String> response = restTemplate.exchange(new URI("http://localhost:8080/uploadFileWIthJson"), HttpMethod.POST, new HttpEntity<MultiValueMap>(formData, headers), new ParameterizedTypeReference<String>() {
		});
		return "Done : " + response.getBody();
	}
	
	@GetMapping("/internalUploadFileDataWithCompression")
	public String internalUploadFileDataWithCompression() throws RestClientException, URISyntaxException, IOException {
		RestTemplate restTemplate = new RestTemplate();
		
		final FileSystemResource fsr = new FileSystemResource("/home/chiragj/git/javaExp/spring-experiment/src/main/resources/test.txt");
		final ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		GZIPOutputStream gzipOutputStream = new GZIPOutputStream(byteArrayOutputStream);
		//gzipOutputStream.wri
		
		final MultiValueMap<String, String> headers = new LinkedMultiValueMap<String, String>();
		headers.add(HttpHeaders.CONTENT_TYPE, MediaType.MULTIPART_FORM_DATA_VALUE);
		
		MultiValueMap<String, Object> formData = new LinkedMultiValueMap<String, Object>();
		formData.add("data", "TestData");
		
		//formData.add("file", );
		
		ResponseEntity<String> response = restTemplate.exchange(new URI("http://localhost:8080/uploadFileData"), HttpMethod.POST, new HttpEntity<MultiValueMap>(formData, headers), new ParameterizedTypeReference<String>() {
		});
		return "Done : " + response.getBody();
	}
}
