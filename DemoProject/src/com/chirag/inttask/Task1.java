package com.chirag.inttask;

public class Task1 {

	public static void main(String[] args) {
		String str = "hello";
		f1(str);
		System.out.println(str);
	}
	
	public static void f1(String st) {
		st = "hi";
		return;
	}
}

interface I1 {
	default void m() {
		System.out.println("I1");
	}
}

interface I2 {
	default void m() {
		System.out.println("I2");
	}
}

class A implements I1 {
	public void callM() {
		m();
	}
}

/*
 * class B extends A implements I2 { public void callSUperM() { super.m(); } }
 */
