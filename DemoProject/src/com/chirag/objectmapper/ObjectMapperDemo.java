package com.chirag.objectmapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectReader;

public class ObjectMapperDemo {

	private static final ObjectMapper mapper = new ObjectMapper();
	
	public static void main(String[] args) throws Exception {
		UserDetail userdetail = new UserDetail();
		userdetail.setName("Hello");
		//userdetail.setId(1);
		String mapperValue = "{\"name\":\"Hello\"}";//mapper.writeValueAsString(userdetail);
		System.out.println(mapperValue);
		System.out.println(mapper.readValue(mapperValue, UserDetail.class));
		UserDetail newUserdetails = new UserDetail();
		
		ObjectReader readerForUpdating = mapper.readerForUpdating(newUserdetails);
		newUserdetails = readerForUpdating.readValue(mapperValue);
		System.out.println(newUserdetails);
	}
	
	
}
