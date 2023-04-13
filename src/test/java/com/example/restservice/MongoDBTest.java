package com.example.restservice;

import static org.hamcrest.CoreMatchers.containsString;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.header;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.example.restservice.repository.PersonRepositoryMongo;

@SpringBootTest
@AutoConfigureMockMvc
public class MongoDBTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PersonRepositoryMongo personRepositoryMongo;
	
	@BeforeEach
	void setUp() throws Exception {
		personRepositoryMongo.deleteAll();
		
	}
	
	@Test
	void repositoryIdxExistTest() throws Exception {
		mockMvc
			.perform(get("/"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("$._links.personsM").exists())
		;
	}

	
	@Test
	void pathValidTest() throws Exception {
		mockMvc
			.perform(
					post("/personM")
						.content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")
			)
			.andDo(print())
			// 예외 발생 시 처리 방식 개선 하였으므로 Ok가 발생
			// .andExpect(status().isNotFound())
			.andExpect(status().isOk())
		;
		
	}
	
	@Test
	void entityCreateTest() throws Exception {
		
		mockMvc
			.perform(
					post("/persons").content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")
			)
			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", containsString("/persons/")) )
		;
		
	}
	
	
	@Test
	void entityCreateReponseTest() throws Exception {
		
		MvcResult mockResult 
					= mockMvc.perform(
									post("/persons")
										.content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")
					)
//					.andDo(print())
					.andExpect(status().isCreated())
					.andExpect(header().string("Location", containsString("/persons/")))
					.andReturn()
					;
		
		String location = mockResult.getResponse().getHeader("Location");
		
		
		mockMvc.perform(get(location))
//				.andDo(print())
				.andExpect(status().isOk())
				.andExpect(jsonPath("$.firstName").value("Frodo"))
				.andExpect(jsonPath("$.lastName").value("Baggins"))
		;
		
	}
	
	@Test
	void entityListByService() throws Exception {
		
		mockMvc.perform(
				post("/persons")
					.content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")
				)
//				.andDo(print())
				.andExpect(status().isCreated())
				.andExpect(header().string("Location", containsString("/persons/")))
				;
		
		mockMvc.perform(
				post("/persons")
				.content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins2\"}")
			)
//			.andDo(print())
			.andExpect(status().isCreated())
			.andExpect(header().string("Location", containsString("/persons/")))
			;
		
		
		mockMvc.perform(
					get("/persons/search/findByFirstName").param("name", "Frodo")
				)
//				.andDo(print())
				.andExpect(jsonPath("$._embedded.personsM").isNotEmpty())
				.andExpect(jsonPath("$._embedded.personsM.length()").value("2"))
			;
		
		mockMvc.perform(
				get("/persons/search/findByLastName").param("name", "Baggins")
			)
			.andDo(print())
			.andExpect(jsonPath("$._embedded.personsM").isNotEmpty())
			.andExpect(jsonPath("$._embedded.personsM.length()").value("1"))
			.andExpect(jsonPath("$._embedded.personsM[0].lastName").value("Baggins"))
			.andExpect(jsonPath("$._embedded.personsM[0].firstName").value("Frodo"))
		;
		
	}
	
	
	@Test
	void entityUpdate() throws Exception {
		MvcResult 
			mockResult = mockMvc.perform(post("/persons")
							.content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")
						)
						// .andDo(print())
						.andExpect(status().isCreated())
						.andReturn()
						;
		
		String location = mockResult.getResponse().getHeader("Location");
		
		mockMvc.perform(post("/persons")
				.content("{\"firstName\": \"Frodo2\", \"lastName\":\"Baggins2\"}")
			)
			// .andDo(print())
			.andExpect(status().isCreated())
			;
		
		mockMvc.perform(
				get("/persons/search/findByLastName").param("name", "Baggins")
			)
			// .andDo(print())
			.andExpect(jsonPath("$._embedded.personsM").isNotEmpty())
			.andExpect(jsonPath("$._embedded.personsM.length()").value("1"))
			.andExpect(jsonPath("$._embedded.personsM[0].lastName").value("Baggins"))
			.andExpect(jsonPath("$._embedded.personsM[0].firstName").value("Frodo"))
		;
		
		mockMvc.perform(patch(location).content("{\"firstName\": \"Frodo_Modify\"}"))
			.andDo(print())
			.andExpect(status().isNoContent())
		;
		
		mockMvc.perform(
				get("/persons/search/findByLastName").param("name", "Baggins")
			)
			// .andDo(print())
			.andExpect(jsonPath("$._embedded.personsM").isNotEmpty())
			.andExpect(jsonPath("$._embedded.personsM.length()").value("1"))
			.andExpect(jsonPath("$._embedded.personsM[0].lastName").value("Baggins"))
			.andExpect(jsonPath("$._embedded.personsM[0].firstName").value("Frodo_Modify"))
		;
		
		mockMvc.perform(
				get("/persons/search/findByLastName").param("name", "Baggins2")
			)
			// .andDo(print())
			.andExpect(jsonPath("$._embedded.personsM").isNotEmpty())
			.andExpect(jsonPath("$._embedded.personsM.length()").value("1"))
			.andExpect(jsonPath("$._embedded.personsM[0].lastName").value("Baggins2"))
			.andExpect(jsonPath("$._embedded.personsM[0].firstName").value("Frodo2"))
		;
		
	}
	
	
	@Test
	void deleteEntityTest() throws Exception {
		
		
		mockMvc.perform(post("/persons")
				.content("{\"firstName\": \"Frodo\", \"lastName\":\"Baggins\"}")
			)
			// .andDo(print())
			.andExpect(status().isCreated())
			;
		
		
		MvcResult 
				mockResult = mockMvc.perform(post("/persons")
								.content("{\"firstName\": \"Frodo2\", \"lastName\":\"Baggins\"}")
							)
							// .andDo(print())
							.andExpect(status().isCreated())
							.andReturn()
							;
			
		String location = mockResult.getResponse().getHeader("Location");
		
		mockMvc.perform(
				get("/persons/search/findByLastName").param("name", "Baggins")
			)
			// .andDo(print())
			.andExpect(jsonPath("$._embedded.personsM").isNotEmpty())
			.andExpect(jsonPath("$._embedded.personsM.length()").value("2"))
		;
		
		mockMvc.perform(
				delete(location)
		)
		.andDo(print())
		.andExpect(status().isNoContent())
		;
		
		mockMvc.perform(
				get("/persons/search/findByFirstName").param("name", "Frodo2")
			)
			// .andDo(print())
			.andExpect(jsonPath("$._embedded.personsM").isEmpty())
		;
		
		mockMvc.perform(get(location))
			.andDo(print())
			// 예외 발생 시 처리 방식 개선 하였으므로 Ok가 발생
					// .andExpect(status().isNotFound())
					.andExpect(status().isOk())
			
		;
		
		
	}
	
	
}
