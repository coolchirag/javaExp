package com.chirag.enumdemo;

public class EnumDemoMain {

	public static void main(String[] args) {
		System.out.println(TestEnum.test2.ordinal());
		System.out.println(TestEnum.valueOf("TEST1"));
		System.out.println(TestEnum.valueOf("test2"));
		TestEnum te = null;
		System.out.println(TestEnum.TEST1.equals(te));
	}
}
