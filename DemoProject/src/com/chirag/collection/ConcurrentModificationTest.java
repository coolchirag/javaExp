package com.chirag.collection;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class ConcurrentModificationTest {

	public static void main(String[] args) {
		List<Integer> list = Collections.singletonList(5);
		
		for (int i = 0; i <= 9; i++) {
			System.out.println(i);
			list.add(i);
		}
		
		/*
		 * for(Integer data : list) { if(data%2==0) { list.remove(data); } }
		 */
		for (int i = 0; i < list.size(); i++) {
			Integer data = list.get(i);
			System.out.println("i : "+i+", size : "+list.size());
			if(data%2!=0) {
				System.out.println("Removing : "+data+", i : "+i);
				list.remove(data);
			} 
		
		}
		System.out.println("Done");
	}
}
