package com.example.restservice.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.core.ResponseDataVo;
import com.example.restservice.exception.ResultServiceException;
import com.example.restservice.repository.UserRepository;
import com.example.restservice.service.model.User;

@RestController
public class UserController {
	
	@Autowired
	private UserRepository userRepository;
	
	@PostMapping(path= "/user")
	public @ResponseBody ResponseEntity<ResponseDataVo> addUser(
							@RequestParam String name
							, @RequestParam String email ) {
		
		String code = "0";
		String message = "회원 추가가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User user = new User();
			
			user.setName(name);
			user.setEmail(email);
			
			User saveUser = userRepository.save(user);
			
			if( saveUser.getId() <= 0 ) {
				saveUser = null;
				
			}
			
			if( saveUser == null ) {
				throw new ResultServiceException("100", "회원 등록 실패");
				
			}
			
			data.put("result", saveUser);
			
		} catch(Exception e) {
			
			code = "999";
			message = "SystemError";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
		
	}
	
	@PatchMapping(path = "/user")
	public @ResponseBody ResponseEntity<ResponseDataVo> updateUser(User user)  {
		String code = "0";
		String message = "수정이 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
	}
	
	/**
	 * TODO
	 * @param id
	 */
	@DeleteMapping(path="/user")
	public @ResponseBody ResponseEntity<ResponseDataVo> deleteUser(Integer id)  {
		String code = "0";
		String message = "회원 삭제가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		
		
		return new ResponseEntity<>(new ResponseDataVo(code, message, data), HttpStatusCode.valueOf(200));
	}
	
	
}
