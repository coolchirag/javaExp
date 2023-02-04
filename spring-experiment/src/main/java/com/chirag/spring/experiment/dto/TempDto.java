package com.chirag.spring.experiment.dto;

public class TempDto {

	private String data;
	private Long id;
	public TempDto() {
		super();
	}
	public TempDto(String data, Long id) {
		super();
		this.data = data;
		this.id = id;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
}
