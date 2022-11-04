package com.chirag.string;

public class StringSpecialCharHandler {

	public static void main(String[] args) {
		String str1 = "Status code 404, \"﻿<?xml version=\"1.0\" encoding=\"utf-8\"?><Error><Code>BlobNotFound</Code><Message>The specified blob does not exist.\nRequestId:3e136cca-901e-0032-5833-cf674200000";
		System.out.println(str1);
		System.out.println("-----------After-------------");
		System.out.println(str1.replaceAll("\\xFFFD", ""));
	}
}
