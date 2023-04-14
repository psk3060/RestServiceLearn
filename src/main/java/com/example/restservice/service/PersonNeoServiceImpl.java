package com.example.restservice.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservice.repository.PersonNeo4jRepository;
import com.example.restservice.service.model.PersonNeoVO;

@Service("personNeoService")
public class PersonNeoServiceImpl implements PersonNeoService {
	
	@Autowired
	private PersonNeo4jRepository personNeo4jRepository;
	
	@Override
	public long countAll() throws Exception {
		return personNeo4jRepository.count();
	}
	
	@Override
	public void deleteAll() throws Exception {
		personNeo4jRepository.deleteAll();
		
	}
	
	@Override
	public void deleteByKey(Long id) throws Exception {
		personNeo4jRepository.deleteById(id);
	}
	
	@Override
	public PersonNeoVO insert(PersonNeoVO vo) throws Exception {
		return personNeo4jRepository.save(vo);
	}
	
	@Override
	public PersonNeoVO update(PersonNeoVO vo) throws Exception {
		return personNeo4jRepository.save(vo);
	}
	
	@Override
	public List<PersonNeoVO> selectListByFirstName(String firstName) throws Exception {
		return StreamSupport
				  .stream(personNeo4jRepository.findByFirstName(firstName).spliterator(), false)
				  .collect(Collectors.toList());
	}
	
	@Override
	public List<PersonNeoVO> selectListByLastName(String lastName) throws Exception {
		return StreamSupport
				  .stream(personNeo4jRepository.findByLastName(lastName).spliterator(), false)
				  .collect(Collectors.toList());
	}
	
	@Override
	public List<PersonNeoVO> selectListAll() throws Exception {
		
		return StreamSupport
				  .stream(personNeo4jRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		
	}
	
	@Override
	public PersonNeoVO select(Long id) throws Exception {
		return personNeo4jRepository.findById(id).get();
	}
	
}
