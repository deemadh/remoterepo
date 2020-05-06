package com.luv2code.springdemo.entity;

import java.time.LocalDate;



public class Income extends TransactionBase {

	
	public Income() {
		super();
	}
	
	
	public Income(int id, int type, double amount, Category category, String comment, LocalDate date) {
		super(id, type, amount, category, comment, date);
	}
	
	public Income(int id, int type, double amount, Integer category, String comment, LocalDate date) {
		super(id, type, amount, category, comment, date);
	}

	@Override
	public String toString() {
		return "Income\t\tid:"+this.getId()+"\ttype:"+this.getType()+"\tamount:"+this.getAmount()+"\tdate:"+this.getDate()+"\tcategory:"+this.getCategory().getId()+"\tcomment:"+this.getComment();

	}
}