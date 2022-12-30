package com.chirag.spring.experiment.dto;

import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public class FileWithDataResponse {

	private String data;
	
	private Resource file;
	
	private MultipartFile mFile;

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}

	public Resource getFile() {
		return file;
	}

	public void setFile(Resource file) {
		this.file = file;
	}

	public MultipartFile getmFile() {
		return mFile;
	}

	public void setmFile(MultipartFile mFile) {
		this.mFile = mFile;
	}
	
	
}
