package com.chirag.collection;

import java.util.Arrays;
import java.util.List;

public class ArrayToList {

	public static void main(String[] args) {
		List<String> datas = Arrays.asList("Data1");
		datas.add("Data2");
		System.out.println(datas);
		for(int i = 3;i<50;i++) {
			datas.add("data"+i);
		}
		System.out.println(datas);
	}
}
