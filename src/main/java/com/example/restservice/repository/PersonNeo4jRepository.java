package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.restservice.service.model.PersonNeoVO;

@RepositoryRestResource(/*path = "personneo", */collectionResourceRel = "person")
public interface PersonNeo4jRepository extends Neo4jRepository<PersonNeoVO, Long>, PagingAndSortingRepository<PersonNeoVO, Long> {
	
	List<PersonNeoVO> findByLastName(String lastName) throws Exception;
	
	List<PersonNeoVO> findByFirstName(String firstName) throws Exception;
	
}
