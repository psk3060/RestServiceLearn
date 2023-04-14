package com.example.restservice.service.model;

import java.io.Serializable;

import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;


/**
 * Neo4j에서 사용하는 Person 
 */
@Node
public class PersonNeoVO implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7664392470690242399L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String firstName;
	
	private String lastName;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

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
