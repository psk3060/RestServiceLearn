package com.example.restservice.service;

import java.util.List;

import com.example.restservice.service.model.PersonNeoVO;

public interface PersonNeoService {
	
	PersonNeoVO insert(PersonNeoVO vo) throws Exception;
	
	PersonNeoVO update(PersonNeoVO vo) throws Exception;
	
	List<PersonNeoVO> selectListAll() throws Exception;
	
	long countAll() throws Exception;
	
	void deleteAll() throws Exception;
	
	void deleteByKey(Long id) throws Exception;
	
	List<PersonNeoVO> selectListByLastName(String lastName) throws Exception;
	
	List<PersonNeoVO> selectListByFirstName(String firstName) throws Exception;
	
	PersonNeoVO select(Long id) throws Exception;
	
}
