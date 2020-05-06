package com.luv2code.springdemo.entity.tms.error;

public class DuplicateCategoryException extends TMSExeption{

	public DuplicateCategoryException(String message) {
		super(message,"E002");
				
	}
	/*
	@Override
	public String toString() {
		// TODO Auto-generated method stub
		 System.out.println("This Ctegory is already found try again!!");
	}
	*/
}
