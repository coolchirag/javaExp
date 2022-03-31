package com.example.springjpa.service;

import java.util.List;
import java.util.Set;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.bean.Employee;
import com.example.springjpa.repository.CompanyRepository;
import com.example.springjpa.repository.EmployeeRepository;

@Service
@Transactional
public class EmployeeService {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private CompanyRepository cmpRepo;

	public void insertEmployeeWithCompany() {
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
	
	public void insertEmployeINExistingCompany() {
		Company cmp1 = cmpRepo.findOne(1);
		Employee emp = new Employee();
		emp.setEmployeeName("emp1_1");
		emp.setSalary(6000);
		emp.setCompnayToEmpMap(cmp1);
		
		empRepo.save(emp);
		System.out.println("Emp saved");
	}
	
	public void findEmp() {
		try {
			Thread.sleep(20000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Set<Integer> cmpIds = empRepo.findCompanyIdByEmployeeName("test");
		System.out.println(cmpIds);
		List<Employee> emps = empRepo.getEmployeesByCompanyName();
		System.out.println(emps);
		
		List<Employee> emps2 = empRepo.findByCompnayToEmpMapCompanyName("test");
		System.out.println(emps2);
	}
}
