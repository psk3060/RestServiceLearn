package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.restservice.repository.PersonRepository;
import com.example.restservice.service.model.Person;

@SpringBootTest
@AutoConfigureMockMvc
public class GeodeAccessTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PersonRepository personRepository;
	
	@BeforeEach
	public void setUp() throws Exception {
		// geode.deleteAll
		mockMvc.perform(delete("/people"))
			// .andDo(print())
		;
		
		// 직접 호출
		personRepository.deleteAll();

	}
	
	
	/**
	 * /person은 찾을 수 없음 
	 */
	@Test
	void notValidPathTest() throws Exception {
		mockMvc.perform(post("/person").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
//				.andDo(print())
				.andExpect(status().is4xxClientError())
				.andExpect(status().isNotFound());
	}
	
	@Test
	void mustResultIdxExistTest() throws Exception {
		mockMvc.perform(get("/"))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._links").exists());
		
	}
	
	
	/**
	 * Person의 id는 객체가 생성될 때마다 AtomicLong을 통해서 인메모리 상 계속 1씩 증가됨(삭제 된다해도 유지) 
	 */
	@Test
	void createTest() throws Exception {
		
		mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
									// .andDo(print())
									.andExpect(status().isCreated())
									.andExpect(header().string("Location", containsString("/people"))) // response Header의 Location에 people이 포함되어 있는지
									;
		
		/**
		 * LastName=Baggins
		 */
		mockMvc.perform(get("/people/search/findByLastName").param("name", "Baggins"))
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$._embedded.people").exists())
				.andExpect(jsonPath("$._embedded.people").isArray())
				// json idx는 0부터
				.andExpect(jsonPath("$._embedded.people[0].firstName").value("Frodo"))
				;
		
		
		List<Person> list = (List<Person>) personRepository.findAll();
		
		assertThat(list.size() == 1);
		
	}
	
	@Test
	void locationValidTest() throws Exception {
		MvcResult mockResult = mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
										// .andDo(print())
										.andExpect(status().isCreated())
										.andReturn()
										;
		
		String location = mockResult.getResponse().getHeader("Location");
		
		mockMvc.perform(get(location))
			// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Frodo"))
				.andExpect(jsonPath("$.lastName").value("Baggins"));
		
		
	}
	
	
	
	@Test
	void deleteAllTest() throws Exception {
		
		// base data setting
		mockMvc
			.perform(post("/people").content("{ \"firstName\": \"Frodo1\", \"lastName\":\"Baggins1\"}"))
			// .andDo(print())
			.andExpect(status().isCreated())
			;
		
		mockMvc
			.perform(post("/people").content("{ \"firstName\": \"Frodo2\", \"lastName\":\"Baggins2\"}"))
			// .andDo(print())
			.andExpect(status().isCreated());
		
		mockMvc
			.perform(post("/people").content("{ \"firstName\": \"Frodo3\", \"lastName\":\"Baggins3\"}"))
			// .andDo(print())
			.andExpect(status().isCreated());
		// base data setting		
		
		mockMvc
			.perform(get("/people").param("LIMIT", "ALL"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.people").isArray())
			.andExpect( jsonPath("$._embedded.people.length()").value("3") )
			;
		
		mockMvc.perform(delete("/people"))
//				.andDo(print())
		;
		
		mockMvc
			.perform(get("/people").param("LIMIT", "ALL"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.people").isArray())
			.andExpect( jsonPath("$._embedded.people.length()").value("3") )
			;
		
	}
	
	
	@Test
	void updateTest() throws Exception {
		
		MvcResult mockResult = mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
				// .andDo(print())
				.andExpect(status().isCreated())
				.andReturn()
				;
		
		String location = mockResult.getResponse().getHeader("Location");
		
		
		// patch를 통해 수정 => personRepository.deleteAll()하지 않을 경우 초기화 안 됨
		mockMvc.perform(
					patch(location).content("{ \"firstName\": \"Frodo Modify\"}")
				)
		// .andDo(print())
				.andExpect(status().isNoContent())
		;
		
		// repository 메소드를 통해서 조회가 되지 않음
		mockMvc.perform(
					get(location)
				)
				// .andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.lastName").value("Baggins"))
				.andExpect(jsonPath("$.firstName").value("Frodo Modify"))
		;
		
	}
	
	
	@Test
	void deleteSelectTest() throws Exception {
		MvcResult mvcResult = mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
				// .andDo(print())
			.andExpect(status().isCreated())
			.andReturn()
		;
		
		mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo2\", \"lastName\":\"Baggins2\"}"))
		// .andDo(print())
			.andExpect(status().isCreated())
		;
		
		mockMvc
			.perform(get("/people").param("LIMIT", "ALL"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.people").isArray())
			.andExpect( jsonPath("$._embedded.people.length()").value("2") )
			;
		

		String location = mvcResult.getResponse().getHeader("Location");
		
		mockMvc
			.perform(delete(location))
			// .andDo(print())
			// 제거 후 NoContent
			.andExpect(status().isNoContent());
			
		// get으로 조회
		mockMvc
			.perform(get(location))
			// .andDo(print())
			// not found
			.andExpect(status().isNotFound())
		;
		
		// 제거하였으니 한 개만 존재
		mockMvc
			.perform(get("/people").param("LIMIT", "ALL"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._embedded.people").isArray())
			.andExpect( jsonPath("$._embedded.people.length()").value("1") )
			;
		
		
	}
	
	@Test
	void updateUseServiceTest() throws Exception {
		
		MvcResult mockResult = mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}"))
				// .andDo(print())
				.andExpect(status().isCreated())
				.andReturn()
				;
		
		mockMvc.perform(post("/people").content("{ \"firstName\": \"Frodo\", \"lastName\":\"Baggins2\"}"))
			// .andDo(print())
			.andExpect(status().isCreated())
			.andReturn()
			;
		
		
		String location = mockResult.getResponse().getHeader("Location");
		
		/**
		 * 등록 역순으로 조회됨
		 */
		mockMvc.perform(get("/people/search/findByFirstName").param("name", "Frodo"))
			// .andDo(print())
			.andExpect(jsonPath("$..people.length()").value("2"))
			.andExpect(jsonPath("$._embedded.people[0].firstName").value("Frodo"))
			.andExpect(jsonPath("$._embedded.people[0].lastName").value("Baggins2"))
			.andExpect(jsonPath("$._embedded.people[1].firstName").value("Frodo"))
			.andExpect(jsonPath("$._embedded.people[1].lastName").value("Baggins"))
			
		;
		
		mockMvc.perform(
				patch(location).content("{ \"firstName\": \"Frodo Modify\"}")
			)
			// .andDo(print())
			.andExpect(status().isNoContent())
		;
		
		mockMvc.perform(
				get(location)
			)
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$.lastName").value("Baggins"))
			.andExpect(jsonPath("$.firstName").value("Frodo Modify"))
			;
		
		mockMvc.perform(get("/people/search/findByLastName").param("name", "Baggins"))
			// .andDo(print())
			.andExpect(jsonPath("$._embedded.people.length()").value("1"))
			.andExpect(jsonPath("$._embedded.people[0].firstName").value("Frodo Modify"))
			.andExpect(jsonPath("$._embedded.people[0].lastName").value("Baggins"))
		;
		
		
		
		
	}
	
	
}
