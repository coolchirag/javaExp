package com.chirag.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversionDemo {

	public static void main(String[] args) throws ParseException {
		final String DOS_DATE_FROMATE = "dd/MM/yyyy";
		String date = "02/24/1960";
		Date outputDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DOS_DATE_FROMATE);
		outputDate = formatter.parse(date);
		System.out.println(outputDate);
	}
	
	 

}
