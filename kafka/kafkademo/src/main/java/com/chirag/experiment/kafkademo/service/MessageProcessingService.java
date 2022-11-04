package com.chirag.experiment.kafkademo.service;

import java.util.HashSet;
import java.util.Set;

import org.springframework.stereotype.Service;

@Service
public class MessageProcessingService {

	private static Set<String> proceesedMessages = new HashSet<>();
	
	public void addProccessed(String message) {
		proceesedMessages.add(message);
	}
	
	public boolean isProccessed(String message) {
		return proceesedMessages.remove(message);
	}
	
}
