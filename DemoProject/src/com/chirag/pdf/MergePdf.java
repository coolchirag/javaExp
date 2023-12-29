package com.chirag.pdf;

import java.io.File;
import java.io.IOException;
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
		for(int i = 1;i<10;i++) {
		File originalPdfFile = new File("D:/temp/pdf-merging/582_Pages_2.pdf");
		PDDocument document = PDDocument.load(originalPdfFile);
		
		File tempModifiedPageFile = new File("D:/temp/pdf-merging/ScannedText3.pdf");
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
		document.save(new File("D:/temp/pdf-merging/582_Pages_2.pdf"));
		
		modifiedDocument.close();
		//tempModifiedPageFile.delete();
		
		
		document.close();
		
		
		}
		System.out.println("DOne : "+(System.currentTimeMillis()-startTime));
	}
}
