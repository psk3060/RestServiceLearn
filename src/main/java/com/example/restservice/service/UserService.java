package com.example.restservice.service;

import java.util.List;

import com.example.restservice.service.model.User;

public interface UserService {
	
	User selectUserById(Integer id) throws Exception;
	
	User addUser(User user) throws Exception;
	
	User updateUser(User user) throws Exception;
	
	void deleteUser(User user) throws Exception;
	
	List<User> selectAll() throws Exception;
	
}
