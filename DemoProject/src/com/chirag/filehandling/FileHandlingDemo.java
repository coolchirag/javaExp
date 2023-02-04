package com.chirag.filehandling;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.CharBuffer;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

import org.apache.commons.io.FilenameUtils;

public class FileHandlingDemo {

	public static void main(String[] args) throws IOException {
		long t1 = System.currentTimeMillis();
		File newFile = new File("/home/chiragjivani/temp/temp_test_new_3.txt");
		System.out.println(FilenameUtils.getName("/home/chiragjivani/temp/temp_test_new_3.txt"));
		if(!newFile.exists()) {
			newFile.createNewFile();
		}
		System.out.println("Done");
		final String filePath = "/home/chiragjivani/temp/temp_test.txt";
		File f = new File(filePath);
		System.out.println(f.getAbsolutePath());
		System.out.println(f.getCanonicalPath());
		System.out.println(f.getName());
		System.out.println(f.getPath());
		
		final String fileWritePath = "/home/chiragjivani/temp/temp_test3.txt";
		long t2 = System.currentTimeMillis();
		System.out.println("1 : "+(t2-t1));
		
		int count = 0;
		while(count<1) { 
			long t3 = System.currentTimeMillis();
			System.out.println("2 : "+(t3-t2));
		FileReader fr = new FileReader(filePath);
		FileWriter fw = new FileWriter(fileWritePath, false);
		long t4 = System.currentTimeMillis();
		System.out.println("3 : "+(t4-t3));
		int data;
		char[] dataArray = new char[909];  
		while((data = fr.read(dataArray)) != -1) {
			//fw.append((char)data);
			fw.write(dataArray,0,data);
			//System.out.println("data : " + data);
			//System.out.println(dataArray);
			//System.out.println("--");
		}
		//System.out.println("-------------"+data);
		//System.out.println(dataArray);
		//fw.write(dataArray);
		long t5 = System.currentTimeMillis();
		System.out.println("4 : "+(t5-t4));
		
		fw.close();
		fr.close();
		long t6 = System.currentTimeMillis();
		System.out.println("5 : "+(t6-t5));
		
		count++;
		System.out.println(count);
		}
		
		/*
		 * fw.write("Hello1"); fw.write("Hello2"); fw.write('\n'); fw.write("Hello3");
		 * fw.flush(); fw.write("Hello4"); fw.close();
		 */
		
		/*
		 * File file = new File(filePath); System.out.println(new
		 * Date(file.lastModified()));
		 * 
		 * BasicFileAttributes readAttributes = Files.readAttributes(file.toPath(),
		 * BasicFileAttributes.class);
		 * 
		 * System.out.println(readAttributes.creationTime());
		 */
	}
}
