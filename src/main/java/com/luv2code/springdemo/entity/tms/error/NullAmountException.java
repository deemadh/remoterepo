package com.luv2code.springdemo.entity.tms.error;

@SuppressWarnings("serial")
public class NullAmountException extends TMSExeption {

	public NullAmountException(String message) {
		super(message, "E006");
		// TODO Auto-generated constructor stub
	}

}
