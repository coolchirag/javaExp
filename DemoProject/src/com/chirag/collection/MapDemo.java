package com.chirag.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDemo {

	public static void main(String[] args) {
		Map<Integer, List<String>> map = new HashMap<>();
		List<String> srs = new ArrayList<>();
		srs.add("S1");
		srs.add("s2");
		Integer i1 = Integer.valueOf(1);
		Integer i2 = Integer.valueOf(1);
		map.put(i1, srs);
		System.out.println(map.get(i1));
		System.out.println(map.get(i2));
		
	}
}
