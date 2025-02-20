package com.chirag.generic;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.function.Function;

public class GenericDemo {

	private static <T> void f1(T obj) {
		System.out.println(obj.getClass());
	}
	
	private static <T, R> void f2(Function<T, R> fun) {
		Type type = ((ParameterizedType) fun.getClass().getGenericInterfaces()[0]).getActualTypeArguments()[0];
		type.equals(String.class);
		Class c = (Class) type;
		if( c.equals(String.class)) {
			
		}
		System.out.println(fun.getClass().getGenericInterfaces());
	}
	
	public static void main(String[] args) {
		Function<String, Integer> f = new Function<String, Integer>() {
			
			@Override
			public Integer apply(String t) {
				
				return 5;
			}
		};
		/*f2((a) -> {
			return "Hello"+a;
		});*/
		f2(f);
		
	}
}
