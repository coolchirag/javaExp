package com.chirag.pdf.dto;

import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class TextExtractor extends PDFTextStripper{
	public List <Word> words;
	public List <Double> spaceWidth;
	
	public TextExtractor() throws Exception {
//		super();
		
		words = new ArrayList<>();
		spaceWidth = new ArrayList<>();
	}
	
@Override protected void writeString(String string, List<TextPosition> textPositions) {
		
		String wordSeparator = this.getWordSeparator();
		List <TextPosition> wordInfo = new ArrayList<>();
		
		for(TextPosition text: textPositions) {
			String str = text.getUnicode();
			
			if(wordSeparator.equals(str)) {
				if(!wordInfo.isEmpty()) {
					this.createWord(wordInfo);
					wordInfo.clear();
				}
			}
			else {
				wordInfo.add(text);
			}
		}
		
		if(!wordInfo.isEmpty()) {
			this.createWord(wordInfo);
			wordInfo.clear();
		}
	}
	
	public void createWord(List <TextPosition> wordInfo) {
		
		double minX = wordInfo.get(0).getXDirAdj();
		double maxX = minX + wordInfo.get(0).getWidthDirAdj();
		double minY = wordInfo.get(0).getYDirAdj() - wordInfo.get(0).getHeightDir();
		double maxY = wordInfo.get(0).getYDirAdj();
		
		double spaceSize = wordInfo.get(0).getWidthOfSpace();
		spaceWidth.add( (spaceSize/72d) ) ;
		
		String curWordText = "";
		
		for(TextPosition cur: wordInfo) {
			minX = Math.min(minX, cur.getXDirAdj() );
			maxX = Math.max(maxX, cur.getXDirAdj() + cur.getWidthDirAdj() );
			minY = Math.min(minY, cur.getYDirAdj() - cur.getHeightDir() );
			maxY = Math.max(maxY, cur.getYDirAdj() );
			
			curWordText += cur.getUnicode();
		}
		
		Word word = new Word();
		BoundingBox boundingBox = new BoundingBox();
		
		minX = (minX / 72d);
		maxX = (maxX / 72d);
		minY = (minY / 72d);
		maxY = (maxY / 72d);
		
		boundingBox.setTopLeftX(minX);
		boundingBox.setTopLeftY(minY);
		
		boundingBox.setBottomLeftX(minX);
		boundingBox.setBottomLeftY(maxY);
		
		boundingBox.setTopRightX(maxX);
		boundingBox.setTopRightY(minY);
		
		boundingBox.setBottomRightX(maxX);
		boundingBox.setBottomRightY(maxY);
		
		word.setBoundingBox(boundingBox);
		word.setText(curWordText);
		
		words.add(word);
	}
}
