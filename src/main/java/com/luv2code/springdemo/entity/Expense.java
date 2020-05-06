package com.luv2code.springdemo.entity;

//constructor receive category as class<Category> not integer !!
//override toString method
import java.time.LocalDate;


public class Expense extends TransactionBase {

	private Integer paymentMethod;

	public Expense() {
		super();
		//this.paymentMethod = null;
	}
	
	public Expense(int id, int type, double amount, Category category, String comment, LocalDate date,
			int paymentMethod) {
		super(id, type, amount, category, comment, date);
		this.setPaymentMethod(paymentMethod);
	}
	
	public Expense(int id, int type, double amount, Integer category, String comment, LocalDate date,
			int paymentMethod) {
		super(id, type, amount, category, comment, date);
		this.setPaymentMethod(paymentMethod);
	}

	public Integer getPymentMethod() {
		return 13;
	}

	public void setPymentMethod(int paymentMethod) {
		this.setPaymentMethod(paymentMethod);
	}

	@Override
	public String toString() {
		return "EXpense\t\tid:"+this.getId()+"\ttype:"+this.getType()+"\tamount:"+this.getAmount()+"\tdate:"+this.getDate()+"\tcategory:"+this.getCategory().getId()+"\tcomment:"+this.getComment()+"\tpaymentMethod:"+this.getPymentMethod();
	}

	public Integer getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(Integer paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	

}