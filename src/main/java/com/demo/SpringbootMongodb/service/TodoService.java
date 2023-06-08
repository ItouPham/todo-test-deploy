package com.demo.SpringbootMongodb.service;

import java.util.List;

import javax.validation.ConstraintViolationException;

import com.demo.SpringbootMongodb.exception.TodoCollectionException;
import com.demo.SpringbootMongodb.model.Todo;

public interface TodoService {
	void createTodo(Todo todo) throws ConstraintViolationException, TodoCollectionException;

	List<Todo> getAllTodos();

	Todo getSingleTodo(String id) throws TodoCollectionException;

	void updateTodo(String id,Todo todo) throws TodoCollectionException;
	
	void deleteTodoById(String id) throws TodoCollectionException;
}
