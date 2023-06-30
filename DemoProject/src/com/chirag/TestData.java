package com.chirag;

import java.util.ArrayList;
import java.util.List;

public class TestData {
	
	public static void main(String[] args) {
		Long l1 = 5l;
		Long l2 = 6l;
		l2--;
		System.out.println(l1.equals(l2));
	}

	public void f1(List<String> s) {
		System.out.println("1");
	}
	
	public void f1(ArrayList<String> s) {
		System.out.println("2");
	}
	
	public static void main2(String[] args) {
		TestData obj = new TestData();
		obj.f1(null);
	}
}
