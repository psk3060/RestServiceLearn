package com.example.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.net.URI;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;

import com.example.restservice.service.model.Greeting;
import com.google.common.net.HttpHeaders;

@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class CorsTest {
	
	@LocalServerPort
	int port;
	
	@Autowired
	TestRestTemplate restTemplate;
	
	@Test
	public void corsWithAnnotation() throws Exception {
		
		ResponseEntity<Greeting> entity 
						= this.restTemplate.exchange(
								RequestEntity
									.get(uri("/greeting-cors"))
									.header(HttpHeaders.ORIGIN, "http://localhost:8080").build()
							, Greeting.class);
		
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		
		assertEquals("http://localhost:8080", entity.getHeaders().getAccessControlAllowOrigin());
		
		Greeting greeting = entity.getBody();
		
		assertEquals("Hello, World!", greeting.getContent());
		
	}
	
	@Test
	public void corsWithJavaconfig() {
		ResponseEntity<Greeting> entity 
						= this.restTemplate.exchange(
								RequestEntity.get(
									uri("/greeting-javaconfig")
								)
								.header(HttpHeaders.ORIGIN, "http://localhost:8080").build()
							, Greeting.class);
		
		assertEquals(HttpStatus.OK, entity.getStatusCode());
		
		assertEquals("http://localhost:8080", entity.getHeaders().getAccessControlAllowOrigin());
		
		Greeting greeting = entity.getBody();
		assertEquals("Hello, World!", greeting.getContent());
		
	}
	
	
	private URI uri(String path) {
		return restTemplate.getRestTemplate().getUriTemplateHandler().expand(path);
	}
	
	
}
