package com.chirag.experiment.kafkademo.controller;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.experiment.kafkademo.config.CustomPartitioner;

@RestController
public class ControllerClass {

	/*
	 * @Autowired KafkaTemplate<String, String> template;
	 * 
	 * @GetMapping("/test") public String test() {
	 * System.out.println("Inside controller"); template.send("test", "test");
	 * System.out.println("Done"); return "done"; }
	 */
	
	private static final String KAFKA_HOST = "127.0.0.1:9092";
	
	private static final String TOPIC_1 = "ctest3";
	
	public static void main(String[] args) {
		//produce();
		System.out.println("Done");
	}
	
	Producer<String, String> producer;
	
	KafkaConsumer<String, String> consumer;
	
	@GetMapping("/produce/{message}")
	public String produce(@PathVariable("message") String msg) {
		initProducer();
		//msg = "{\"topic\": \"/subscriptions/b186e45b-bd3d-431a-9fda-a2460981a15f/resourceGroups/int-coding-platform-rg/providers/Microsoft.Storage/storageAccounts/intcodingplatformsa\",\n  \"subject\": \"/blobServices/default/containers/capc-apigateway/blobs/inbound/KPMG/Project1/text/1150_MR 38.txt\",\n  \"eventType\": \"Microsoft.Storage.BlobCreated\",\n  \"id\": \"3b216be8-101e-004e-7d13-86fa7706efeb\",\n  \"data\": {\n    \"api\": \"PutBlob\",\n    \"clientRequestId\": \"92b30ac8-a544-4ddf-b767-71b0151ac027\",\n    \"requestId\": \"3b216be8-101e-004e-7d13-86fa77000000\",\n    \"eTag\": \"0x8DA542A33CD0C50\",\n    \"contentType\": \"text/plain\",\n    \"contentLength\": 1281,\n    \"blobType\": \"BlockBlob\",\n    \"url\": \"/home/chiragjivani/capc-apigateway/2020_doc.pdf\",\n    \"sequencer\": \"000000000000000000000000000009A200000000004f5b4b\",\n    \"storageDiagnostics\": {\n      \"batchId\": \"c5cacd7d-e006-0072-0013-864eac000000\"\n    }\n  },\n  \"dataVersion\": \"\",\n  \"metadataVersion\": \"1\",\n  \"eventTime\": \"2022-06-22T08:35:46.078216Z\"\n}";
		ProducerRecord<String, String> record = new ProducerRecord(TOPIC_1, msg, msg);
		producer.send(record);
		//producer.close();
		return "Done";
	}
	
	void initProducer() {
		if(producer == null) {
			Properties properties = new Properties();
			properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOST);
			properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
			properties.setProperty(ProducerConfig.PARTITIONER_CLASS_CONFIG, CustomPartitioner.class.getName());
			producer = new  KafkaProducer<String, String>(properties);
		}
	}
	
	@GetMapping("/consume")
	public String consume() throws InterruptedException {
		
		initConsumer();
		StringBuilder sb = new StringBuilder();
		boolean foundData = false;
		int count = 1;
		while(!foundData) {
			//initConsumer();
			ConsumerRecords<String, String> messages = consumer.poll(Duration.ofMillis(10000));
			
			for(ConsumerRecord<String, String> record : messages) {
				sb.append(record.value());
				sb.append("\n");
				foundData = true;
			}
			if(foundData) {
				sb.append(count +" : "+System.currentTimeMillis());
				System.out.println(sb.toString());
			} else {
				//Thread.sleep(30000+100);
				count++;
				foundData = true;
			}
			//foundData = true;
			consumer.commitSync();
		}
		return sb.toString();
	}
	
	private void initConsumer() {
		if(consumer == null) {
			Properties properties = new Properties();
			properties.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, KAFKA_HOST);
			properties.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
			properties.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "groupId6"); //Set group id
			properties.setProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "false"); //TO disable auto commit
			properties.setProperty(ConsumerConfig.MAX_POLL_RECORDS_CONFIG, "1"); //TO read only single message in each poll
			properties.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest"); //Set this property to start reading messages from earliest/oldest uncommitted message.
			
			//300000
			properties.setProperty(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "30000"); //Set this to block that message in a queue (i.e visibility timeout in AWS SQS)
			
			
			consumer = new  KafkaConsumer<String, String>(properties);
			consumer.subscribe(Collections.singleton(TOPIC_1));
		}
	}
}
