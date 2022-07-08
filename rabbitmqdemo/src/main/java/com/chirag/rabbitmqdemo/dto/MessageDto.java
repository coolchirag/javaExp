package com.chirag.rabbitmqdemo.dto;

public class MessageDto {

	private String message;
	private boolean isReceived;
	
	public MessageDto() {
		super();
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public boolean isReceived() {
		return isReceived;
	}
	public void setReceived(boolean isReceived) {
		this.isReceived = isReceived;
	}
	
	
	
}
