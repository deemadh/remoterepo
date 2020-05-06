package com.luv2code.springdemo.entity.tms.error;

@SuppressWarnings("serial")
public class InvalidCategoryException extends TMSExeption {
	public InvalidCategoryException(String message) {
		super(message,"E007");
				
	}

}
