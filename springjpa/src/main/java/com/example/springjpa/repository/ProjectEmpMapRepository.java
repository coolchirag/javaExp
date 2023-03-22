package com.example.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.Project;

@Repository
public interface ProjectEmpMapRepository extends JpaRepository<Project, Integer>{
	
}
