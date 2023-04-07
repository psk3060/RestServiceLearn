package com.example.restservice;

import static org.hamcrest.CoreMatchers.endsWith;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GeodeAccessTest {
	
	@Autowired
	MockMvc mockMvc;
	
	/**
	 * /person은 찾을 수 없음 
	 */
	@Test
	void notValidPathTest() throws Exception {
		mockMvc.perform(post("/person").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(status().isNotFound());
	}
	
	@Test
	void createTest() throws Exception {
		mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
			.andDo(print())
			.andExpect(status().isCreated());
		
		/**
		 * LastName=Baggins
		 * , json요소 중 people가 존재해야 하고
		 * , people[0].firstName이 Frodo여야 하며
		 * , 링크의 끝이 /people/1 이어야 함(첫 번째 이므로).
		 */
		mockMvc.perform(get("/people/search/findByLastName?name={name}", "Baggins"))
				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.people").exists())
				.andExpect(jsonPath("$._embedded.people[0].firstName").value("Frodo"))
				.andExpect(jsonPath("$._embedded.people[0]._links.self.href").value(endsWith("1")))
				;
		
	}
	
	
	
}
