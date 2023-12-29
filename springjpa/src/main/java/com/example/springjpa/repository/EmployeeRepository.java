package com.example.springjpa.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.Employee;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, Integer>, JpaSpecificationExecutor<Employee> {

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
	
	//TO get specific column.
	@Query("select companyId from Employee where employeeName = :employeeName")
	Set<Integer> findCompanyIdByEmployeeName(@Param("employeeName") String employeeName);
	
	@Query("SELECT emp from Employee emp left join  emp.compnayToEmpMap cmp where cmp.companyName = 'test'")
	List<Employee> getEmployeesByCompanyName();
	
	@Query("select emp from Employee emp inner join emp.compnayToEmpMap cmp where emp.employeeName like %:employeeName%")
	List<Employee> findByEmployeeName(@Param("employeeName") String employeeName, Pageable page);
	
	List<Employee> findByCompnayToEmpMapCompanyName(String companyName);
	
	Employee findByEmployeeNameAndCompanyId(String name, Integer id);

	long countByCompanyId(Integer companyId);
	
}
