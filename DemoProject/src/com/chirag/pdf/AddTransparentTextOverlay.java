package com.chirag.pdf;

import java.io.File;
import java.io.IOException;
import java.util.List;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.graphics.color.PDColor;
import org.apache.pdfbox.pdmodel.graphics.color.PDDeviceRGB;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotationTextMarkup;
import org.apache.pdfbox.pdmodel.interactive.annotation.PDAnnotation;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

public class AddTransparentTextOverlay {
    public static void main(String[] args) {
        try {
            // Load the existing PDF document
            PDDocument document = PDDocument.load(new File("D:/data/prod/specialchar-issue/singlepage_trasparent_3.pdf"));
            PDPage page = document.getPage(0);
            List<PDAnnotation> annotations = page.getAnnotations();
            PDAnnotation firstAnnoation = annotations.get(0);
            //firstAnnoation.setReadOnly(true);
           // firstAnnoation.setLocked(true);
            //firstAnnoation.setLockedContents(true);
           //firstAnnoation.setHidden(true);
           //firstAnnoation.setInvisible(true);
           //PDColor transparentColor2 = new PDColor(new float[]{0, 255, 0, 0}, PDDeviceRGB.INSTANCE);
           //firstAnnoation.setColor(transparentColor2);
            //annotations.remove(1);
            //page.setAnnotations(null);
            // Create a transparent text annotation
            PDAnnotationTextMarkup annotation = new PDAnnotationTextMarkup(PDAnnotationTextMarkup.SUB_TYPE_HIGHLIGHT);
            annotation.setRectangle(new PDRectangle(612, 508, 796, 600)); // Specify position and size
            annotation.setReadOnly(true); // Make annotation non-selectable
            //annotation.setOpacity(0.0f); // Set opacity to fully transparent
            PDColor transparentColor = new PDColor(new float[]{0, 255, 0, 0}, PDDeviceRGB.INSTANCE);
            annotation.setInteriorColor(transparentColor);
            annotation.setLocked(true);
            annotation.setLockedContents(true);
            annotation.setHidden(true);
            annotation.setInvisible(true);
            // Add the annotation to the page
           // page.getAnnotations().add(annotation);
            document.getCurrentAccessPermission().setReadOnly();
            document.getCurrentAccessPermission().setCanExtractContent(false);
            document.getCurrentAccessPermission().setCanModify(false);
            document.getCurrentAccessPermission().setCanModifyAnnotations(false);

            // Add text to the annotation
            PDPageContentStream contentStream = new PDPageContentStream(document, page, PDPageContentStream.AppendMode.APPEND, true);
            contentStream.beginText();
            contentStream.setFont(PDType1Font.TIMES_ROMAN, 12);
            contentStream.newLineAtOffset(100, 80); // Adjust position
            contentStream.showText("Your transparent text overlay");
            contentStream.endText();
            contentStream.close();

            // Save the modified document
            document.save(new File("D:/data/prod/specialchar-issue/singlepage_trasparent_4.pdf"));
            document.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
