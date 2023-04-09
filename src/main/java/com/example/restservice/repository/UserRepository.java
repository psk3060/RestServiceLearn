package com.example.restservice.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.example.restservice.service.model.User;

@Repository("userRepository")
public interface UserRepository extends CrudRepository<User, Integer> {
	
}
