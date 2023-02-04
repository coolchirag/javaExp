package com.chirag.experiment.springBootStaticWeb.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@Controller
public class GeneralController {

	@GetMapping(value = "/helloTest")
	public String getHelloTest() {
		return "hello.html";
	}
}
