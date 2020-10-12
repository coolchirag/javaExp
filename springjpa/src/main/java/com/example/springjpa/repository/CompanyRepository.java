package com.example.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {
	
	@Query("select count(c.id), c.city from Company c group by c.city")
	List<Object> countDemo();

}
