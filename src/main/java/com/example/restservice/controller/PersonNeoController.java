package com.example.restservice.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.core.ResponseDataVo;
import com.example.restservice.exception.ResultServiceException;
import com.example.restservice.service.PersonNeoService;
import com.example.restservice.service.model.PersonNeoVO;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@RestController
public class PersonNeoController {
	
	private Logger logger = LoggerFactory.getLogger(PersonNeoController.class);
	
	@Autowired
	private PersonNeoService personNeoService;
	
	@GetMapping("/personneo/{id}")
	public @ResponseBody ResponseEntity<ResponseDataVo> selectUserDetail(@PathVariable Long id) throws Exception {
		
		String code = "0";
		String message = "상세 조회가 완료되었습니다.";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			PersonNeoVO person = personNeoService.select(id);
			
			data.put("result", person);
			
		} catch(Exception e) {
			
			code = "999";
			message = "시스템에러";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
		
	}
	
	
	@GetMapping("/personneo")
	public @ResponseBody ResponseEntity<ResponseDataVo> selectUserList(Map<String, Object> params) {
		
		String code = "0";
		String message = "조회가 완료되었습니다.";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			List<PersonNeoVO> dataList = personNeoService.selectListAll();
			
			Long totalCount = personNeoService.countAll();
			
			data.put("dataList", dataList);
			data.put("totalCount", totalCount);
			
		} catch(Exception e) {
			
			logger.error("exception {}", e.toString());
			
			code = "999";
			message = "시스템에러";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
		
	}
	
	@PostMapping("/personneo")
	public @ResponseBody ResponseEntity<ResponseDataVo> addPerson(@RequestBody PersonNeoVO vo) throws Exception {
		String code = "0";
		String message = "회원 추가가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			PersonNeoVO saveUser = personNeoService.insert(vo);
			
			// ID가 0부터 시작
			/*
			if( saveUser.getId() <= 0 ) {
				saveUser = null;
				
			}
			*/
			
			if( saveUser == null ) {
				throw new ResultServiceException("100", "회원 등록 실패");
				
			}
			
			data.put("result", saveUser);
			
		} catch(Exception e) {
			
			code = "999";
			message = "시스템에러";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
	}
	
	@PatchMapping("/personneo")
	public @ResponseBody ResponseEntity<ResponseDataVo> updatePerson(@RequestBody PersonNeoVO vo) throws Exception {
		String code = "0";
		String message = "회원 수정이 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			PersonNeoVO saveUser = personNeoService.update(vo);
			
			data.put("result", saveUser);
			
		} catch(Exception e) {
			
			code = "999";
			message = "시스템에러";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
	}
	
	@DeleteMapping("/personneo/{id}")
	public @ResponseBody ResponseEntity<ResponseDataVo> deletePerson(@PathVariable Long id) throws Exception {
		String code = "0";
		String message = "회원 수정이 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			personNeoService.deleteByKey(id);
			
			data.put("result", id);
			
		} catch(Exception e) {
			
			code = "999";
			message = "시스템에러";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
	}
	
	/*
	@PostMapping("/personneo_hateoas")
	public @ResponseBody ResponseEntity<PersonNeoVO> addPersonHateoas(@RequestBody PersonNeoVO vo) throws Exception {
		
		PersonNeoVO saveUser = new PersonNeoVO();
		
		saveUser.setFirstName(vo.getFirstName());
		saveUser.setLastName(vo.getLastName());
		
		saveUser = personNeoService.insert(saveUser);
		
		if( saveUser == null ) {
			throw new ResultServiceException("100", "회원 등록 실패");
			
		}
		
		saveUser
			.add(
				linkTo(
					methodOn(PersonNeoController.class).addPersonHateoas(saveUser)
				)
				.withSelfRel()
			);
		
		
		return new ResponseEntity<PersonNeoVO>(saveUser, HttpStatusCode.valueOf(200));
	}
	*/
	
}
