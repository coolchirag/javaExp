package com.chirag.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionStreamDemo {

	public static void main(String[] args) {
		List<String> datas = new ArrayList<>();
		datas.add("Hello");
		datas.add("HI");
		datas.add("HiHello");
		Stream<String> stream = datas.stream();
		Stream<String> mapStream = stream.map(String::toUpperCase);
		mapStream.collect(Collectors.toList());
		System.out.println(mapStream);
	}
}
