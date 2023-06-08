package com.demo.SpringbootMongodb.model;

import java.util.Date;

import javax.validation.constraints.NotNull;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Document(collection = "todos")
public class Todo {
	@Id
	private String id;
	
	@NotNull(message = "todo cannot be null")
	private String todo;
	
	@NotNull(message = "description cannot be null")
	private String description;
	
	@NotNull(message = "completed cannot be null")
	private Boolean completed;
	
	private Date createAt;
	
	private Date updateAt;
}
