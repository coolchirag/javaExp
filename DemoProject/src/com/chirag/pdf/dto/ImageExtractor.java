package com.chirag.pdf.dto;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.pdfbox.contentstream.PDFStreamEngine;
import org.apache.pdfbox.contentstream.operator.DrawObject;
import org.apache.pdfbox.contentstream.operator.Operator;
import org.apache.pdfbox.contentstream.operator.state.Concatenate;
import org.apache.pdfbox.contentstream.operator.state.Restore;
import org.apache.pdfbox.contentstream.operator.state.Save;
import org.apache.pdfbox.contentstream.operator.state.SetGraphicsStateParameters;
import org.apache.pdfbox.contentstream.operator.state.SetMatrix;
import org.apache.pdfbox.cos.COSBase;
import org.apache.pdfbox.cos.COSName;
import org.apache.pdfbox.pdmodel.graphics.PDXObject;
import org.apache.pdfbox.pdmodel.graphics.form.PDFormXObject;
import org.apache.pdfbox.pdmodel.graphics.image.PDImageXObject;
import org.apache.pdfbox.util.Matrix;


public class ImageExtractor extends PDFStreamEngine{
	
	public double imagesArea;
	public List <ImageDetail> imageDetails;
	
	public ImageExtractor() {
		super();
		
		addOperator(new Concatenate());
        addOperator(new DrawObject());
        addOperator(new SetGraphicsStateParameters());
        addOperator(new Save());
        addOperator(new Restore());
        addOperator(new SetMatrix());
        
        imagesArea = 0f;
        imageDetails = new ArrayList<>();
	}
	
	@Override protected void processOperator(Operator operator, List<COSBase> operands) throws IOException {
		String operation = operator.getName();
		
		if("Do".equals(operation))
		{
			COSName objectName = (COSName) operands.get( 0 );
			
            // get the PDF object
            PDXObject xobject = getResources().getXObject( objectName );
            
            // check if the object is an image object
            if( xobject instanceof PDImageXObject)
            {
            	
            	PDImageXObject image = (PDImageXObject)xobject;
  
                Matrix ctmNew = getGraphicsState().getCurrentTransformationMatrix();
                Double imageXScale = new Double(ctmNew.getScalingFactorX() );
                Double imageYScale = new Double(ctmNew.getScalingFactorY() );
               
                Double xPPI = new Double(image.getWidth() ) / (imageXScale/72d);
                Double yPPI = new Double(image.getHeight()) / (imageYScale/72d);
                
                Double imageArea = (imageXScale * imageYScale);
                imagesArea += imageArea;
                
//                Float [] info = {ctmNew.getTranslateX(), ctmNew.getTranslateY(), imageXScale, imageYScale, xPPI, yPPI};
//                imgInfo.add(info);
                ImageDetail imageDetail = new ImageDetail();
                
                imageDetail.setxPPI(xPPI);
                imageDetail.setxPPI(yPPI);
                imageDetail.setAreaInPercentage(imageArea);
                
                imageDetails.add(imageDetail);
            }
            else if(xobject instanceof PDFormXObject)
            {
                PDFormXObject form = (PDFormXObject)xobject;
                showForm(form);
//                System.out.println(form.getFormType() + " something");
            }
		}
		else
        {
            super.processOperator( operator, operands);
        }
	}
}
