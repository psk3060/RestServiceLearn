package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.restservice.service.model.PersonVO;


@RepositoryRestResource(path = "persons", collectionResourceRel = "personsM")
public interface PersonRepositoryMongo extends MongoRepository<PersonVO, String> {
	
	List<PersonVO> findByLastName(@Param("name") String name );
	
	List<PersonVO> findByFirstName(@Param("name") String name );
	
}
