package com.luv2code.springdemo.entity.tms.error;

@SuppressWarnings("serial")
public class InvalidDateException extends TMSExeption {

	public InvalidDateException(String message) {
		super(message, "E003");
		// TODO Auto-generated constructor stub
	}

}
