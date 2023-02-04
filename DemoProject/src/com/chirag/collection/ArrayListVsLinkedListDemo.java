package com.chirag.collection;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ArrayListVsLinkedListDemo {
	
	public static void main(String[] args) {
		ArrayListVsLinkedListDemo obj = new ArrayListVsLinkedListDemo();
		ArrayList<TempDto> arrayList = new ArrayList<>();
		LinkedList<TempDto> linkedList = new LinkedList<>();
		obj.collectionTask(arrayList);
		arrayList.clear();
		obj.collectionTask(linkedList);
		linkedList.clear();
		
	
		System.out.println("Done");
	}

	private void collectionTask(List<TempDto> list) {
		long startTime = System.currentTimeMillis();
		int size = 999999;
		String className = list.getClass().getName();
		for(int i=0; i<size;i++) {
			list.add(new TempDto(i, "data"+i+className));
		}
		System.out.println("Insertion Completed in : "+(System.currentTimeMillis()-startTime)+" for "+list.getClass());
		/*
		 * ArrayListPerformWell
		 * for(int i=0; i<size;i++) { list.get(i).setId(i+1); }
		 * System.out.println("Update1 Completed in : "+(System.currentTimeMillis()-
		 * startTime)+" for "+list.getClass());
		 */
		startTime = System.currentTimeMillis();
		for(TempDto dto : list) {
			dto.setId(dto.getId()+1);
		}
		System.out.println("Update2 Completed in : "+(System.currentTimeMillis()-startTime)+" for "+list.getClass());
	}
	
	private static class TempDto {
		private Integer id;
		private String data;
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getData() {
			return data;
		}
		public void setData(String data) {
			this.data = data;
		}
		public TempDto() {
			super();
			// TODO Auto-generated constructor stub
		}
		public TempDto(Integer id, String data) {
			super();
			this.id = id;
			this.data = data;
		}
		
		
	}
}
