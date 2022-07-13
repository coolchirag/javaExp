package com.chirag.experiment.kafkademo.schedular;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class ConsumerSchedular {
	
	private Map<String, ConsumerDetailDto> queueToConsumerMap = new HashMap<>();
	
	private static final Long TOKEN_QUEUE_INTERVAL_IN_MS = 60000l;
	private static final Long INTERVAL_IN_MS = 30000l;
	
	private static final String KAFKA_HOST = "127.0.0.1:9092";
	
	public static boolean uncommited = false;
	
	@Scheduled(fixedDelay = 1000)
	public void performConsumerScheduler() throws Exception {
		System.out.println("Hello");
		String queueName = "ctest3";
		List<String> messages = receiveMessageWithRetry(queueName, 1, true);
		System.out.println("ReceivedMessages : "+messages);
	}
	
	private List<String> receiveMessageWithRetry(String queueName, Integer maxNumberOfMessages, boolean allowRetry) throws Exception {
		System.out.println("Inside receive : "+allowRetry);
		final List<String> messages = new ArrayList<>();
		ConsumerDetailDto consumerDto = getConsumerDto(queueName, maxNumberOfMessages);
		KafkaConsumer<String,String> consumer = consumerDto.getConsumer();
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
		if(records!= null && !records.isEmpty()) {
			records.forEach(record -> messages.add(record.key()+" : "+record.value()));
			
			//consumer.commitSync();
		} else if(allowRetry) {
			Thread.sleep(consumerDto.getInterval()+100);
			return receiveMessageWithRetry(queueName, maxNumberOfMessages, false);
		}
		return messages;
	}

	
	private ConsumerDetailDto getConsumerDto(String queueName, Integer maxNumberOfMessages) {
		ConsumerDetailDto consumerDetailDto = queueToConsumerMap.get(queueName);
		if(consumerDetailDto == null) {
			Properties properties = new Properties();
			properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOST);
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "groupId8");
			properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
			
			final Long interval = queueName.contains("token") ? TOKEN_QUEUE_INTERVAL_IN_MS : INTERVAL_IN_MS;
			properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, interval+"");
			
			properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
			
			KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
			consumer.subscribe(Arrays.asList(queueName), new ConsumerRebalanceListener() {
				
				@Override
				public void onPartitionsRevoked(Collection<TopicPartition> partitions) {
					System.out.println("Inside onPartitionsRevoked : "+partitions);
					
				}
				
				@Override
				public void onPartitionsAssigned(Collection<TopicPartition> partitions) {
					System.out.println("Inside onPartitionsAssigned : "+partitions);
					
				}
			});
			consumerDetailDto = new ConsumerDetailDto(consumer, interval);
			queueToConsumerMap.put(queueName, consumerDetailDto);
		}
		return consumerDetailDto;
	}
	
	private class ConsumerDetailDto {
		private KafkaConsumer<String, String> consumer;
		private Long interval;
		
		private ConsumerDetailDto(KafkaConsumer<String, String> consumer, Long interval) {
			super();
			this.consumer = consumer;
			this.interval = interval;
		}
		private KafkaConsumer<String, String> getConsumer() {
			return consumer;
		}
		private Long getInterval() {
			return interval;
		}
		
		
	}
}
