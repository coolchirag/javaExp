package com.chirag.pdf.dto;

import java.util.List;

public class ParserOutput {
	
	private List <ParserPageOutput> parserPageOutputs;

	public ParserOutput() {
	}


	public ParserOutput(List<ParserPageOutput> parserPageOutputs) {
		this.parserPageOutputs = parserPageOutputs;
	}
	
	public List<ParserPageOutput> getParserPageOutputs() {
		return parserPageOutputs;
	}


	public void setParserPageOutputs(List<ParserPageOutput> parserPageOutputs) {
		this.parserPageOutputs = parserPageOutputs;
	}

	
	
	public static class ParserPageOutput{
		
		private Integer pageNumber;
		private List <ImageDetail> imageDetails;
		private String text;
		private List<Word> words;
		private Double pageHeight;
		private Double pageWidth;
		private PageDimensionUnit pageDimensionUnit;
		private boolean isParsedSuccessfully;
		
		public Integer getPageNumber() {
			return pageNumber;
		}
		public void setPageNumber(Integer pageNumber) {
			this.pageNumber = pageNumber;
		}
		
		public List<ImageDetail> getImageDetails() {
			return imageDetails;
		}
		public void setImageDetails(List<ImageDetail> imageDetails) {
			this.imageDetails = imageDetails;
		}
		
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		
		public List<Word> getWords() {
			return words;
		}
		public void setWords(List<Word> words) {
			this.words = words;
		}
		
		public Double getPageHeight() {
			return pageHeight;
		}
		public void setPageHeight(Double pageHeight) {
			this.pageHeight = pageHeight;
		}
		
		public Double getPageWidth() {
			return pageWidth;
		}
		public void setPageWidth(Double pageWidth) {
			this.pageWidth = pageWidth;
		}
		
		public PageDimensionUnit getPageDimensionUnit() {
			return pageDimensionUnit;
		}
		public void setPageDimensionUnit(PageDimensionUnit pageDimensionUnit) {
			this.pageDimensionUnit = pageDimensionUnit;
		}
		public boolean isParsedSuccessfully() {
			return isParsedSuccessfully;
		}
		public void setParsedSuccessfully(boolean isParsedSuccessfully) {
			this.isParsedSuccessfully = isParsedSuccessfully;
		}
		
		
	}
	
	public enum PageDimensionUnit {
        PIXEL("pixel"),
        INCH("inch");

        private String value;

        private PageDimensionUnit(String value) {
            this.value = value;
        }

        public static PageDimensionUnit fromString(String value) {
            PageDimensionUnit[] items = values();
            for (int i = 0; i < items.length; ++i) {
                PageDimensionUnit item = items[i];
                if (item.toString().equalsIgnoreCase(value)) {
                    return item;
                }
            }
            return null;
        }

        public String toString() {
            return this.value;
        }
    }
}
