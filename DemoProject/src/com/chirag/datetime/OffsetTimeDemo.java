package com.chirag.datetime;

import java.time.OffsetDateTime;
import java.util.Date;

public class OffsetTimeDemo {

	public static void main(String[] args) {
		OffsetDateTime nowTime = OffsetDateTime.now();
		System.out.println(nowTime.toInstant().toEpochMilli());
		System.out.println(new Date(nowTime.toInstant().toEpochMilli()));
		System.out.println("Now : "+nowTime);
		System.out.println("before : "+nowTime.minusHours(25));
		System.out.println(new Date(nowTime.minusHours(23).toInstant().toEpochMilli()));
	}
}
