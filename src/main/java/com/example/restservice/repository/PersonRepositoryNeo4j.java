package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.restservice.service.model.PersonNeoVO;

@RepositoryRestResource(/*path = "personneo", */collectionResourceRel = "person")
public interface PersonRepositoryNeo4j extends CrudRepository<PersonNeoVO, Long>, PagingAndSortingRepository<PersonNeoVO, Long> {
	
	List<PersonNeoVO> findByLastName(String lastName) throws Exception;
	
	List<PersonNeoVO> findByFirstName(String firstName) throws Exception;
	
}
