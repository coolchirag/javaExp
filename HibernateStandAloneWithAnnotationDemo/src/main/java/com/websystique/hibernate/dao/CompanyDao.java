package com.websystique.hibernate.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.hibernate.Session;

import com.websystique.hibernate.HibernateUtil;
import com.websystique.hibernate.model.COmpanyDtoTemp;
import com.websystique.hibernate.model.Company;
import com.websystique.hibernate.model.Employee;

public class CompanyDao {
	HibernateUtil hibernetUtil = new HibernateUtil();
	public void updateCompanyEmployee()
	{
		Session session = hibernetUtil.getSession();
		Object obj =  session.load(Company.class, 4);
		Company company = (Company) obj; 
		System.out.println("company detail is fecthed"+company);
		COmpanyDtoTemp temp = new COmpanyDtoTemp();
		temp.setId(company.getId());
		temp.setCompanyName(company.getCompanyName());
		temp.setCity(company.getCity());
		//company.getEmployeeList().size();
		//hibernetUtil.commit(session);
		//session.clear();
		//System.out.println("session cleared.");
		//System.out.println(company);
		/*Employee emp1 = new Employee();
		emp1.setEmployeeName("test5_3");
		emp1.setSalary(7000);
		emp1.setCompnayToEmpMap(company);*/
		company = new Company();
		company.setId(temp.getId());
		company.setCompanyName(temp.getCompanyName());
		company.setCity(temp.getCity());
		
		//company.setCompanyName("test");
		//company = (Company) session.load(Company.class, 5);
		/*List<Employee> emplist = company.getEmployeeList();
		if(emplist != null)
		{
			emplist.clear();
			
		}
		else {
			emplist = new ArrayList<>();
			company.setEmployeeList(emplist);
		}
		
		emplist.add(emp1);*/
		/*List<Employee> empList = company.getEmployeeList();
		empList.remove(0);
		empList.add(emp1);*/
		//List<Employee> empNewList = new ArrayList<>(empList);
		//empNewList.addAll(empList);
		//empNewList.remove(0);
		//empNewList.add(emp1);
		//company.setEmployeeList(Arrays.asList(emp1));
		//company.setEmployeeList(empNewList);
		//session = hibernetUtil.getSession();
		//session.merge(company);
		//session.saveOrUpdate(company);
		session.saveOrUpdate(company);
		System.out.println("company saved");
		hibernetUtil.commit(session);
	}
	
	public void insertComapny()
	{
		Session session = hibernetUtil.getSession();
		Company cmp = new Company();
		//cmp.setCompanyName("cmp5");
		//cmp.setCity("c5");
		Employee emp1 = new Employee();
		emp1.setEmployeeName("test5_1");
		emp1.setSalary(7000);
		//emp1.setCompnayToEmpMap(cmp);
		//cmp.setEmployeeList(Arrays.asList(emp1));
		session.saveOrUpdate(cmp);
		System.out.println("company saved");
		hibernetUtil.commit(session);
	}

}
