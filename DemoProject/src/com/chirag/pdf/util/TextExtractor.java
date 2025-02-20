package com.chirag.pdf.util;

import java.util.ArrayList;
import java.util.List;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.pdfbox.text.TextPosition;

public class TextExtractor extends PDFTextStripper{
	List <Double> spaceWidth;
	
	public TextExtractor() throws Exception {
//		super();
		
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
		
		
		minX = (minX / 72d);
		maxX = (maxX / 72d);
		minY = (minY / 72d);
		maxY = (maxY / 72d);
		
	}
}
