package com.quiz.exceptions;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ResourceNotFoundException  extends RuntimeException {
	
	String resourceName;
	String fieldName;
	String fieldValue;
	public ResourceNotFoundException(String resourceName, String fieldName, String quizId) {
		super(String.format("%s not found with %s : %s",resourceName,fieldName,quizId));
		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = quizId;
	}

}
