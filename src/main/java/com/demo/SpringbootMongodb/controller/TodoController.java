package com.demo.SpringbootMongodb.controller;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.validation.ConstraintViolationException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.SpringbootMongodb.exception.TodoCollectionException;
import com.demo.SpringbootMongodb.model.Todo;
import com.demo.SpringbootMongodb.repository.TodoRepository;
import com.demo.SpringbootMongodb.service.TodoService;

@RestController
@RequestMapping("todos")
public class TodoController {

	@Autowired
	private TodoRepository todoRepository;

	@Autowired
	private TodoService todoService;

	@GetMapping
	public ResponseEntity<?> getAllTodos() {
		List<Todo> todos = todoService.getAllTodos();
		return new ResponseEntity<>(todos, todos.size() > 0 ? HttpStatus.OK : HttpStatus.NOT_FOUND);
	}

	@PostMapping
	public ResponseEntity<?> createTodo(@RequestBody Todo todo) {
		try {
			todoService.createTodo(todo);
			todoRepository.save(todo);
			return new ResponseEntity<Todo>(todo, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@GetMapping("{id}")
	public ResponseEntity<?> getSingleTodo(@PathVariable("id") String id) {
		try {
			return new ResponseEntity<>(todoService.getSingleTodo(id), HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}

	@PutMapping("{id}")
	public ResponseEntity<?> updateById(@PathVariable("id") String id, @RequestBody Todo todo) {
		try {
			todoService.updateTodo(id, todo);
			return new ResponseEntity<>("Update Todo with id " + id, HttpStatus.OK);
		} catch (ConstraintViolationException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.UNPROCESSABLE_ENTITY);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.CONFLICT);
		}
	}

	@DeleteMapping("{id}")
	public ResponseEntity<?> deleteById(@PathVariable("id") String id) {
		try {
			todoService.deleteTodoById(id);
			return new ResponseEntity<>("Successfully deleted with id " + id, HttpStatus.OK);
		} catch (TodoCollectionException e) {
			return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
		}
	}
}
