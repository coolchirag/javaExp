package com.chirag.pdf;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

public class ApachePDFBoxDemo {

	public static void main(String[] args) {
		splitPDFPerPage();
		//findImageInPDF();
	}
	
	private static void splitPDFPerPage() {
		long currentTimeMillis = System.currentTimeMillis();
		try {
			// Load the PDF document
			PDDocument document = PDDocument.load(new File("/home/cj/temp/pdf-test/larger_ihh_file.pdf"));
			long ptime = System.currentTimeMillis();
			System.out.println(" t1 : "+(ptime- currentTimeMillis));
			// Get the total number of pages
			int totalPages = document.getNumberOfPages();
			ptime = System.currentTimeMillis();
			System.out.println(" t2 : "+(ptime- currentTimeMillis));
			PDPageTree pages = document.getPages();
			ptime = System.currentTimeMillis();
			System.out.println(" t3 : "+(ptime- currentTimeMillis));
			int index = 1;
			for(PDPage page : pages) {
				
				PDDocument newDocument = new PDDocument();
				newDocument.addPage(page);

				// Save the new document with the page number as the filename
				newDocument.save("second_output_" + (index++) + ".pdf");
				// Close the new document
				newDocument.close();
				
				System.out.println("file time for page :  "+index+" : "+(System.currentTimeMillis()-ptime));
				ptime = System.currentTimeMillis();
			}

			// Split the PDF into individual pages
			for (int i = 0; i < (totalPages-totalPages); i++) {
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
	
	private static void findImageInPDF() {
		try {
            PDDocument document = PDDocument.load(new File("/home/cj/temp/pdf-test/larger_ihh_file.pdf"));

            int pageNum = 0;
            for (PDPage page : document.getPages()) {
                PDResources resources = page.getResources();

                for (COSName name : resources.getXObjectNames()) {
                	System.out.println(name);
                    if (resources.isImageXObject(name)) {
                        PDImageXObject image = (PDImageXObject) resources.getXObject(name);
                        COSArray decode = image.getDecode();
                        System.out.println(decode);
                        BufferedImage image2 = image.getImage();
                        // Save the image to a file with the page number and image number
                        InputStream createInputStream = image.createInputStream();
                        File f = new File("output_"+pageNum+".jpg");
                        FileWriter fw = new FileWriter(f);
                        int c;
                        while((c = createInputStream.read()) != -1) {
                        	fw.write(c);
                        }
                        fw.close();
                    }
                }

                pageNum++;
            }

            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
	}
}
