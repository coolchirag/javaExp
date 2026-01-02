package com.chirag.generic;

import java.util.function.Function;

public class GenericTEst {

	public static void main(String[] args) {
		Function<MessageTemplate, String> task = new Function<MessageTemplate, String>() {
			@Override
			public String apply(MessageTemplate t) {
				MessageType1 messageType1 = (MessageType1) t;
				

				return null;
			}
		};
		
		task.apply(new MessageType1());
	}
	
	void function(Class<?> className) {
	}
}
