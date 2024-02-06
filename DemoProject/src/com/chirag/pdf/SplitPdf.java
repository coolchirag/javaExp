package com.chirag.pdf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;

public class SplitPdf {

	public static void main(String[] args) throws IOException {
		
		String filePath ="D:\\temp\\pdf-spliting\\49410.pdf";
		String ourputPath = "D:\\temp\\pdf-spliting\\49410_2_out";
		PDDocument document = PDDocument.load(new File(filePath));
		//PDPageTree pages = document.getPages();
		Splitter splitter = new Splitter();
		List<PDDocument> pages = splitter.split(document);
		int pageIndex = 1;
		for(PDDocument page : pages) {
			//PDDocument d = new PDDocument();
			//d.addPage(page);
		page.save(new File(ourputPath+"_"+pageIndex+".pdf"));
		page.close();
			pageIndex++;
			if(pageIndex>11) {
				break;
			}
		}
	}
}
