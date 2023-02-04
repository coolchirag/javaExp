package com.chirag.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CollectionStreamDemo {

	public static void main(String[] args) {
		List<Data> list = new ArrayList<CollectionStreamDemo.Data>();
		list.add(new Data(10));
		list.add(new Data(2));
		list.add(new Data(30));
		System.out.println("Init : "+list);
		list.sort(Comparator.comparing(d -> d.getI()));
		System.out.println("DOne : "+list);
		
	}
	
	public static void main2(String[] args) {
		List<String> datas = new ArrayList<>();
		datas.add("Hello");
		datas.add("HI");
		datas.add("HiHello");
		Stream<String> stream = datas.stream();
		Stream<String> mapStream = stream.map(String::toUpperCase);
		mapStream.collect(Collectors.toList());
		System.out.println(mapStream);
	}
	
	public static class Data {
		private Integer i;
		

		public Data(Integer i) {
			super();
			this.i = i;
		}

		public Integer getI() {
			System.out.println("get data : "+i);
			return i;
		}

		public void setI(Integer i) {
			this.i = i;
		}

		@Override
		public String toString() {
			return "Data [i=" + i + " hashCode : "+hashCode()+ "]";
		}

		
		
	}
}
