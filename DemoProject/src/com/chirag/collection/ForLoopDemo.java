package com.chirag.collection;

import java.util.ArrayList;
import java.util.List;

public class ForLoopDemo {

	public static void main(String[] args) {
		for(Integer i : getDatas()) {
			System.out.println(i);
		}
	}
	
	private static List<Integer> getDatas() {
		System.out.println("Getting data");
		List<Integer> data = new ArrayList<>();
		data.add(1);
		data.add(2);
		data.add(3);
		data.add(4);
		return data;
	}
	
}
