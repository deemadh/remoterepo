package com.luv2code.springdemo.entity;
import java.sql.SQLException;
import java.util.List;

public interface TMSService {

	//public List<TransactionBase> getTransatcions(TransactionFilters filters) throws SQLException, Exception;
	
	public List<Category> getCategories(Integer type)throws SQLException;
	
	public void addIncome(Income income) throws SQLException;
	
	public void addExpense(Expense expense)throws SQLException;
	
	public void addCategory(Category category) throws SQLException;
	
	public void removeCategory(Integer id) throws SQLException;
	
	public void updateCategory(Category category) throws SQLException;
	
	public double getBalance(TransactionFilters filters) throws SQLException;

	public List<TransactionBase> getTransatcions(TransactionFilters filters) throws SQLException, Exception;
	public Category getCategory(int id)throws SQLException;
}
