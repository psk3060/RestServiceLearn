package com.example.restservice.controller;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.repository.QuoteRepository;
import com.example.restservice.service.model.Quote;
import com.example.restservice.service.model.QuoteResource;

@RestController
public class QuoteController {
	
	private final static Quote NONE = new Quote("None");
	private final static Random RANDOMIZER = new Random();
	
	@Autowired
	private QuoteRepository quoteRepository;
	
	@GetMapping("/api")
	public List<QuoteResource> getAll() {
		return quoteRepository.findAll().stream()
					.map(quote -> new QuoteResource(quote, "success"))
					.collect(Collectors.toList());
	}
	
	@GetMapping("/api/{id}")
	public QuoteResource getOne(@PathVariable Long id) {
		
		return quoteRepository.findById(id)
					.map(quote -> new QuoteResource(quote, "success"))
					.orElse( new QuoteResource(NONE, "Quote " + id + " does not exist") ) ;
					
	}
	
	private Long nextLong(long lowerRange, long upperRange) {
		return (long) (RANDOMIZER.nextDouble() * (upperRange - lowerRange)) + lowerRange;
	}
	
	@GetMapping("/api/random")
	public QuoteResource getRandomOne() {
		return getOne(nextLong(1, quoteRepository.count() + 1));
	}
	
	
}
