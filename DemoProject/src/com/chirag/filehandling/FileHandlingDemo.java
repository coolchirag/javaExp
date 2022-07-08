package com.chirag.filehandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;

public class FileHandlingDemo {

	public static void main(String[] args) throws IOException {
		final String filePath = "/home/chiragjivani/temp/test_install.sh";
		File file = new File(filePath);
		System.out.println(new Date(file.lastModified()));
		
		BasicFileAttributes readAttributes = Files.readAttributes(file.toPath(), BasicFileAttributes.class);
		System.out.println(readAttributes.creationTime());
	}
}
