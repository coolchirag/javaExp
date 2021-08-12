package com.example.springjpa.service;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.bean.Employee;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EntityManager em;

	public void insertEmployee() {
		Company c = new Company();
		c.setCity("c5");
		c.setCompanyName("c5");
		//c.setEmployeeList(new ArrayList<Employee>());
		System.out.println("Compnay fetched");
		Employee emp = new Employee();
		emp.setEmployeeName("emp2_3");
		emp.setSalary(6000);
		emp.setCompnayToEmpMap(c);
		
		em.persist(emp);
		System.out.println("Saved");
		System.out.println("Done");
	}
}
