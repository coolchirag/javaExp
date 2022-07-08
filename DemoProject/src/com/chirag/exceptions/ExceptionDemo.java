package com.chirag.exceptions;

public class ExceptionDemo {

	public static void main(String[] args) {
		f1();
	}
	
	private static void f1() {
		try {
			int i = 0;
			int j = 5/i;
			} catch (Exception e) {
				System.out.println("Catch ");
				throw e;
			} finally {
				System.out.println("finally  ");
				//return;
			}
			System.out.println("Exit");
		}
	
}
