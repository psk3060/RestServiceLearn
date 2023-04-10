package com.example.restservice.service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restservice.repository.PersonRepositoryNeo4j;
import com.example.restservice.service.model.PersonNeoVO;

@Service
public class PersonNeoServiceImpl implements PersonNeoService {
	
	@Autowired
	private PersonRepositoryNeo4j personRepository;
	
	@Override
	public long countAll() throws Exception {
		return personRepository.count();
	}
	
	@Override
	public void deleteAll() throws Exception {
		personRepository.deleteAll();
		
	}
	
	@Override
	public void deleteByKey(Long id) throws Exception {
		personRepository.deleteById(id);
	}
	
	@Override
	public PersonNeoVO insert(PersonNeoVO vo) throws Exception {
		return personRepository.save(vo);
	}
	
	@Override
	public PersonNeoVO update(PersonNeoVO vo) throws Exception {
		return personRepository.save(vo);
	}
	
	@Override
	public List<PersonNeoVO> selectListByFirstName(String firstName) throws Exception {
		return StreamSupport
				  .stream(personRepository.findByFirstName(firstName).spliterator(), false)
				  .collect(Collectors.toList());
	}
	
	@Override
	public List<PersonNeoVO> selectListByLastName(String lastName) throws Exception {
		return StreamSupport
				  .stream(personRepository.findByLastName(lastName).spliterator(), false)
				  .collect(Collectors.toList());
	}
	
	@Override
	public List<PersonNeoVO> selectListAll() throws Exception {
		
		return StreamSupport
				  .stream(personRepository.findAll().spliterator(), false)
				  .collect(Collectors.toList());
		
	}
	
	@Override
	public PersonNeoVO select(Long id) throws Exception {
		return personRepository.findById(id).get();
	}
	
}
