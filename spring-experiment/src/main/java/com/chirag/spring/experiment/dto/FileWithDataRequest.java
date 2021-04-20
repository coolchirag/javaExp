package com.chirag.spring.experiment.dto;

import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileWithDataRequest {

	private String data;
	
	private MultipartFile file;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public MultipartFile getFile() {
		return file;
	}

	public void setFile(MultipartFile file) {
		this.file = file;
	}
	
	
}
