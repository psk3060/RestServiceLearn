package com.example.restservice;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.restdocs.AutoConfigureRestDocs;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import com.example.restservice.repository.PersonNeo4jRepository;
import com.example.restservice.service.PersonNeoService;
import com.example.restservice.service.model.PersonNeoVO;
import com.example.restservice.util.TestUtil;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureRestDocs(outputDir = "target/snippets")
public class Neo4jTest {
	
	@Autowired
	MockMvc mockMvc;
	
	@Autowired
	PersonNeo4jRepository personRepository;
	
	@Autowired
	PersonNeoService personNeoService;
	
	@BeforeEach
	void setUp() throws Exception {
		personRepository.deleteAll();
		
	}
	
	@Test
	void isEmptyTest() throws Exception {
		
		String value = "  	 ABC";
		
		// 앞 공백 제거
		value = value.replaceAll("$?\\s", "");
		// 뒷 공백 제거
		value = value.replaceAll("\\s+$", "");
		
		assertEquals("ABC", value);
		
	}
	
	
	@Test
	void insertTest() throws Exception {
		
		/*
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("0"))
		;
		*/
		
		PersonNeoVO vo = new PersonNeoVO();
		
		vo.setFirstName(TestUtil.getUniqueString());
		vo.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
		mockMvc
			.perform(get("/personneo"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isNotEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("1"))
		;
		
	}
	
	/*
	@Test
	void updateTest() throws Exception {
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("0"))
		;
			
		PersonNeoVO vo = new PersonNeoVO();
		
		vo.setFirstName(TestUtil.getUniqueString());
		vo.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
	
		PersonNeoVO vo2 = new PersonNeoVO();
		
		String vo2FirstName = TestUtil.getUniqueString(); 
		
		vo2.setFirstName(vo2FirstName);
		vo2.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo2)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isNotEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("2"))
		;
		
		PersonNeoVO vo3 =  personRepository.findByFirstName(vo2FirstName).get(0);
		
		vo3.setFirstName("ModifyFirstName");
		
		mockMvc
			.perform(patch("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo3)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
			.andExpect(jsonPath("data.result.firstName").value("ModifyFirstName"))
		;
		
		
	}
	
	@Test
	void deleteOneTest() throws Exception {
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("0"))
		;
			
		PersonNeoVO vo = new PersonNeoVO();
		
		vo.setFirstName(TestUtil.getUniqueString());
		vo.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
	
		PersonNeoVO vo2 = new PersonNeoVO();
		
		String vo2FirstName = TestUtil.getUniqueString(); 
		
		vo2.setFirstName(vo2FirstName);
		vo2.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo2)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isNotEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("2"))
		;
		
		PersonNeoVO vo3 =  personRepository.findByFirstName(vo2FirstName).get(0);
		
		mockMvc
			.perform(delete("/personneo/" + vo3.getId()))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
			.andExpect(jsonPath("data.result").value(vo3.getId()))
		;
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isNotEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value(1))
		;
		
	}
	
	@Test
	void deleteAllTest() throws Exception {
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("0"))
		;
			
		PersonNeoVO vo = new PersonNeoVO();
		
		vo.setFirstName(TestUtil.getUniqueString());
		vo.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
	
		PersonNeoVO vo2 = new PersonNeoVO();
		
		vo2.setFirstName(TestUtil.getUniqueString());
		vo2.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo2)))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
		;
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isNotEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("2"))
		;
		
		personNeoService.deleteAll();
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value(0))
		;
		
	}
	
	@Test
	void insertHateoasTest() throws Exception {
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("0"))
		;
			
		PersonNeoVO vo = new PersonNeoVO();
		
		vo.setFirstName(TestUtil.getUniqueString());
		vo.setLastName(TestUtil.getUniqueString());
		
		mockMvc
			.perform(post("/personneo_hateoas").contentType(MediaType.APPLICATION_JSON).content(TestUtil.convertObjectToJsonBytes(vo)))
			.andDo(print())
			.andExpect(status().isOk())
		;
		
		
		mockMvc
			.perform(get("/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.dataList").isNotEmpty())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value("1"))
		;
		
	}
	*/
	
}
