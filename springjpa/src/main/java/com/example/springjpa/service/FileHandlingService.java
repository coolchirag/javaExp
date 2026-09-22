package com.example.springjpa.service;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

@Service
public class FileHandlingService {

	public void loadFile() {
		File file = new File("D:\\temp\\large pdf\\1_GB_sample_pdf_file.pdf");
		//write a code to print file size in MB
		long fileSize = file.length();
		double fileSizeMB = (double) fileSize / (1024 * 1024);
		System.out.println("File size: " + fileSizeMB + " MB");
		
		File file2 = new File("D:\\temp\\large pdf\\1_GB_sample_pdf_file_"+System.currentTimeMillis()+".pdf");
		//Transfer file from file to file2
		try {
		InputStream inputStream = new FileInputStream(file);
		OutputStream outputStream = new FileOutputStream(file2);
		byte[] buffer = new byte[1024];
		int bytesRead;
		while ((bytesRead = inputStream.read(buffer)) != -1) {
			outputStream.write(buffer, 0, bytesRead);
		}
		//inputStream.close();
		//outputStream.close();
		
		
			Thread.sleep(10000);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
