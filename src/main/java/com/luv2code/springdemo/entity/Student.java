package com.luv2code.springdemo.entity;

public class Student {

	private String firstName;
	private String lastName;
	private int id ;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public Student() {
		
	}

	public Student(String firstName, String lastName , int id) {
		this.firstName = firstName;
		this.lastName = lastName;
		this.id = id ;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	

	
}
