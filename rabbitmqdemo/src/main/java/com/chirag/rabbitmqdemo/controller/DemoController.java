package com.chirag.rabbitmqdemo.controller;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.rabbitmqdemo.dto.MessageDto;
import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.rabbitmq.client.Envelope;

@RestController
public class DemoController {
	
	private static final String HOST = "127.0.0.1";
	private static final String QUEUE_NAME = "phi";

	@GetMapping("/produce/{message}")
	public String produce(@PathVariable("message") String message) throws IOException, TimeoutException {
		ConnectionFactory cf = new ConnectionFactory();
		cf.setUsername("user");
		cf.setPassword("bitnami");
		cf.setHost(HOST);
		Connection connection = cf.newConnection();
		Channel channel = connection.createChannel();
		
		channel.queueDeclare();
		
		channel.basicPublish("", QUEUE_NAME, null, message.getBytes("UTF-8"));
		
		channel.close();
		connection.close();
		return "Done";
	}
	
	@GetMapping("/consume")
	public String consume() throws IOException, TimeoutException {
		String data = "";
		ConnectionFactory cf = new ConnectionFactory();
		cf.setUsername("user");
		cf.setPassword("password_test");
		cf.setHost(HOST);
		Connection connection = cf.newConnection();
		Channel channel = connection.createChannel();
		/*
		 * DeliverCallback callback = new DeliverCallback() {
		 * 
		 * @Override public void handle(String consumerTag, Delivery message) throws
		 * IOException { data = consumerTag+" : "+message+"\n";
		 * 
		 * } };
		 */
		MessageDto messageDto = new MessageDto();
		DefaultConsumer consumer = new DefaultConsumer(channel) {
			 @Override
	         public void handleDelivery(String consumerTag,
	                                    Envelope envelope,
	                                    AMQP.BasicProperties properties,
	                                    byte[] body)
	             throws IOException
	         { 
				 System.out.println(System.currentTimeMillis() + " : " + new String(body));
				 messageDto.setMessage(new String(body));
				 channel.basicAck(envelope.getDeliveryTag(), false);
				 messageDto.setReceived(true);
				 
	         }
		};
		 
		//channel.basicConsume(QUEUE_NAME, false, callback, a -> {});
		channel.basicConsume(QUEUE_NAME, false, consumer);
		while(!messageDto.isReceived()) {
			
		}
		channel.close();
		connection.close();
		return messageDto.getMessage();
	}
}
