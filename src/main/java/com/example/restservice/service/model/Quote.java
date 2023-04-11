package com.example.restservice.service.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;

@Entity
@JsonIgnoreProperties(ignoreUnknown = true)
public class Quote implements Serializable {
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -7155003870570612568L;

	@Id
	@GeneratedValue
	private Long id;
	
	private String quote;
	
	public Quote() {
		
	}
	
	public Quote(String quote) {
		this.quote = quote;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getQuote() {
		return quote;
	}

	public void setQuote(String quote) {
		this.quote = quote;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if( this == obj ) {
			return true;
		}
		
		if( !(obj instanceof Quote)) {
			return false;
			
		}
		
		final Quote o = (Quote)obj;

		return Objects.equals(this.id, o.id) && Objects.equals(this.quote, o.quote);
		
	}
	
	
	@Override
	public int hashCode() {
		return Objects.hash(id, quote);
	}
	
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		return "Quote{id=\'" + id + "\', quote=\'" + quote + "\'}";
	}
	
}