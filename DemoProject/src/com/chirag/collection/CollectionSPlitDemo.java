package com.chirag.collection;

import java.util.ArrayList;
import java.util.List;

public class CollectionSPlitDemo {

	public static void main(String[] args) {
		List<Integer> datas = new ArrayList<>();
		for(int i=0;i<100;i++) {
			datas.add(i);
		}
		
		final int splitWindowSize = 9;
		final int codeIdListSize = 100;
		int i =0;
		while(i<codeIdListSize) {
			int windowSize = (i+splitWindowSize) < codeIdListSize ? splitWindowSize : codeIdListSize-i;
			List<Integer> subCodeIdList = datas.subList(i, i+windowSize);
			System.out.println(subCodeIdList);
			i += windowSize;
		}
	}
}
