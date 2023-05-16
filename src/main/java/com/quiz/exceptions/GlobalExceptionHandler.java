package com.quiz.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.quiz.messages.Messages;


@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
	public ResponseEntity<Messages> resourceNotFondException(ResourceNotFoundException rnf){
		String message = rnf.getMessage();
		Messages msg = new Messages(message, false);
		
		return new ResponseEntity<Messages>(msg,HttpStatus.NOT_FOUND);
		
	}

}
