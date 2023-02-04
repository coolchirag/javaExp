package com.chirag.collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionModification {

	public static void main(String[] args) {
		List<String> datas = new ArrayList<String>();
		for(int i = 0; i<10;i++) {
			datas.add(i+"");
		}
		datas.remove(2);
		datas.remove(4-1);
		System.out.println(datas);
		
	}
}
