package com.luv2code.springdemo.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class TMSRestExceptionHandler {

	
	//add an exception handler to catch any exception (call catch)
	@ExceptionHandler
	public ResponseEntity<TMSErrorResponse> handlerException(Exception exc){
		TMSErrorResponse error = new TMSErrorResponse();
		error.setStatus(HttpStatus.BAD_REQUEST.value());
		error.setMessage(exc.getMessage());
		error.setTimeStamp(System.currentTimeMillis());
		//return responseEntity
		return new ResponseEntity<>(error,HttpStatus.BAD_REQUEST);

	}

}
