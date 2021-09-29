package com.example.springjpa.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Fetch;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.bean.Employee;
import com.example.springjpa.dto.CustomCmpDto;
import com.example.springjpa.repository.CompanyRepository;

@Service
@Transactional
public class CompanyService {
	
	@Autowired
	private EntityManager em;
	
	@Autowired
	private CompanyRepository cmpRepo;

	public void getCompany() {
		Company c = em.find(Company.class, 2);//em.find(Company.class, 2);
		/*
		 * c.setCity("c5"); c.setCompanyName("c5"); c.setEmployeeList(new
		 * ArrayList<Employee>());
		 */
		System.out.println("Compnay fetched");
		Company c2 = cmpRepo.findCMTest("test");
		System.out.println("Compnay fetched c2");
		/*
		 * Employee emp = new Employee(); emp.setEmployeeName("emp2_3");
		 * emp.setCmpName("IBM"); emp.setSalary(6000); emp.setCompnayToEmpMap(c);
		 * c.getEmployeeList().add(emp);
		 */
		//c.getEmployeeList().remove(0);
		//c.setCity(c.getCity()+"test");
		//c.setCompanyName("test");
		
		System.out.println("City : "+c.getCity()+" : "+c2.getCity());
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
	
	public void insertCompanyWithEmp() {
		Company cmp = new Company();
		cmp.setCompanyName("test");
		cmp.setCity("cmp");
		
		Employee emp = new Employee();
		emp.setEmployeeName("test");
		emp.setSalary(100000);
		emp.setCompnayToEmpMap(cmp);
		
		cmp.setEmployeeList(Collections.singletonList(emp));
		
		cmpRepo.save(cmp);
		System.out.println("Exit from insertCompanyWithEmp");
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
