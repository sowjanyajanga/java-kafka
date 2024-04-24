package com.sowjanya.kafka.producer.web.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloWorldController {

	@GetMapping(path = "/hiThere", produces = "application/json")
	public String sayHi() {
		
		return "{\"message\" : \"Happy Birthday\"}";
	}
}
