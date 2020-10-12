package com.websystique.hibernate.model;

import javax.persistence.Column;

public class COmpanyDtoTemp {

	private int id;

	private String companyName;
	
	private String city;

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	@Override
	public String toString() {
		return "COmpanyDtoTemp [id=" + id + ", companyName=" + companyName + ", city=" + city + "]";
	}
	
	
}
