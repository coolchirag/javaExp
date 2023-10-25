package com.chirag.collection;

import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {

	public static void main(String[] args) {
		Queue<String> queue = new LinkedList<>();
		queue.add("msg1");
		queue.add("msg2");
		queue.add("msg3");
		queue.add("msg4");
		String msg;
		while((msg = queue.poll()) != null) {
			System.out.println("Msg : " + msg);
		}
	}
}
