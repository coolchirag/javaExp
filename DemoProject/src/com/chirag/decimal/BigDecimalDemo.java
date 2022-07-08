package com.chirag.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDemo {

	public static void main(String[] args) {
		/*
		 * BigDecimal codingAdjust = new BigDecimal(0.059);
		 * System.out.println("Adjust : "+new BigDecimal(5).subtract(new
		 * BigDecimal(2)));
		 */
		
		  BigDecimal hccRf = new BigDecimal(2.997); BigDecimal demograohicRf = new
		  BigDecimal(0.451); BigDecimal totalRf = hccRf.add(demograohicRf);
		  System.out.println("Total : "+totalRf); BigDecimal normalRf = new
		  BigDecimal(1.069); totalRf = totalRf.divide(normalRf, 6,
		  RoundingMode.HALF_UP); System.out.println("normalized rf : "+totalRf);
		  BigDecimal codingAdjust = new BigDecimal(0.059);
		  System.out.println("Adjust : "+new BigDecimal(1).subtract(codingAdjust)); totalRf
		  = totalRf.multiply(new BigDecimal(1).subtract(codingAdjust));
		  System.out.println("Adjusted rf : "+totalRf);
		 
	}
}
