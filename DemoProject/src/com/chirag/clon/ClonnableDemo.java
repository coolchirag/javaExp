package com.chirag.clon;

public class ClonnableDemo {

	public static void main(String[] args) throws CloneNotSupportedException {
		ClonnableBean2 bean2_1 = new ClonnableBean2("test2_1");
		ClonnableBean2 bean2_2 = new ClonnableBean2("test2_2");
		
		Clonable1 bean = new Clonable1("root");
		bean.addData("root1_1");
		bean.addData("root1_2");
		bean.addcSet(bean2_1);
		bean.addcList(bean2_2);
		
		Object clone = bean.clone();
		System.out.println(clone);
		
	}
}
