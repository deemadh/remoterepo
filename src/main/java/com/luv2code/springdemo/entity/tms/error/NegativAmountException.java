package com.luv2code.springdemo.entity.tms.error;

public class NegativAmountException extends TMSExeption {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public NegativAmountException (String message) {
		super(message, "E006");
		// TODO Auto-generated constructor stub
	}
}
