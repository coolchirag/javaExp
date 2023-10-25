package com.chirag;

public class TestClass {

	private static int x;
	
	public static void main(String[] args) {
		TestClass obj1 = new TestClass();
		obj1.x=10;
		
		TestClass obj2 = new TestClass();
		obj2.x=20;
		
		x=30;
		
		System.out.print("x="+obj1.x);
		System.out.print("x="+obj2.x);
	}
}
