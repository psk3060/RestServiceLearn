package com.example.restservice;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

@SpringBootTest
@AutoConfigureMockMvc
public class GreetingTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Test
	void greeting() throws Exception {
		
		mockMvc.perform(get("/greeting"))
//			.andDo(print()) // 모든 정보 Log 출력(가능하면 추가)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").value("Hello, World"));
			
	}
	
	
	@Test
	void greetingWithName() throws Exception {
		
		mockMvc.perform(get("/greeting").param("name", "Spring"))
//			.andDo(print()) // 모든 정보 Log 출력(가능하면 추가)
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.content").value("Hello, Spring"));
			
	}
	
}
