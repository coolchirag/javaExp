package com.chirag.filehandling;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.stream.Stream;

public class FolderHandling {

	public static void main(String[] args) throws IOException {
		File folder = new File("/home/chiragjivani/folder_demo/test_1/1");
		if(folder.exists()) {
			Stream<Path> list = Files.list(folder.toPath());
			System.out.println("Count : "+list.count());
			
		} 
		if(folder.exists()) {
			System.out.println("Folder exists : "+folder.delete());
		} else {
			System.out.println("Folder not exists : "+folder.mkdirs());
		}
	}
}
