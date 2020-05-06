package com.luv2code.springdemo.entity;
import java.time.LocalDate;
// datatype of category is class<Category> not integer
//constructor receive category as class<Category> 
//edit get and set methods for category
public class TransactionArgument {
	private Integer id;
	private Integer type;
	private Double amount;
	private Category category;
	private Integer categorykey;
	private String comment;
	private LocalDate date;
	private Integer pymentMethod;
	
	
	TransactionArgument(Integer id, Integer type, Double amount,Integer category, String comment, LocalDate date,
			Integer pymentMethod, Integer monthFrequent) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.categorykey=category;
		this.comment = comment;
		this.date = date;
		this.pymentMethod = pymentMethod;
		
	}
	
	TransactionArgument(Integer id, Integer type, Double amount, Category category, String comment, LocalDate date,
			Integer pymentMethod) {
		super();
		this.id = id;
		this.type = type;
		this.amount = amount;
		this.category = category;
		this.comment = comment;
		this.date = date;
		this.pymentMethod = pymentMethod;
	
	}
	
	public TransactionArgument() {
		// TODO Auto-generated constructor stub
		this.id = null;
		this.type = null;
		this.amount = null;
		this.setCategorykey(null);
		this.comment = null;
		this.date = null;
		this.pymentMethod = null;
	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public double getAmount() {
		return amount;
	}
	public void setAmount(double amount) {
		this.amount = amount;
	}
	public Category getCategory() {
		return category;
	}
	public void setCategory(Category category) {
		this.category = category;
	}
	public String getComment() {
		return comment;
	}
	public void setComment(String comment) {
		this.comment = comment;
	}
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getPymentMethod() {
		return pymentMethod;
	}
	public void setPymentMethod(int pymentMethod) {
		this.pymentMethod = pymentMethod;
	}
	
	
	public Integer getCategorykey() {
		return categorykey;
	}
	public void setCategorykey(Integer categorykey) {
		this.categorykey = categorykey;
	}


}
