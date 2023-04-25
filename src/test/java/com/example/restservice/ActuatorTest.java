package com.example.restservice;

import java.util.Map;

import org.springframework.http.HttpStatus;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import static org.assertj.core.api.BDDAssertions.then;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SuppressWarnings("rawtypes")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource(properties = {"management.port = 0"})
@AutoConfigureMockMvc
public class ActuatorTest {
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@LocalServerPort
	private int port;
	
	@Value("${local.management.port}")
	private int mgt;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Test
	public void shouldReturn200WhenSendingRequestToController() throws Exception {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity("http://localhost:" + this.port + "/greeting", Map.class);
		
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
	}
	
	
	@Test
	public void shouldReturn404WhenSendingRequestToController() throws Exception {
		ResponseEntity<Map> entity = this.restTemplate.getForEntity("http://localhost:" + this.port + "/greetingabdc", Map.class);

		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		then(entity.getBody().get("code")).isEqualTo("SYS000404"); 
		
	}
	
	
	@Test
	public void shouldReturn200WhenSendingRequestToManagementEndpoint() throws Exception {
		ResponseEntity<Map> entity = 	
							this.restTemplate.getForEntity("http://localhost:" + this.mgt + "/actuator", Map.class);
		
		then(entity.getStatusCode()).isEqualTo(HttpStatus.OK);
		
		ResponseEntity<Map> entityHealth = 	
				this.restTemplate.getForEntity("http://localhost:" + this.mgt + "/actuator/health", Map.class);
		
		then(entityHealth.getBody().get("status")).isEqualTo("UP");
		
	}
	
	@Test
	public void sendRequestMockUseTest() throws Exception {
		
		logger.error("port : {}, mgt : {}", this.port, this.mgt);
		
		// not found use 
		mockMvc
			.perform(get("http://localhost:" + this.port + "/greetingaa"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("SYS000404"))
		;
		
		// greeting은 ResponseDataVo가 아님
		mockMvc
			.perform(get("http://localhost:" + this.port + "/greeting"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("SYS000500"))
		;
		
		mockMvc
			.perform(get("http://localhost:" + this.port + "/personneo"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("0"))
		;
		
		// MockMVC는 TestDispatcherServlet를 사용하여, 진짜 네트워크를 사용하는 것이 아니어어서, 전체 네트워크 스택 사용 불가하므로, 액츄에이터 사용할 수 없음
		// 전체 네트워크 스택 사용하기 위해서는 RestTemplate 사용
		mockMvc
			.perform(get("http://localhost:" + this.mgt + "/actuator"))
			// .andDo(print())
			.andExpect(status().isOk())
			.andExpect(jsonPath("code").value("SYS000404"))
		;
		
	}
	
	
}
