package com.chirag.enumdemo;

import java.util.Collections;
import java.util.List;

public class EnumDemoMain {

	public static void main(String[] args) {
		List<String> list = Collections.emptyList();
		System.out.println(list.isEmpty() + " : "+list.size());
		System.out.println(TestEnum.TEST1.equals(null));
		System.out.println(TestEnum.test2.ordinal());
		System.out.println(TestEnum.valueOf("TEST1"));
		System.out.println(TestEnum.valueOf("test2"));
		TestEnum te = null;
		System.out.println(TestEnum.TEST1.equals(te));
	}
}
