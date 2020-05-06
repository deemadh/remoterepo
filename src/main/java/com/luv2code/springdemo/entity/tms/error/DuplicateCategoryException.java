package com.luv2code.springdemo.entity.tms.error;

@SuppressWarnings("serial")
public class DuplicateCategoryException extends TMSExeption{

	public DuplicateCategoryException(String message) {
		super(message,"E002");
				
	}
	
}
