package com.example.springjpa.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.bean.Employee;
import com.example.springjpa.bean.ProjectEmpMap;
import com.example.springjpa.dto.CustomCmpDto;
import com.example.springjpa.repository.CompanyRepository;
import com.example.springjpa.repository.EmployeeRepository;

@Service
@Transactional//(propagation = Propagation.NESTED)
public class CompanyService {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private CompanyRepository cmpRepo;
	
	@Autowired
	private EmployeeRepository empRepo;
	
	@Autowired
	private EmployeeService empService;
	
	public void getCmpByCityCount() {
		List<Object> cmps = cmpRepo.findByCityCount();
		System.out.println(cmps);
	}
	
	public void getCompanyFullDetails() {
		/*
		 * Optional<Company> cmpOption = cmpRepo.findOne((root, query, criteriaBuilder)
		 * -> { Fetch<Object, Object> employee = root.fetch("emp", JoinType.LEFT);
		 * return criteriaBuilder.equal(root.get("id"), 1); });
		 * if(cmpOption.isPresent()) { Company company = cmpOption.get();
		 * System.out.println(company); System.out.println(company.getEmp()); }
		 */
		
		List<Company> allCMps = cmpRepo.findAll((root, query, criteriaBuilder) -> {
			Fetch<Object, Object> employee = root.fetch("emp", JoinType.LEFT);
			//employee.fetch("empProject");
			Fetch<Object, Object> empProjectMap = employee.fetch("empProjectEmp", JoinType.LEFT);
			Fetch<Object, Object> project = empProjectMap.fetch("projectEmpMapProject", JoinType.LEFT);
			query.distinct(true);
			return criteriaBuilder.equal(root.get("isActive"), true);
		});
		for(Company cmp : allCMps) {
			System.out.println(cmp);
			System.out.println(cmp.getEmp());
			System.out.println(cmp.getEmp().get(0).getEmpProjectEmp());
			for(ProjectEmpMap peMap : cmp.getEmp().get(0).getEmpProjectEmp()) {
				System.out.println(peMap.getProjectEmpMapProject());
			}
			//System.out.println(cmp.getEmp().get(0).getEmpProject());
		}
	}
	
	public void getCompanysByCity() {
		/*
		 * Map<Integer, String> map = new HashMap(); map.put(7, "7"); map.put(8, "8");
		 * List<Company> companies = cmpRepo.findByIdsWithEmployee(map.keySet());
		 * System.out.println(companies.get(0).getEmp()); List<Company> list2 =
		 * cmpRepo.findByCity("test"); System.out.println(list2);
		 */
		List<Company> list3 = cmpRepo.findByCityWithEmployee("test");
		System.out.println(list3);
	}
	
