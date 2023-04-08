package com.example.restservice.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import com.example.restservice.service.model.Person;

/**
 * 단순 CRUD 기능일 제공하는 CrudRepository 상속
 * - 런타임 시 @RepositoryRestResource.path에 작성된 /people에 RESTful한 엔드포인트 생성(컨트롤러 불필요)
 * - 접근은 collectionResourceRel를 통해 접근(_embedded.people[idx].id와 같이 접근 가능)
 * - 해당 인터페이스 안에 명시적으로 작성을 해야 /people/searcch/* 로부터 조회 가능
 */
@RepositoryRestResource(collectionResourceRel = "people", path="people")
public interface PersonRepository extends CrudRepository<Person, Long>{
	
	List<Person> findByFirstName(@Param("name") String name) throws Exception;
	
	List<Person> findByLastName(@Param("name") String name) throws Exception;
	
}
