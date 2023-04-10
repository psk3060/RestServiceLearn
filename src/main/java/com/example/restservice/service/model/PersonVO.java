package com.example.restservice.service.model;

import java.io.Serializable;

import org.springframework.data.annotation.Id;

/**
 * id를 객체에서 증가시키지 않음 
 * - MongoDB 연동 테스트에서 사용
 */
public class PersonVO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 4758758174271675452L;

	/**
	 * Id를 통해 자동 생성
	 */
	@Id
	private String id;
	
	private String firstName;
	
	private String lastName;


	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
}
