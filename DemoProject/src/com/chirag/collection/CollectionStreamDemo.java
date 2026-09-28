package com.chirag.collection;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;


public class CollectionStreamDemo {

	public static void main(String[] args) {
		streamToMap();
	}
	public static void main1(String[] args) {
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
	
	public static void streamToMap() {
		List<Data> list = new ArrayList<>();
		
		Map<String, String> map = list.stream().collect(Collectors.toMap(Data::getStr, Data::toString, (d1,d2)-> d1));
		
		/*Map<String, String> documentCodeBeanMap = list.stream()
				.collect(Collectors.toMap(Data::getStr, dcb -> dcb.toString(), (dcb1, dcb2) -> {
					final String errorMsg = "Same code found in documentCodeBeans for documentDosId: " + documentDosBean.getId();
					
					System.out.println(errorMsg);
				}));*/
		System.out.println(map);
		
	}
	
	public static class Data {
		private String str;
		private Integer i;
		

		public Data(Integer i) {
			super();
			this.i = i;
		}

		public Integer getI() {
			System.out.println("get data : "+i);
			return i;
		}

		public String getStr() {
			return str;
		}

		public void setStr(String str) {
			this.str = str;
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
