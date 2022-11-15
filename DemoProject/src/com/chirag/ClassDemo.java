package com.chirag;

public class ClassDemo {

	public static void f3(I obj) {
		System.out.println("I");
	}
	
	
	public static void f3(B obj) {
		System.out.println("B");
	}
	
	public static void f3(A obj) {
		System.out.println("A");
	}

	public static void main(String[] args) {
		/*
		 * A ob; System.out.println("1"); A.i = 5;
		 */
		new B("hello");
		/*
		 * I obj = new IC(); obj.f2();
		 */
		/*
		 * A ob2 = new A(); System.out.println(ob2 instanceof A); System.out.println(ob2
		 * instanceof B); f3(null);
		 */
	}

}


class A {
	 C obc = new C();
	 static D d = new D();
	 static int i = 0;
	 static {
		 System.out.println("Static block A");
	 }
	public A() {
		System.out.println("A constructor");
	}
	
	public A(String str) {
		System.out.println("A arg constructor");
	}
	
}

class B extends A {
	static C c = new C();
	 static int i = 0;
	 static {
		 System.out.println("Static block B");
	 }
	public B() {
		System.out.println("B constructor");
	}
	
	public B(String s) {
		super(s);
		System.out.println("B arg constructor");
	}
}
class C {
	public C() {
		System.out.println("C constructor");
	}
}
class D {
	public D() {
		System.out.println("D constructor");
	}
}

interface I {
	default void f2() {
		System.out.println("I");
	}
}

class IC implements I {
	public void f2() {
		System.out.println("IC");
	}
}

class ICB extends IC {
	public void f2() {
		System.out.println("TCB");
	}
}