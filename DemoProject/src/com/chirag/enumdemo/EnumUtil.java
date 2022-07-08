package com.chirag.enumdemo;

import java.awt.Window.Type;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class EnumUtil {
	
	private static Map<String, Types> nameMap = new HashMap<>();

	private static Map<Integer, Types> idMap = new HashMap<>();
	
	public enum Types {
		TYPE1("one"),
		TYPE2("two");
		
		private String name;
		
		private Integer id;

		private Types(String name) {
			this.name = name;
			nameMap.put(name, this);
		}
		
		
		
		private void setId(Integer id) {
			idMap.put(id, this);
			this.id = id;
		}
		
	}
	
	public static Types getBYName(String name) {
		return nameMap.get(name);
	}
	
	public static void main(String[] args) {
		//Types data = Types.TYPE2;
		Types.values();
		System.out.println(nameMap.get("one"));
		String[] names = {"one", "two"};
		int index = 0;
		for(String name : names) {
			getBYName(name).setId(index++);
		}
		
		System.out.println(idMap);
	}
	
}
