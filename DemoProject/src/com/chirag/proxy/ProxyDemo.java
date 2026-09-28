package com.chirag.proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

public class ProxyDemo {

	public static void main(String[] args) {
		ProxyDemo obj = new ProxyDemo();
		DummyInterface clasObj = (DummyInterface) Proxy.newProxyInstance(DummyInterface.class.getClassLoader()	, new Class<?>[] {DummyInterface.class}, obj.new ProxyHandler());
		String msg = clasObj.getMsg();
		System.out.println(clasObj.getMsg());
		
		
	}
	private class ProxyHandler implements InvocationHandler {

		RealClass obj;

		public ProxyHandler() {
			obj = new RealClass();
		}
 
		@Override
		public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
			// TODO Auto-generated method stub
			return method.invoke(obj, args);
		}
		
	}
}
