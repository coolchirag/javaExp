package com.example.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>{

	/*
	 * @Override default void delete(Integer id) { delete(findOne(id)); }
	 * 
	 * @Override default void delete(Employee entity) { entity.setIsActive(null);
	 * 
	 * }
	 * 
	 * @Override default void delete(Iterable<? extends Employee> entities) {
	 * entities.forEach(this::delete); }
	 */
	
	@Query("SELECT emp from Employee emp left join  emp.compnayToEmpMap cmp where cmp.companyName = 'test'")
	List<Employee> getEmployeesByCompanyName();
	
	List<Employee> findByCompnayToEmpMapCompanyName(String companyName);

	
}
