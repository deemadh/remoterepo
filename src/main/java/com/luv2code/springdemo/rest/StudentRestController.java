package com.luv2code.springdemo.rest;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.luv2code.springdemo.entity.Student;

@RestController
@RequestMapping("/api")
public class StudentRestController {
	private List<Student> theStudents;

	
	@PostConstruct
	public void loadData () {
		 theStudents = new ArrayList<>();
		 theStudents.add(new Student("Poornima", "Patel" ,1));
			theStudents.add(new Student("Mario", "Rossi",2));
			theStudents.add(new Student("Mary", "Smith",3));
	}
	// define endpoint for "/students" - return list of students
	@GetMapping("/students")
	public List<Student> getStudents() {
		
		return theStudents;
	}

	@GetMapping("/students/{id}")
	public Student getStudent(@PathVariable int id) {
		List<Student> theStudents = new ArrayList<>();

		theStudents.add(new Student("Poornima", "Patel", 1));
		theStudents.add(new Student("Mario", "Rossi", 2));
		theStudents.add(new Student("Mary", "Smith", 3));

		for (Student s : theStudents) {
			if (s.getId() == id)
				return s;
		}
		
		throw new StudentNotFoundException("Student id not found -"+ id);

	}
	
}
