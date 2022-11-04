package com.chirag.datetime;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DateConversionDemo {

	public static void main(String[] args) throws ParseException {
		final String DOS_DATE_FROMATE = "MM/dd/yyyy";
		String date = "12/11/2021";
		Date outputDate = null;
		SimpleDateFormat formatter = new SimpleDateFormat(DOS_DATE_FROMATE);
		outputDate = formatter.parse(date);
		System.out.println(outputDate);
	}
}
