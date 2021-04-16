package com.chirag.spring.experiment.controller;

import java.util.List;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.chirag.spring.experiment.dto.TestRquest;

@RestController
public class TestController {

	@PostMapping("/test")
	public String loadData(@RequestBody(required = false) List<TestRquest> data) {
		return "hi";
	}
}
