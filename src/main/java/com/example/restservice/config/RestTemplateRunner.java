package com.example.restservice.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.example.restservice.service.model.QuoteResource;

/**
 * CommandLineRunner : 스프링부트 시작 시 자동으로 수행되는 인터페이스
 */
// @Component
public class RestTemplateRunner /*implements CommandLineRunner*/ {
	
	private Logger logger = LoggerFactory.getLogger(RestTemplateRunner.class);
	
	@Autowired
	private RestTemplate restTemplate;
	
	// @Override
	public void run(String... args) throws Exception {
		String url = "http://localhost:8080/api/random";
		
		QuoteResource resource = restTemplate.getForObject(url, QuoteResource.class);
		
		logger.error("resource toString {}", resource.toString());
		
	}
	
}
