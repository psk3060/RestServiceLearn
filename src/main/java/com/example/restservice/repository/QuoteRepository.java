package com.example.restservice.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restservice.service.model.Quote;

/**
 * Quote Test 용  
 */
public interface QuoteRepository extends JpaRepository<Quote, Long> {
	
}
