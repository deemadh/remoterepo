package com.luv2code.springdemo.entity.tms.error;

public class MissingArgumentException extends TMSExeption {

	/**
	 * 
	 */
	private static final long serialVersionUID = 84;

	public MissingArgumentException(String message) {
		super(message, "E004");
		// TODO Auto-generated constructor stub
	}

}
