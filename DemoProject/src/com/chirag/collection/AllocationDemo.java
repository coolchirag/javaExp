package com.chirag.collection;

import java.util.HashMap;
import java.util.Map;

public class AllocationDemo {

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();
		map.put(1, "One");
		map.put(2,  "Two");
		String data = "";
		if((data = map.get(3)) != null) {
			System.out.println(data);
		} else { 
			System.out.println("NotFound");
		}
			
	}
}
