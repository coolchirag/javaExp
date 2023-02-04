package com.chirag.collection;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MapDemo {

	public static void main(String[] args) {
		Map<String, List<String>> map = new HashMap<>();
		List<String> srs = new ArrayList<>();
		srs.add("S1");
		srs.add("s2");
		map.put("1", srs);
		
	}
}
