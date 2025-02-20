package com.chirag.dto;

public class TestDto {

	private String data;

	public TestDto(String data) {
		super();
		System.out.println("Creating data : "+Thread.currentThread().getId());
		this.data = data;
	}

	public String getData() {
		return data;
	}

	public void setData(String data) {
		this.data = data;
	}
	
}
