package com.luv2code.springdemo.entity;
public class Factory {
	public static TransactionBase createTransaction(String transactionType, TransactionArgument arguments) {
		switch (transactionType) {
		case "Income":
			return new Income(arguments.getId(), arguments.getType(), arguments.getAmount(), arguments.getCategorykey(), arguments.getComment(),
					arguments.getDate());
		case "Expense":
			return new Expense(arguments.getId(), arguments.getType(), arguments.getAmount(), arguments.getCategorykey(), arguments.getComment(),
					arguments.getDate(), arguments.getPymentMethod());
				default:
			return null;
		}
	}
}