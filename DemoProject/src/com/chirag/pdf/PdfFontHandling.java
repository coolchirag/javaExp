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

import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.common.PDStream;
import org.apache.pdfbox.pdmodel.font.PDFont;

public class PdfFontHandling {

	public static void main(String[] args) throws IOException {
		//Integer pageNum = 4;
		long startTime = System.currentTimeMillis();
		File originalPdfFile = new File("D:/data/Encoding_Issue.pdf");
		PDDocument document = PDDocument.load(originalPdfFile);
		PDPage page = document.getPage(0);
		PDResources resources = page.getResources();
		Iterable<COSName> fontNames = resources.getFontNames();
		
		COSName r15Name = null;
		for(COSName name : fontNames) {
			r15Name = name;
		}
		PDFont r15Font = resources.getFont(r15Name);
		File secondPdfFile = new File("D:/data/Sample_2.pdf");
		PDDocument secondDocument = PDDocument.load(secondPdfFile);
		PDPage page2 = secondDocument.getPage(0);
		Iterable<COSName> fontNames2 = page2.getResources().getFontNames();
		PDFont font=null;
		for(COSName name :  fontNames2) {
			if(name.getName().equalsIgnoreCase("F1")) {
				font = page2.getResources().getFont(name);
			}
			
		}
		
		
		PDResources newResource = new PDResources();
		newResource.put(r15Name, font);
		page.setResources(newResource);
		File modifiedPdfFile = new File("D:/data/Encoding_Issue_2.pdf");
		document.save(modifiedPdfFile);
		document.close();
		
		System.out.println("DOne : "+(System.currentTimeMillis()-startTime));
	}
}
