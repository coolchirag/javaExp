package com.chirag.hibernate.entitymanager.examples.service;

import java.util.ArrayList;

import javax.persistence.EntityManager;

import com.chirag.hibernate.entitymanager.examples.HibernateUtility;
import com.chirag.hibernate.entitymanager.examples.model.Company;
import com.chirag.hibernate.entitymanager.examples.model.Employee;

public class EmployeeService {

	public void insertEmployee() {
		EntityManager em = HibernateUtility.getEntityManager();
		Company c = new Company();
		c.setCity("c5");
		c.setCompanyName("c5");
		//c.setEmployeeList(new ArrayList<Employee>());
		System.out.println("Compnay fetched");
		Employee emp = new Employee();
		emp.setEmployeeName("emp2_3");
		emp.setCmpName("IBM");
		emp.setSalary(6000);
		emp.setCompnayToEmpMap(c);
		
		em.persist(emp);
		System.out.println("Saved");
		HibernateUtility.commit(em);
		System.out.println("Done");
	}
}
