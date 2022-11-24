package com.chirag;

import java.util.ArrayList;
import java.util.List;

public class TestData {

	public void f1(List<String> s) {
		System.out.println("1");
	}
	
	public void f1(ArrayList<String> s) {
		System.out.println("2");
	}
	
	public static void main(String[] args) {
		TestData obj = new TestData();
		obj.f1(null);
	}
}
