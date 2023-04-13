package com.example.restservice.service.model;

import java.io.Serializable;

import org.springframework.hateoas.RepresentationModel;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * java 12부터 이용가능한 record
 * - JSON 맞추기 위해 생성 
 */
public class Greeting extends RepresentationModel<Greeting> implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -1221545736313278364L;
	
	private Long id;
	private String content;
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	
	public Greeting() {}
	
	@JsonCreator
	public Greeting(@JsonProperty("content") String content) {
		this.content = content;
	}
	
	@JsonCreator
	public Greeting(Long id, @JsonProperty("content") String content) {
		this.id = id;
		this.content = content;
	}
	
}