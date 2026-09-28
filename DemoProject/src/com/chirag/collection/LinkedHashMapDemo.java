package com.chirag.collection;

import java.util.LinkedHashMap;
import java.util.Set;

public class LinkedHashMapDemo {

	public static void main(String[] args) {
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("Key1", "Value1");
		map.put("Key5", "Value5");
		map.put("Key2", "Value2");
		map.put("Key3", "Value3");
		map.put("Key4", "Value4");
		map.put("Key2", "Value22");
		map.put("Key1", "Value11");
		Set<String> keySet = map.keySet();
		System.out.println(keySet);
		System.out.println(map);
	}
}
