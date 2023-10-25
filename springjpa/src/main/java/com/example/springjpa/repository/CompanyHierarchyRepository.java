package com.example.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.springjpa.bean.CompanyHierarchy;

public interface CompanyHierarchyRepository extends JpaRepository<CompanyHierarchy, Integer> {

	//List<CompanyHierarchy> findByPCmpId(Integer pid);
}
