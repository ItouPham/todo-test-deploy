package com.demo.SpringbootMongodb.repository;

import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.stereotype.Repository;

import com.demo.SpringbootMongodb.model.Todo;

@Repository
public interface TodoRepository extends MongoRepository<Todo, String>{

	@Query("{'todo': ?0}")
	Optional<Todo> findByTodo(String todo);
}
