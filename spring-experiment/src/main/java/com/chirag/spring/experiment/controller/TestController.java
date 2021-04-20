package com.chirag.spring.experiment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.chirag.spring.experiment.dto.TestRequest;

@RestController
public class TestController {

	@PostMapping("/test")
	public String loadData(@RequestBody(required = false) List<TestRequest> data) {
		return "hi";
	}
}
