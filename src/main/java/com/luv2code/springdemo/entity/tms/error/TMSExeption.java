package com.luv2code.springdemo.entity.tms.error;

public class TMSExeption extends RuntimeException {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String errorCode;

	public TMSExeption(String message, String errorCode) {
		super(message);
		this.errorCode = errorCode;

	}

	public String getErrorCode() {
		return errorCode;
	}

}
