package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restservice.repository.UserRepository;
import com.example.restservice.service.model.User;


@SpringBootTest
@AutoConfigureMockMvc
public class MariaDBAccessTest {
	
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	MockMvc mockMvc;
	
	@BeforeEach
	void setUp() {
		userRepository.deleteAll();
		
	}
	
	@Test
	void connTest() throws Exception {
		Connection c = dataSource.getConnection();
		
		assertThat(c != null);
		
	}
	
	
	@Test 
	void addUserTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(count, 0);
		
		User newUser = new User();
		
		newUser.setEmail("a@n.com");
		newUser.setName("temp");
		
		newUser = userRepository.save(newUser);
		
		count = userRepository.count();
		
		assertEquals(count, 1);
		assertThat((newUser.getId() != null) && (newUser.getId() > 0));
		
		User getUser = userRepository.findById(newUser.getId()).get();
		
		assertEquals(newUser.getEmail(), getUser.getEmail());
		assertEquals(newUser.getName(), getUser.getName());
		
	}
	
	@Test
	void addUserControllerTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(count, 0);
		
		mockMvc
			.perform(post("/user").param("email", "a@n.com").param("name", "temp"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("message").value("회원 추가가 완료되었습니다."))
		;
		
		count = userRepository.count();
		
		assertEquals(count, 1);
		
	}
}
