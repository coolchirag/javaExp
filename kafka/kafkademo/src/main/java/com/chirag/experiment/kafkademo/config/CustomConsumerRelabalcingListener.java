package com.chirag.experiment.kafkademo.config;

import java.util.Collection;

import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.common.TopicPartition;

public class CustomConsumerRelabalcingListener implements ConsumerRebalanceListener {
	
	private String topic;
	private Collection<TopicPartition> assignedPartitions;
	
	public CustomConsumerRelabalcingListener(String topic) {
		super();
		this.topic = topic;
	}

	public String getTopic() {
		return topic;
	}

	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
		System.out.println(topic + " onPartitionsRevoked : "+partitions);
		assignedPartitions.removeAll(partitions);
		
	}

	@Override
	public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
		System.out.println(topic + " onPartitionsAssigned : "+partitions);
		assignedPartitions=partitions;
	}

	public Collection<TopicPartition> getAssignedPartitions() {
		return assignedPartitions;
	}

}
