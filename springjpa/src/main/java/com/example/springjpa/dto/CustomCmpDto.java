package com.example.springjpa.dto;

public class CustomCmpDto {

	private Integer cmpId;
	private String city;
	public CustomCmpDto(Integer cmpId, String city) {
		super();
		this.cmpId = cmpId;
		this.city = city;
	}
	public Integer getCmpId() {
		return cmpId;
	}
	public void setCmpId(Integer cmpId) {
		this.cmpId = cmpId;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
	
}
