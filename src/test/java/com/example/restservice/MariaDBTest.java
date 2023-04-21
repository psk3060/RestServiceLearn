package com.example.restservice;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.sql.Connection;

import javax.sql.DataSource;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;

import com.example.restservice.repository.UserRepository;
import com.example.restservice.service.UserService;
import com.example.restservice.service.model.User;
import com.example.restservice.util.TestUtil;


@SpringBootTest
@AutoConfigureMockMvc
public class MariaDBTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass()); 
	
	@Autowired
	DataSource dataSource;
	
	@Autowired 
	UserRepository userRepository;
	
	@Autowired
	UserService userService;
	
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
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
		User newUser = new User();
		
		newUser.setEmail("a@n.com");
		newUser.setName("temp");
		
		newUser = userRepository.save(newUser);
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(1L), Long.valueOf(count));
		
		assertThat((newUser.getId() != null) && (newUser.getId() > 0));
		
		User getUser = userRepository.findById(newUser.getId()).get();
		
		assertEquals(newUser.getEmail(), getUser.getEmail());
		assertEquals(newUser.getName(), getUser.getName());
		
	}
	
	@Test
	void addUserControllerTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
		
		// 
		
		mockMvc
			.perform(post("/user").param("email", "a@n.com").param("name", "temp"))
			.andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(
					jsonPath("message")
						.value("회원 추가가 완료되었습니다.")
			)
		;

		count = userRepository.count();
		
		assertEquals(Long.valueOf(1L), Long.valueOf(count));
		
	}
	
	
	@Test
	void modifyControllerTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
		User newUser = new User();
		
		newUser.setEmail("a@n.com");
		newUser.setName("temp");
		
		long id = userService.addUser(newUser).getId();
		
		User newUser2 = new User();
		
		newUser2.setEmail("a2@n.com");
		newUser2.setName("temp2");
		
		userService.addUser(newUser2).getId();
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(2L), Long.valueOf(count));
		
		assertEquals(newUser.getEmail(), "a@n.com");
		assertEquals(newUser.getName(), "temp");
		
		assertEquals(newUser2.getEmail(), "a2@n.com");
		assertEquals(newUser2.getName(), "temp2");
		
		// update 
		newUser.setEmail("a_modify@n.com");
		
		mockMvc
			.perform(patch("/user")
					.contentType(MediaType.APPLICATION_JSON)
					.content(TestUtil.convertObjectToJsonBytes(newUser))
			)
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
			.andExpect(jsonPath("data.result.id").exists())
			.andExpect(jsonPath("data.result.id").value(id))
		;
		
		
		mockMvc
			.perform(get("/user/" + id))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.result").exists())
			.andExpect(jsonPath("data.result.id").value(id))
			.andExpect(jsonPath("data.result.name").value(newUser.getName()))
			.andExpect(jsonPath("data.result.email").value(newUser.getEmail()))
		;
		
	}

	@Test
	void deleteControllerTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
		User newUser = new User();
		
		newUser.setEmail("a@n.com");
		newUser.setName("temp");
		
		long id = userService.addUser(newUser).getId();
		
		User newUser2 = new User();
		
		newUser2.setEmail("a2@n.com");
		newUser2.setName("temp2");
		
		userService.addUser(newUser2);
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(2L), Long.valueOf(count));
		
		assertEquals(newUser.getEmail(), "a@n.com");
		assertEquals(newUser.getName(), "temp");
		
		assertEquals(newUser2.getEmail(), "a2@n.com");
		assertEquals(newUser2.getName(), "temp2");
		
		mockMvc
			.perform(delete("/user/" + id))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.deleteId").value(id))
		;
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(1L), Long.valueOf(count));
		
	}
	
	/**
	 * deleteAll은 서비스로도, 컨트롤러에도 등록하지 않을 것이므로 repository로 호출 
	 */
	@Test
	void deleteAllTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
		User newUser = new User();
		
		newUser.setEmail("a@n.com");
		newUser.setName("temp");
		
		userService.addUser(newUser);
		
		User newUser2 = new User();
		
		newUser2.setEmail("a2@n.com");
		newUser2.setName("temp2");
		
		userService.addUser(newUser2);
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(2L), Long.valueOf(count));
		
		userRepository.deleteAll();
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
	}
	
	@Test
	void selectAllControllerTest() throws Exception {
		
		long count = userRepository.count();
		
		assertEquals(Long.valueOf(0L), Long.valueOf(count));
		
		User newUser = new User();
		
		newUser.setEmail("a@n.com");
		newUser.setName("temp");
		
		userService.addUser(newUser);
		
		User newUser2 = new User();
		
		newUser2.setEmail("a2@n.com");
		newUser2.setName("temp2");
		
		userService.addUser(newUser2);
		
		count = userRepository.count();
		
		assertEquals(Long.valueOf(2L), Long.valueOf(count));
		
		mockMvc
			.perform(get("/user"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
			.andExpect(jsonPath("data").exists())
			.andExpect(jsonPath("data.dataList").exists())
			.andExpect(jsonPath("data.totalCount").exists())
			.andExpect(jsonPath("data.totalCount").value(2L))
			;
		
		
	}
	
}
