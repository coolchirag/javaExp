package com.chirag.experiment.kafkademo.schedular;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRebalanceListener;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.apache.kafka.common.TopicPartition;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.chirag.experiment.kafkademo.config.CustomConsumerRelabalcingListener;
import com.chirag.experiment.kafkademo.service.MessageProcessingService;

@Component
public class ConsumerSchedular {
	
	private Map<String, ConsumerDetailDto> queueToConsumerMap = new HashMap<>();
	
	private static final Long TOKEN_QUEUE_INTERVAL_IN_MS = 60000l;
	private static final Long INTERVAL_IN_MS = 30000l;
	
	private static final String KAFKA_HOST = "34.86.244.86:9092";
	
	//public static String uncommitedMessage = null;
	
	@Autowired
	private MessageProcessingService service;
	
	private KafkaProducer<String, String> producer;
	
	private List<String> queueSequence = new ArrayList<>();
	
	
	
	public ConsumerSchedular() {
		super();
		initProducer();
		queueSequence.add("ctest1");
		queueSequence.add("ctest2");
		queueSequence.add("ctest3");
		queueSequence.add("ctest4");
		//queueSequence.add("ctest3");
	}

	@Scheduled(fixedDelay = 1000)
	public void performConsumerScheduler1() throws Exception {
		System.out.println("Hello");
		String queueName = "nlp_v1_service_preprocessing_token_queue";
		List<String> messages = receiveMessageWithRetry(queueName, 1);
		System.out.println(queueName+" : ReceivedMessages : "+messages);
	}
	
	//@Scheduled(fixedDelay = 1000)
	public void performConsumerScheduler2() throws Exception {
		System.out.println("Hello");
		String queueName = "ctest2";
		List<String> messages = receiveMessageWithRetry(queueName, 1);
		System.out.println(queueName+" : ReceivedMessages : "+messages);
	}
	
	//@Scheduled(fixedDelay = 1000)
	public void performConsumerScheduler3() throws Exception {
		System.out.println("Hello");
		String queueName = "ctest3";
		List<String> messages = receiveMessageWithRetry(queueName, 1);
		System.out.println(queueName+" : ReceivedMessages : "+messages);
		
	}
	
	//@Scheduled(fixedDelay = 1000)
	public void performConsumerScheduler4() throws Exception {
		System.out.println("Hello");
		String queueName = "ctest4";
		List<String> messages = receiveMessageWithRetry(queueName, 1);
		System.out.println(queueName+" : ReceivedMessages : "+messages);
	}
	
	private List<String> receiveMessageWithRetry(String queueName, Integer maxNumberOfMessages) throws Exception {
		final List<String> messages = new ArrayList<>();
		ConsumerDetailDto consumerDto = getConsumerDto(queueName, maxNumberOfMessages);
		KafkaConsumer<String,String> consumer = consumerDto.getConsumer();
		if(consumerDto.getLastUncommitedMessage() != null) {
			System.out.println(queueName+" found uncommited message : "+consumerDto.getLastUncommitedMessage());
			sendMessage(consumerDto.getLastUncommitedMessage(), queueName);
			consumer.commitAsync();
			consumerDto.setLastUncommitedMessage(null);
			
		}
		ConsumerRecords<String, String> records = consumer.poll(Duration.ofSeconds(10));
		if(records!= null && !records.isEmpty()) {
			records.forEach(record -> {
				messages.add(record.key()+" : "+record.value());
				System.out.println("------------------ "+queueName+ " : ReceivedMessages : "+messages);
				consumerDto.setLastUncommitedMessage(record.value());
				if(service.isProccessed(record.value())) {
					consumer.commitSync();
					consumerDto.setLastUncommitedMessage(null);
					int indexOfNextQueue = queueSequence.indexOf(queueName)+1;
					
					if(indexOfNextQueue < queueSequence.size()) {
						sendMessage(record.value(), queueSequence.get(indexOfNextQueue));
					}
				}
			});
			//consumerDto.setLastUncommitedMessage(null);
			//consumer.commitSync();
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
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "groupId12489");
			properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false");
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
			
			final Long interval = queueName.contains("token") ? TOKEN_QUEUE_INTERVAL_IN_MS : INTERVAL_IN_MS;
			properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, interval+"");
			
			properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1");
			
			KafkaConsumer<String, String> consumer = new KafkaConsumer<String, String>(properties);
			CustomConsumerRelabalcingListener listener = new CustomConsumerRelabalcingListener(queueName);
			consumer.subscribe(Arrays.asList(queueName), listener);
			consumerDetailDto = new ConsumerDetailDto(consumer, interval, listener);
			queueToConsumerMap.put(queueName, consumerDetailDto);
		}
		return consumerDetailDto;
	}
	
	private class ConsumerDetailDto {
		private KafkaConsumer<String, String> consumer;
		private Long interval;
		private String lastUncommitedMessage;
		private CustomConsumerRelabalcingListener listener;
		
		private ConsumerDetailDto(KafkaConsumer<String, String> consumer, Long interval, CustomConsumerRelabalcingListener listener) {
			super();
			this.consumer = consumer;
			this.interval = interval;
			this.listener = listener;
		}
		private KafkaConsumer<String, String> getConsumer() {
			return consumer;
		}
		private Long getInterval() {
			return interval;
		}
		private String getLastUncommitedMessage() {
			return lastUncommitedMessage;
		}
		private void setLastUncommitedMessage(String lastUncommitedMessage) {
			this.lastUncommitedMessage = lastUncommitedMessage;
		}
		public CustomConsumerRelabalcingListener getListener() {
			return listener;
		}
		
	}
	
	public void sendMessage(String message, String queueName) {
		ProducerRecord<String, String> record = new ProducerRecord<>(queueName, message);
		Future<RecordMetadata> send = producer.send(record);
		try {
			RecordMetadata recordMetadata = send.get();
		} catch (InterruptedException | ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	
	private void initProducer() {
		Properties properties = new Properties();
		properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOST);
		properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		// properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG,
		// CustomPartitioner.class.getName());
		producer = new KafkaProducer<String, String>(properties);
	}
}
