package com.websystique.hibernate.dao;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


import org.hibernate.Criteria;
import org.hibernate.Filter;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.criterion.Restrictions;

import com.websystique.hibernate.HibernateUtil;
import com.websystique.hibernate.model.Company;
import com.websystique.hibernate.model.Employee;

public class EmpDao {

	HibernateUtil hibernetUtil = new HibernateUtil();
	public List<Employee> getAllEmpDetails()
	{
		Session session = hibernetUtil.getSession();
		Filter enableFilter = session.enableFilter("ef");
		enableFilter.setParameter("salary", new Integer(5000));
		Employee emp1 = (Employee) session.get(Employee.class, 1);
		/*List<Employee> list = session.createQuery(
				"FROM Employee").list();*/
		Criteria cr = session.createCriteria(Employee.class);
		cr.add(Restrictions.eq("cmpName", "google"));
		List<Employee> list = cr.list();
		
		System.out.println("emp list fetched");
		//session.flush();
		/*Employee emp1 = (Employee) session.get(Employee.class, 3);
		System.out.println("emp1 is fetched."+emp1);
		emp1.setSalary(888);
		emp1.setEmployeeName("test2");*/
		Query query = session.createQuery("update Employee set salary = 5502 where cmpName = 'Google' and employeeName = 'emp1_3'");
		query.executeUpdate();
		System.out.println("hql update is fired.");
		for(Employee emp : list)
		{
			if(emp.getEmployeeName().equalsIgnoreCase("emp1_3"))
			{
				emp.setEmployeeName("temp");
			}
		}
	
		//session.save(emp1);
		session.flush();
		hibernetUtil.commit(session);
		list.forEach(emp -> System.out.println(emp));
		
		return list;
		
	}
	
	public Employee getEmployee()
	{
		Session session = hibernetUtil.getSession();
		//Employee emp = (Employee) session.get(Employee.class, 3);
		Company cmp = (Company) session.load(Company.class, 1);
		System.out.println(cmp);
		Employee emp = (Employee) session.load(Employee.class, 2);
		System.out.println("emp1 fetched."+emp);
		System.out.println(emp.getCompnayToEmpMap());
		//emp.getCompanyId();
		
		hibernetUtil.commit(session);
		return emp;
	}
	
	public void saveEmp()
	{
		Session session = hibernetUtil.getSession();
		//Company c = (Company) session.get(Company.class, 4);
		Employee e = (Employee) session.load(Employee.class, 1);
		
		
		Employee ep1 = new Employee();
		ep1.setEmployeeName("tempemp4_4");
		ep1.setSalary(500000);
		ep1.setId(15);
		ep1.setCompanyId(4);
		//ep1.setCompnayToEmpMap(c);
		//c.setEmployeeList(Arrays.asList(ep1));
		session.save(ep1);
		System.out.println("emp saved.");
		System.out.println("id : "+ep1.getId());
		session.getTransaction().commit();
		System.out.println("trasection commited");
//		int a = 5/0;
	
		session.beginTransaction();
		//session = hibernetUtil.getSession();
		Employee e2 = (Employee) session.load(Employee.class, ep1.getId());
		System.out.println("emp gets.");
		hibernetUtil.commit(session);
		//int j = 10/0;
		
		
	}
	
	public void updateEmp() 
	{
		Session session = hibernetUtil.getSession();
		/*Employee emp = (Employee) session.get(Employee.class, 5);
		System.out.println("emp fetched.");
		Company cmp = emp.getCompnayToEmpMap();
		System.out.println("cmp fetched");
		cmp.setCity("tempct2");
		emp.setEmployeeName("tempemp2");
		session.saveOrUpdate(emp);
		System.out.println("emp saved.");*/
		/*Query query = session.createQuery("update Employee set salary=9999 where id = 5");
		query.executeUpdate();
		System.out.println("update is executed.");*/
		Employee emp1 = (Employee) session.get(Employee.class, 2);
		System.out.println("emp1 is fetched."+emp1);
		emp1.setCmpName("test");
		hibernetUtil.commit(session);
	}
	
	public void deleteEmp()
	{
		Session session = hibernetUtil.getSession();
		Employee emp = (Employee) session.get(Employee.class, 3);
		System.out.println("emp1 fetched.");
		Employee emp2 = (Employee) session.get(Employee.class, 4);
		System.out.println("emp2 fetched.");
		//emp2.setSalary(8);
		//emp2.setSalary(55);
		//session.save(emp2);
		System.out.println("emp2 is saved");
	
		session.delete(emp);
		System.out.println("deleted.");
		/*session.saveOrUpdate(emp2);
		System.out.println("Saved.");*/
		
		hibernetUtil.commit(session);
	}
}
