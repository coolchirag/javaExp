package com.chirag.expor;

public class ExporThisKeyWord {

	public static void main(String[] args) {
		
		ExporThisKeyWord data = new ExporThisKeyWord();
		//data.this;
	}
	
	private void f1() {
		ExporThisKeyWord obj = ExporThisKeyWord.this;
		
	}
}
