package com.example.restservice.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservice.repository.UserRepository;
import com.example.restservice.service.model.User;

/**
 * TODO selectSearch Param 추가
 */
@Service("userService")
public class UserServiceImpl implements UserService {
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public User selectUserById(Integer id) throws Exception {
		return userRepository.findById(id).get();
	}

	@Override
	public User addUser(User user) throws Exception {
		return userRepository.save(user);
	}

	@Override
	public User updateUser(User user) throws Exception {
		return userRepository.save(user);
	}

	@Override
	public void deleteUser(User user) throws Exception {
		userRepository.deleteById(user.getId());
	}

	@Override
	public List<User> selectAll() throws Exception {
		return StreamSupport
				  .stream(userRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		
	}
	
	@Override
	public long totalCount() throws Exception {
		return userRepository.count();
	}
	
}
