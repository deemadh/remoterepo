package com.luv2code.springdemo.entity;

//override toString method 
public class Category {

	private Integer id;
	private Integer dkey;
	private String value;
	private String iconPath;
	
	
	public Category(Integer id, int dkey, String value, String iconPath) {
		super();
		this.id = id;
		this.dkey = dkey;
		this.value = value;
		this.iconPath = iconPath;
		
	}


	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getDkey() {
		return dkey;
	}

	public void setDkey(Integer dkey) {
		this.dkey = dkey;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getIconPath() {
		return iconPath;
	}

	public void setIconPath(String iconPath) {
		this.iconPath = iconPath;
	}
	

	@Override
	public String toString() {
		return "Category id:" + id + "\tdkey:" + dkey +  "\tvalue:" + value + "\ticonPath:" + iconPath+ "\tenable";
	}
}