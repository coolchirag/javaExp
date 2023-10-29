package com.chirag.pdf;

import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedMap;
import java.util.TreeMap;

import org.apache.pdfbox.cos.COSArray;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;
import org.apache.pdfbox.pdmodel.PDResources;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;

import com.chirag.pdf.dto.ImageDetail;
import com.chirag.pdf.dto.ImageExtractor;
import com.chirag.pdf.dto.ParserOutput;
import com.chirag.pdf.dto.ParserOutput.PageDimensionUnit;
import com.chirag.pdf.dto.ParserOutput.ParserPageOutput;
import com.chirag.pdf.dto.TextExtractor;
import com.chirag.pdf.dto.Word;


public class ApachePDFBoxDemo {

	public static void main(String[] args) {
		try {
			ParserOutput parse = parse(new File("/home/cj/Desktop/prod-doc-issue/Sentara_46_783154_20231021113002682_request.pdf"), 50d);
			System.out.println("==========================Done=============");
			System.out.println(parse);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		//splitPDFPerPage();
		//findImageInPDF();
	}
	
	public static ParserOutput parse(File file, Double threshold) throws Exception{
		
		ParserOutput parserOutput = null;
		List <ParserPageOutput> parserPageOutputs = new ArrayList<>();
		PDDocument document = null;
		
		try {
			document = PDDocument.load(file);
			
			//Should be given as parameter in function
			Integer pageNumber = 0;
			parserOutput = new ParserOutput();
			
			ImageExtractor imageExtractor = new ImageExtractor();
			// Zero based index
			PDPage page = document.getPage(pageNumber);
			
			imageExtractor.processPage(page);
    		
    		double pageWidth = page.getMediaBox().getWidth();
        	double pageHeight = page.getMediaBox().getHeight();
        	
        	double pageArea = pageWidth * pageHeight;
        	
        	double percentOccupied = ( imageExtractor.imagesArea / pageArea )*100;
        	ParserPageOutput parserPageOutput = new ParserPageOutput();
        	
        	parserPageOutput.setPageNumber(pageNumber + 1);
        	parserPageOutput.setPageWidth(pageWidth/72d);
        	parserPageOutput.setPageHeight(pageHeight/72d);
        	parserPageOutput.setPageDimensionUnit(PageDimensionUnit.INCH);
        	
        	for(ImageDetail img: imageExtractor.imageDetails) {
        		img.areaInPercentage /= pageArea;
        		img.areaInPercentage *= 100d;
        	}
        	parserPageOutput.setImageDetails(imageExtractor.imageDetails);
        	
        	
        	if(percentOccupied > threshold) {
        		parserPageOutput.setParsedSuccessfully(false);
        		parserPageOutputs.add(parserPageOutput);
        		parserOutput.setParserPageOutputs(parserPageOutputs);
        		
        		return parserOutput;
        	}
        	
        	TextExtractor textExtractor = new TextExtractor();
        	textExtractor.setSortByPosition(true);
        	Writer dummy = new OutputStreamWriter(new ByteArrayOutputStream());
        	textExtractor.writeText(document, dummy);
        	System.out.println("==========================Done----1----------=============");
        	// Creating text for complete page
        	SortedMap<Double, List <String> > curMap = new TreeMap<Double, List<String>>();
        	SortedMap<Double, List <Double> > curXCoords = new TreeMap<>();
        	
        	for(int i = 0; i < textExtractor.words.size(); i++) {
	    		Word word = textExtractor.words.get(i);
	    		
	    		double bottomY = word.getBoundingBox().getBottomLeftY();
	    		double bottomX = word.getBoundingBox().getBottomLeftX();
	    		double curSpaceWidth = textExtractor.spaceWidth.get(i);
	    		
	    		
	    		// Adding pipes for table
	    		if(curMap.containsKey(bottomY)) {
	    			Double prevX = curXCoords.get(bottomY).get(curXCoords.get(bottomY).size() - 1);
	    			Double diff = (bottomX - prevX)/curSpaceWidth;
	    			
	    			if(diff > 5d) {
	    				curMap.get(bottomY).add("|" + word.getText());
	    			}
	    			else {
	    				curMap.get(bottomY).add(" " + word.getText());	        				
	    			}
	    			curXCoords.get(bottomY).add( word.getBoundingBox().getBottomRightX() );
	    		}
	    		else {
	    			List <String> curLineList = new ArrayList<>();
	    			curLineList.add(word.getText()); 
	    			curMap.put(bottomY, curLineList);
	    			
	    			List <Double> xCoordList = new ArrayList<>();
	    			xCoordList.add(word.getBoundingBox().getBottomRightX());
	    			curXCoords.put(bottomY, xCoordList);
	    		}
	    	}
	    	
	    	String pageText = "";
	    	
	    	for(Double key: curMap.keySet()) {
	    		for(String s: curMap.get(key)) {
	    			pageText += s;
	    		}
	    		pageText += "\n";
	    	}
	    	
	    	parserPageOutput.setText(pageText);
	    	parserPageOutput.setWords(textExtractor.words);
	    	parserPageOutput.setParsedSuccessfully(true);
	    	
	    	parserPageOutputs.add(parserPageOutput);
	    	parserOutput.setParserPageOutputs(parserPageOutputs);
		}
		catch(Exception e) {
			System.out.println("Error occured during getting OCR result from pdfParser utlity, error message : " + e.getMessage());
			throw e;
		}
		finally {
			if(document != null)document.close();
		}
		
		
		
		return parserOutput;
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
