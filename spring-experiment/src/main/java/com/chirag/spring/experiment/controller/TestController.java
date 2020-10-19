package com.chirag.spring.experiment.controller;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
public class TestController {

	@PostMapping("/")
	public String loadFile(@RequestParam MultipartFile[] files) {
		for(MultipartFile file : files) {
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(file.getOriginalFilename());
		}
		return "Done";
		
	}
}
