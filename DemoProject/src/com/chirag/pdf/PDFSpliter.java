package com.chirag.pdf;

import java.io.File;
import java.io.IOException;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;

public class PDFSpliter {

	public static void main(String[] args) {
		long currentTimeMillis = System.currentTimeMillis();
		try {
			// Load the PDF document
			PDDocument document = PDDocument.load(new File("/home/cj/temp/pdf-test/larger_ihh_file.pdf"));

			// Get the total number of pages
			int totalPages = document.getNumberOfPages();

			// Split the PDF into individual pages
			for (int i = 0; i < totalPages; i++) {
				// Create a new document for each page
				PDDocument newDocument = new PDDocument();

				// Get the current page
				PDPage page = document.getPage(i);

				// Add the page to the new document
				newDocument.addPage(page);

				// Save the new document with the page number as the filename
				newDocument.save("output_" + (i + 1) + ".pdf");

				// Close the new document
				newDocument.close();
			}

			// Close the original document
			document.close();
			System.out.println("DOne : "+(System.currentTimeMillis()-currentTimeMillis));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
