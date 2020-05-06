package com.luv2code.springdemo.entity.tms.error;

public class InvalidDataException extends TMSExeption {

	public InvalidDataException(String message) {
		super(message,"E001");
				
	}

}
