package com.chirag.collection;

import java.util.HashSet;
import java.util.Set;

public class CollectionJoinTOSTrDemo {

	public static void main(String[] args) {
		Set<String> datas = new HashSet<String>();
		datas.add("hi1");
		System.out.println(String.join(",", datas));
	}
}
