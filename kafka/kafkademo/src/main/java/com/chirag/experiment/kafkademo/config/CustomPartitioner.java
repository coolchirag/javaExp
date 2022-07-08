package com.chirag.experiment.kafkademo.config;

import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;

public class CustomPartitioner implements Partitioner {

	private static int count = 0;
	private static Object priviousValue;
	@Override
	public void configure(Map<String, ?> configs) {
		
	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		if(priviousValue == null || !priviousValue.equals(value)) {
			count++;
			count = count%2;
			priviousValue = value;
		}
		return count;
	}

	@Override
	public void close() {
	}

}
