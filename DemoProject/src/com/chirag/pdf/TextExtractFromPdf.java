package com.chirag.pdf;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.file.Files;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import com.chirag.pdf.util.TextExtractor;

public class TextExtractFromPdf {
	
	public static void main(String[] args) throws Exception {
		File originalPdfFile = new File("D:/data/prod/specialchar-issue/modified.pdf");
		byte[] bytearray = Files.readAllBytes(originalPdfFile.toPath());
		ByteArrayInputStream bi = new ByteArrayInputStream(bytearray);
		PDDocument document = PDDocument.load(bi);
		TextExtractor ts = new TextExtractor();
		File modifiedPdfFile = new File("D:/data/prod/specialchar-issue/singlepage_m.txt");
		Writer dummy = new OutputStreamWriter(new FileOutputStream(modifiedPdfFile));
		ts.writeText(document, dummy);
		System.out.println(dummy.toString());
		dummy.close();
		
	}
}
