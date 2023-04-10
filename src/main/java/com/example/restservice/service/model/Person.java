package com.example.restservice.service.model;

import java.io.Serializable;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.data.annotation.Id;
import org.springframework.data.gemfire.mapping.annotation.Region;

import lombok.Data;

/**
 * Person Entity(People 정보)
 * Geode 연동 테스트에서 사용
 */
@Region("People")
@Data
public class Person implements Serializable {
	
	private static AtomicLong counter = new AtomicLong();
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 7529462896789234421L;
	
	/**
	 * PK seq 자동 증가
	 */
	@Id
	private Long id;
	
	private String firstName;
	private String lastName;
	
	public Person() {
		this.id = counter.incrementAndGet();
	}
	
	
}
