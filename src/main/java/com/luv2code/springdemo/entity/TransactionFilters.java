package com.luv2code.springdemo.entity;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonProperty;

public class TransactionFilters {
	private Integer type = null;
	private Integer categoryId = null;
	private LocalDate from = null;
	private LocalDate to = null;
	public TransactionFilters(@JsonProperty Integer type, @JsonProperty Integer categoryId,
			@JsonProperty LocalDate from, @JsonProperty LocalDate to) {
		super();
		this.type = type;
		this.categoryId = categoryId;
		this.from = from;
		this.to = to;

	}

	public TransactionFilters() {
		// TODO Auto-generated constructor stub
	}

	

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public Integer getcategoryId() {
		return categoryId;
	}

	public void setcategoryId(Integer categoryId) {
		this.categoryId = categoryId;
	}

	public LocalDate getFrom() {
		return from;
	}

	public void setFrom(LocalDate from) {
		this.from = from;
	}

	public LocalDate getTo() {
		return to;
	}

	public void setTo(LocalDate to) {
		this.to = to;
	}

}
