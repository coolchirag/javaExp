package com.chirag.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.common.PDStream;

public class MergePdf {

	public static void main(String[] args) throws IOException {
		//Integer pageNum = 4;
		long startTime = System.currentTimeMillis();
		File originalPdfFile = new File("D:/data/prod/largedocformerging/single/original-doc-1-50.pdf");
		byte[] bytearray = Files.readAllBytes(originalPdfFile.toPath());
		for(int i = 1;i<10;i++) {
		ByteArrayInputStream bi = new ByteArrayInputStream(bytearray);
		PDDocument document = PDDocument.load(bi);
		
		
		
		File tempModifiedPageFile = new File("D:/data/prod/largedocformerging/original-doc_100_page.pdf");
		PDDocument modifiedDocument = PDDocument.load(tempModifiedPageFile);
		PDPage page = document.getPage(i-1);
		document.getPages().insertAfter(modifiedDocument.getPage(0), page);
		document.removePage(page);
		//document.addPage(modifiedDocument.getPage(0));
		
		//PDPage page = document.getPage(pageNum-1);
		//page.setContents(listOfPdStream);
		Runtime ru = Runtime.getRuntime();
		long totalMemory = ru.totalMemory();
		long freeMemory = ru.freeMemory();
		System.out.println("TotalMemory : "+totalMemory+" Free memory : "+freeMemory+" used : "+(totalMemory-freeMemory));
		ByteArrayOutputStream bo = new ByteArrayOutputStream();
		//document.save(new File("D:/temp/pdf-merging/582_Pages_2.pdf"));
		document.save(bo);
		bytearray = bo.toByteArray();
		modifiedDocument.close();
		//tempModifiedPageFile.delete();
		
		
		document.close();
		}
		File modifiedPdfFile = new File("D:/data/prod/largedocformerging/single/original-doc-1-50_modified.pdf");
		OutputStream os = new FileOutputStream(modifiedPdfFile);
		os.write(bytearray);
		os.close();
		System.out.println("DOne : "+(System.currentTimeMillis()-startTime));
	}
}
