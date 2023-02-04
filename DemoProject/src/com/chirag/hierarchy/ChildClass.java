package com.chirag.hierarchy;

public class ChildClass extends SuperClass{

	/*
	 * @Override public void f1() { System.out.println("Inside child f1"); f2(); }
	 */
	
	@Override
	public void f2() {
		System.out.println("Inside child f2");
	}
}
