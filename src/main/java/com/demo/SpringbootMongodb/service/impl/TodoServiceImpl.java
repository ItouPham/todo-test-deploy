package com.demo.SpringbootMongodb.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.demo.SpringbootMongodb.exception.TodoCollectionException;
import com.demo.SpringbootMongodb.model.Todo;
import com.demo.SpringbootMongodb.repository.TodoRepository;
import com.demo.SpringbootMongodb.service.TodoService;

@Service
public class TodoServiceImpl implements TodoService {

	@Autowired
	private TodoRepository todoRepository;

	@Override
	public void createTodo(Todo todo) throws ConstraintViolationException, TodoCollectionException {
		Optional<Todo> todoOptional = todoRepository.findByTodo(todo.getTodo());
		if (todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
		} else {
			todo.setCreateAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todo);
		}
	}

	@Override
	public List<Todo> getAllTodos() {
		List<Todo> todos = todoRepository.findAll();
		if (todos.size() > 0) {
			return todos;
		} else {
			return new ArrayList<Todo>();
		}
	}

	@Override
	public Todo getSingleTodo(String id) throws TodoCollectionException {
		Optional<Todo> todoOptional = todoRepository.findById(id);
		if (!todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {
			return todoOptional.get();
		}
	}

	@Override
	public void updateTodo(String id,Todo todo) throws TodoCollectionException {
		Optional<Todo> todoWithId = todoRepository.findById(id);
		Optional<Todo> todoWithSameName = todoRepository.findByTodo(todo.getTodo());
		if(todoWithId.isPresent()) {
			if(todoWithSameName.isPresent() && !todoWithSameName.get().getId().equals(id)) {
				throw new TodoCollectionException(TodoCollectionException.TodoAlreadyExists());
			}
			Todo todoToUpdate = todoWithId.get();
			todoToUpdate.setTodo(todo.getTodo());
			todoToUpdate.setDescription(todo.getDescription());
			todoToUpdate.setCompleted(todo.getCompleted());
			todoToUpdate.setUpdateAt(new Date(System.currentTimeMillis()));
			todoRepository.save(todoToUpdate);
		}else {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		}
	}

	@Override
	public void deleteTodoById(String id) throws TodoCollectionException {
		Optional<Todo> todoOptional = todoRepository.findById(id);
		if(!todoOptional.isPresent()) {
			throw new TodoCollectionException(TodoCollectionException.NotFoundException(id));
		} else {
			todoRepository.deleteById(id);
		}
	}

}
