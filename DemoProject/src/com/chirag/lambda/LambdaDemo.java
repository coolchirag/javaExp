package com.chirag.lambda;

import java.util.List;

public class LambdaDemo {

	public static void main(String[] args) {
		List<Bean> data = null;
		data.stream().filter(Bean::isValid);
	}
}
