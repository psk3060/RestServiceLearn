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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.restservice.core.ResponseDataVo;
import com.example.restservice.exception.ResultServiceException;
import com.example.restservice.service.UserService;
import com.example.restservice.service.model.User;

/**
 * TODO httpStatusCode 개선(현재는 무조건 200), 객체로 관리
 */
@RestController
public class UserController {
	
	private Logger logger = LoggerFactory.getLogger(UserController.class);
	
	@Autowired
	private UserService userService;
	
	@PostMapping(path= "/user")
	public ResponseDataVo addUser(
							@RequestParam String name
							, @RequestParam String email ) {
		
		String code = "0";
		String message = "회원 추가가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User user = new User();
			
			user.setName(name);
			user.setEmail(email);
			
			User saveUser = userService.addUser(user);
			
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
		
		return new ResponseDataVo(code, message, data);
		
	}
	
	@PatchMapping(path = "/user")
	public ResponseDataVo updateUser(@RequestBody User user)  {
		
		String code = "0";
		String message = "수정이 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User saveUser = userService.updateUser(user);
			
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
		
		return new ResponseDataVo(code, message, data);
	}
	
	/**
	 * @param id
	 */
	@DeleteMapping(path="/user/{id}")
	public @ResponseBody ResponseDataVo deleteUser(@PathVariable Integer id)  {
		String code = "0";
		String message = "회원 삭제가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User user = userService.selectUserById(id);
			
			userService.deleteUser(user);
			
			data.put("deleteId", id);
			
		} catch(Exception e) {
			
			code = "999";
			message = "SystemError";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseDataVo(code, message, data);
	}
	
	@GetMapping(path = "/user")
	public ResponseDataVo selectUserList(Map<String, Object> params) {
		
		String code = "0";
		String message = "회원 조회가 완료되었습니다.";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			List<User> dataList = userService.selectAll();
			
			Long totalCount = userService.totalCount();
			
			data.put("dataList", dataList);
			data.put("totalCount", totalCount);
			
		} catch(Exception e) {
			
			code = "999";
			message = "SystemError";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
			}
			
			data = null;
			
		}
		
		return new ResponseDataVo(code, message, data);
		
	}
	
	
	@GetMapping(path = "/user/{id}")
	public
		ResponseDataVo selectUser(@PathVariable Integer id) {
		
		String code = "0";
		String message = "회원 상세 조회가 완료되었습니다.";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User result = userService.selectUserById(id); 
			
			data.put("result", result);
			
		} catch(Exception e) {
			
			code = "999";
			message = "SystemError";
			
			if( e instanceof ResultServiceException ) {
				code = ((ResultServiceException)e).getErrorCode();
				message = ((ResultServiceException)e).getErrorMsg();
				
			}
			
			data = null;
			
		}
		
		return new ResponseDataVo(code, message, data);
		
	}
	
	
	@Deprecated
	public @ResponseBody ResponseEntity<ResponseDataVo> addUser_20230421(
							@RequestParam String name
							, @RequestParam String email ) {
		
		String code = "0";
		String message = "회원 추가가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User user = new User();
			
			user.setName(name);
			user.setEmail(email);
			
			User saveUser = userService.addUser(user);
			
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
	
	@Deprecated
	public @ResponseBody ResponseEntity<ResponseDataVo> updateUser_20230421(@RequestBody User user)  {
		
		String code = "0";
		String message = "수정이 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User saveUser = userService.updateUser(user);
			
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
	
	@Deprecated
	public
		@ResponseBody ResponseEntity<ResponseDataVo> 
			selectUserList_20230421(Map<String, Object> params) {
		
		String code = "0";
		String message = "회원 조회가 완료되었습니다.";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			List<User> dataList = userService.selectAll();
			
			Long totalCount = userService.totalCount();
			
			data.put("dataList", dataList);
			data.put("totalCount", totalCount);
			
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
	
	/**
	 * ReturnType 변경으로 인한 Deprecated
	 * @param id
	 * @return
	 */
	@Deprecated
	public
		@ResponseBody ResponseEntity<ResponseDataVo> selectUser_20230421(@PathVariable Integer id) {
		
		String code = "0";
		String message = "회원 상세 조회가 완료되었습니다.";
		
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User result = userService.selectUserById(id); 
			
			data.put("result", result);
			
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
	
	
	/**
	 * 
	 * @param id
	 */
	@Deprecated
	public @ResponseBody ResponseEntity<ResponseDataVo> deleteUser_20230421(@PathVariable Integer id)  {
		String code = "0";
		String message = "회원 삭제가 완료되었습니다.";
		Map<String, Object> data = new HashMap<String, Object>();
		
		try {
			
			User user = userService.selectUserById(id);
			
			userService.deleteUser(user);
			
			data.put("deleteId", id);
			
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
	
	
	
}
