package com.example.restservice.config;

import java.time.Duration;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

import com.example.restservice.repository.QuoteRepository;
import com.example.restservice.service.model.Quote;
import com.example.restservice.service.model.QuoteResource;

@Configuration
public class DefaultConfig {
	
	private Logger logger = LoggerFactory.getLogger(DefaultConfig.class);
	
	/**
	 * 연결시간 5초, 읽기 시간 5초 제한 
	 * @param builder
	 * @return
	 */
	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder) {
		return builder
				.setConnectTimeout(Duration.ofSeconds(5))
	            .setReadTimeout(Duration.ofSeconds(5))
	            .build();
		
	}
	
	/**
	 *  CommandLineRunner : SpringBoot 시작과 동시에 실행하는 로직
	 * @param repository
	 * @return
	 */
	@Bean
	CommandLineRunner init(QuoteRepository repository) {
		return args -> {
			repository.save(new Quote("Working with Spring Boot is like pair-programming with the Spring developers."));
			repository.save(new Quote("With Boot you deploy everywhere you can find a JVM basically."));
			repository.save(new Quote("Spring has come quite a ways in addressing developer enjoyment and "
					+ "ease of use since the last time I built an application using it."));
			repository.save(new Quote(
					"Previous to Spring Boot, I remember XML hell, confusing set up, and " + "many hours of frustration."));
			repository.save(new Quote("Spring Boot solves this problem. It gets rid of XML and wires up "
					+ "common components for me, so I don't have to spend hours scratching my "
					+ "head just to figure out how it's all pieced together."));
			repository.save(new Quote("It embraces " + "convention over configuration, providing an experience on par with "
					+ "frameworks that excel at early stage development, such as Ruby on " + "Rails."));
			repository.save(new Quote("The real benefit of Boot, however, is that it's just Spring. That "
					+ "means any direction the code takes, regardless of complexity, I know " + "it's a safe bet."));
			repository.save(new Quote("I don't worry about my code scaling. Boot allows the "
					+ "developer to peel back the layers and customize when it's appropriate "
					+ "while keeping the conventions that just work."));
			repository.save(new Quote("So easy it is to switch container in #springboot."));
			repository.save(new Quote("Really loving Spring Boot, makes stand alone Spring apps easy."));
			repository.save(new Quote("I have two hours today to build an app from scratch. @springboot to the rescue!"));
			repository.save(new Quote("@springboot with @springframework is pure productivity! Who said in #java one has "
					+ "to write double the code than in other langs? #newFavLib"));
			
		};
	}
	
	
	@Bean
	public CommandLineRunner run(RestTemplate restTemplate) throws Exception {
		
		return args -> {
			
			try {
				QuoteResource quote = restTemplate.getForObject("http://localhost:8080/api/random", QuoteResource.class);
				
				logger.error("run String quote {}", quote);
				
			} catch(Exception e) {
				logger.error(e.toString());
				
			}
			
		};
	}
	
}
