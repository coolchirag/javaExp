package com.chirag.collection;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {

	public static void main(String[] args) {
		List<Integer> list = new ArrayList<>();
		for(int i=1;i<=25;i++) {
			list.add(i);
		}
		System.out.println(list);
		Iterator<Integer> iterator = list.iterator();
		final int buffer = 10;
		int count = 0;
		String str = "";
		while(iterator.hasNext()) {
			count++;
			str = str + iterator.next()+", ";
			if(count == buffer || !iterator.hasNext()) {
				System.out.println(str);
				count = 0;
				str="";
			}
		}
	}
}
