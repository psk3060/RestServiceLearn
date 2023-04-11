package com.example.restservice.service.model;

import java.io.Serializable;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;


@JsonIgnoreProperties(ignoreUnknown = true)
public class QuoteResource implements Serializable {
	
	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5984066299932173135L;

	private String type;
	
	private Quote value;
	
	public QuoteResource() {}
	
	public QuoteResource(Quote value, String type) {
		this.type = type;
		this.value = value;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Quote getValue() {
		return value;
	}

	public void setValue(Quote value) {
		this.value = value;
	}
	
	@Override
	public boolean equals(Object obj) {
		
		if( this == obj ) {
			return true;
			
		}
		
		if( !(obj instanceof QuoteResource) ) {
			return false;
			
		}
		
		QuoteResource quoteObj = (QuoteResource)obj;
		
		return Objects.equals(this.type, quoteObj.type) && Objects.equals(this.value, quoteObj.value);
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(this.type, this.value);
	}
	
	@Override
	public String toString() {
		return "QuoteResource{type=\'" + type + "\', value=\'" + value + "\'}";
	}
	
}
