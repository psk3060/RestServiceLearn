package com.example.restservice.controller;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.concurrent.atomic.AtomicLong;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.service.model.Greeting;

@RestController
public class GreetingController {
	
	//  출력 템플릿
	private static String template = "Hello, %s";
	
	// 고유ID - Auto Increment 생성
	private final AtomicLong counter = new AtomicLong();
	
	@GetMapping("/greeting")
	public Greeting greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		
		return new Greeting(
				counter.incrementAndGet()
					, String.format(template, name)
		);
	}
	
	@GetMapping("/greeting_hateoas")
	public HttpEntity<Greeting> greetingRest(@RequestParam(value = "name", defaultValue = "World") String  name) {
		Greeting greeting = new Greeting(
				counter.incrementAndGet()
				, String.format(template, name)
				);
		
		greeting.add(
				linkTo(
					methodOn(GreetingController.class).greetingRest(name)
				)
				.withSelfRel()
		);
		
		return new ResponseEntity<Greeting>(greeting, HttpStatus.OK);
		
	}
	
	
}
