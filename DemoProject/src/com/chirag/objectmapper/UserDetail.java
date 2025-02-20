package com.chirag.objectmapper;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDetail {

	private String name;
	private Integer id2;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Integer getId() {
		return id2;
	}
	public void setId(Integer id) {
		this.id2 = id;
	}
	@Override
	public String toString() {
		return "UserDetail [name=" + name + ", id=" + id2 + "]";
	}
	
	
	
}
