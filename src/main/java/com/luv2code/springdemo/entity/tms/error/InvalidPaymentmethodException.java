package com.luv2code.springdemo.entity.tms.error;

@SuppressWarnings("serial")
public class InvalidPaymentmethodException extends TMSExeption {

	public InvalidPaymentmethodException(String message) {
		super(message, "E007");
		// TODO Auto-generated constructor stub
	}

}
