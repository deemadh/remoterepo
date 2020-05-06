package com.luv2code.springdemo.rest;

import org.springframework.http.MediaType;
import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.luv2code.springdemo.entity.*;

@CrossOrigin(origins = "*", allowedHeaders = "*")
@RestController
@RequestMapping(value = "/tms")
public class TMSRestController {
	@CrossOrigin
	@GetMapping("/transactions")
	public List<TransactionBase> getTransaction(TransactionFilters filters) throws Exception {
		TMSServiceDatabaseImpl tmsService;

		try {
			tmsService = new TMSServiceDatabaseImpl();
			if (filters != null)

				return tmsService.getTransatcions(filters);
			else
				return tmsService.getTransatcions(new TransactionFilters(null, null, null, null));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@CrossOrigin
	@GetMapping("/balance")
	public double getBalane(TransactionFilters filter) {
		TMSServiceDatabaseImpl tmsService;

		try {
			tmsService = new TMSServiceDatabaseImpl();
			if (filter != null)

				return tmsService.getBalance(filter);
			else
				return tmsService.getBalance(new TransactionFilters(null, null, null, null));
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return -1;
	}

	@CrossOrigin
	@GetMapping("/categories")
	public List<Category> getGategoreies(int type) {
		TMSServiceDatabaseImpl tmsService;

		try {
						
			tmsService = new TMSServiceDatabaseImpl();
			
				return tmsService.getCategories(type);
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@CrossOrigin
	@GetMapping("/category/{id}")
	public Category getGategory(@PathVariable final String id) {
		TMSServiceDatabaseImpl tmsService;

		try {
			tmsService = new TMSServiceDatabaseImpl();
			
				return tmsService.getCategory(Integer.parseInt(id));
			
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	@CrossOrigin
	@PostMapping("/category")

	public void addCategory(Category category) {
		TMSServiceDatabaseImpl tmsService;

		try {
			tmsService = new TMSServiceDatabaseImpl();
			tmsService.addCategory(category);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}

	@CrossOrigin
	@DeleteMapping("/category/{id}")
	public void deleteCategory(@PathVariable int id) {
		TMSServiceDatabaseImpl tmsService;
		try {
			tmsService = new TMSServiceDatabaseImpl();
			tmsService.removeCategory(id);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@PutMapping("/category/{id}")
	public void updateCategory(@PathVariable String id, Category category) {
		TMSServiceDatabaseImpl tmsService;
		try {
			tmsService = new TMSServiceDatabaseImpl();
			category.setId(Integer.parseInt(id));
			tmsService.updateCategory(category);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}
	}

	@CrossOrigin
	@PostMapping("/expense")

	public void addExpense(Expense expense) {
		TMSServiceDatabaseImpl tmsService;
      
		try {
			tmsService = new TMSServiceDatabaseImpl();
			tmsService.addExpense(expense);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}

	@CrossOrigin
	@PostMapping("/income")

	public void addIncome(Income income) {
		TMSServiceDatabaseImpl tmsService;
		
		try {
			    
			tmsService = new TMSServiceDatabaseImpl();
			tmsService.addIncome(income);
		} catch (ClassNotFoundException | SQLException | IOException e) {
			e.printStackTrace();
		}

	}

}
