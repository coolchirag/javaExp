package com.chirag.decimal;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalDemo {

	public static void main2(String[] args) {
		/*
		 * BigDecimal codingAdjust = new BigDecimal(0.059);
		 * System.out.println("Adjust : "+new BigDecimal(5).subtract(new
		 * BigDecimal(2)));
		 */
		int i = 5;
		i+=10;
		System.out.println(i);
		
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
	
	public static void main(String[] args) {
		BigDecimal c1 = new BigDecimal(0.335000);
		BigDecimal c2 = new BigDecimal(0.329000);
		
		BigDecimal c3 = new BigDecimal(0.331000);
		
		BigDecimal c4 = new BigDecimal(0.302000);
		
		BigDecimal c5 = new BigDecimal(0.288000);
		
		BigDecimal c6 = new BigDecimal(0.250000);
		
		BigDecimal c7 = new BigDecimal(0.302000);
		
		BigDecimal c8 = new BigDecimal(0.363000);
		
		BigDecimal di1 = new BigDecimal(0.155000);
		
		BigDecimal di2 = new BigDecimal(0.121000);
		
		BigDecimal pc = new BigDecimal(0.126000);
		
		BigDecimal dt = c1.add(c2).add(c3).add(c4).add(c5).add(c6).add(c8).add(c7);
		dt = dt.add(di1).add(di2).add(pc);
		System.out.println(dt);
		
	}
}