	public void getCompany() {
		//Company nullName = cmpRepo.findByCompanyName(null);
		//System.out.println(nullName);
		Integer id = 7;
		//Company nullId = cmpRepo.findOne(id);
		Company nullId = cmpRepo.findByCompanyName("test");
		System.out.println(nullId);
		nullId.getEmployeeList();
		List<Employee> empList = nullId.getEmp();
		System.out.println(empList.size());
		Company cmp = cmpRepo.findCMTest("cmp1");
		//insertCompanyWithEmp();
		try {
		empService.insertCompany(cmp);
		} catch (Exception e) {
			System.out.println("Error occured in cmp Service : "+e.getMessage());
		}
		/*
		 * if(cmp!=null) { throw new RuntimeException("Custom exception"); }
		 */
				//List<Company> cmps = findByNameWithEmployee("test");
		try {
			Thread.sleep(10000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println(cmp);
		//Company c = em.find(Company.class, 2);//em.find(Company.class, 2);
		/*
		 * c.setCity("c5"); c.setCompanyName("c5"); c.setEmployeeList(new
		 * ArrayList<Employee>());
		 */
		System.out.println("Compnay fetched");
		//Company c2 = cmpRepo.findCMTest("test");
		//System.out.println("Compnay fetched c2");
		/*
		 * Employee emp = new Employee(); emp.setEmployeeName("emp2_3");
		 * emp.setCmpName("IBM"); emp.setSalary(6000); emp.setCompnayToEmpMap(c);
		 * c.getEmployeeList().add(emp);
		 */
		//c.getEmployeeList().remove(0);
		//c.setCity(c.getCity()+"test");
		//c.setCompanyName("test");
		
		//System.out.println("City : "+c.getCity()+" : "+c2.getCity());
		/*
		 * try { Thread.sleep(100*1000); } catch (InterruptedException e) { // TODO
		 * Auto-generated catch block e.printStackTrace(); }
		 */
		//cmpRepo.save(c);
		/*
		 * em.flush(); em.persist(c); System.out.println("Saved1");
		 * 
		 * System.out.println("Done1");
		 */
	}
	
	@Transactional
	public void compareCmpBean() {
		Company cmp =  new Company();
		cmp.setCompanyName("cmp5");
		cmp.setCity("city5");
		cmp.setIsActive(true);
		cmpRepo.save(cmp);
		
		Company cmp1 = null;//cmpRepo.findById(cmp.getId());
		Company cmp2 = cmpRepo.findByCompanyName("cmp5");
		cmp2.setCity("temp");
		//cmpRepo.saveAndFlush(cmp2);
		Company cmp3 = null;//cmpRepo.findById(cmp.getId());
		System.out.println(cmp.hashCode()+ " : "+ cmp1.hashCode()+" : "+cmp2.hashCode()+" : "+cmp3.hashCode());
		
	}
	
	@Transactional(propagation = Propagation.REQUIRES_NEW)
	public void insertCompanyWithEmp() {
		Company cmp = new Company();
		cmp.setCompanyName("test25");
		String str1 = "Status code 404, \"﻿<?xml";
		cmp.setCity(str1);
		cmpRepo.save(cmp);
		
		/*
		 * Employee emp = new Employee(); emp.setEmployeeName("test2");
		 * emp.setSalary(100000); emp.setCompanyId(8); empRepo.save(emp);
		 * 
		 * em.detach(cmp);
		 */
		
		Company fetchedCmp = cmpRepo.findByCompanyName("test2");
		
		System.out.println(fetchedCmp);
		//emp.setCompnayToEmpMap(cmp);
		
		//cmp.setEmployeeList(Collections.singletonList(emp));
		
		
		System.out.println("Exit from insertCompanyWithEmp");
	}
	
	public void getCmpDetilaInDto() {
		List<CustomCmpDto> customDtos = cmpRepo.findDtoByName("test");
		System.out.println(customDtos);
	}
	
	public void insertMultipleCompany() {
		for(int i=0; i<10;i++) {
		Company c = new Company();
		c.setCity("test");
		c.setCompanyName("test5"+i);
		em.persist(c);
		}
		try {
			Thread.sleep(20*1000);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		System.out.println("saved2");
		System.out.println("Done2");
	}
	
	public void mergeCompany() {
		Company c = em.find(Company.class, 1);
		Company c2 = em.find(Company.class, 2);
		for(Employee emp : c2.getEmployeeList()) {
			emp.setCompnayToEmpMap(c);
			c.getEmployeeList().add(emp);
		}
		//c.getEmployeeList().addAll(c2.getEmployeeList());
		c.setCity(c.getCity()+1);
		c2.setCity(c2.getCity()+1);
		//em.persist(c);
		//em.persist(c2);
		System.out.println("saved");
		System.out.println("Done");
	}
	
	public void callJpaRepo() {
		List<Object> list = cmpRepo.countDemo();
		//System.out.println(list.get(0).getClass());
		System.out.println(list);
	}
	
	public void criteriaBuilderDemo() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(Company.class);
        
        /* FROM cause*/
        Root<Company> cmp = criteriaQuery.from(Company.class);
       
        
        /* WHERE cause */
        criteriaQuery.where(criteriaBuilder.equal(cmp.get("city"), "test"));
        
        /* SELECT cause */
        criteriaQuery.select(criteriaBuilder.countDistinct(cmp.get("id")));
        
        
        
        TypedQuery tq = em.createQuery(criteriaQuery);
        List result = tq.getResultList();
        System.out.println(result);
	}
	
	public void criteriaBuilderDemo2() {
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        CriteriaQuery criteriaQuery = criteriaBuilder.createQuery(CustomCmpDto.class);
        
        /* FROM cause*/
        Root<Company> cmp = criteriaQuery.from(Company.class);
        //cmp.joi
        cmp.join("employeeList", JoinType.INNER);
        
        /* WHERE cause */
        //criteriaQuery.where(criteriaBuilder.equal(cmp.get("city"), "test"));
        
        //criteriaQuery.groupBy(cmp.get("city"));
        
        /* SELECT cause */
        //criteriaQuery.select(criteriaBuilder.countDistinct(cmp.get("id")));
        
        //criteriaQuery.orderBy(criteriaBuilder.desc(criteriaBuilder.countDistinct(cmp.get("id"))));
        
        criteriaQuery.multiselect(cmp.get("id"), cmp.get("city"));
        
        TypedQuery tq = em.createQuery(criteriaQuery);
        List result = tq.getResultList();
        System.out.println(result);
	}
	
	public void deleteCompanyWithEmp() {
		Company cmp = cmpRepo.findByCompanyName("test");
		cmp.setCity(cmp.getCity()+"5");
		cmpRepo.delete(cmp);
		
	}
}
