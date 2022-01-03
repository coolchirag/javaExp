package com.chirag.hierarchy;

public class SuperClass {

	public void f1() {
		System.out.println("Inside Super f1");
		f2();
	}
	
	public void f2() {
		System.out.println("Inside super f2");
	}
}
