package com.example.springjpa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.ProjectEmpMap;

@Repository
public interface ProjectRepository extends JpaRepository<ProjectEmpMap, Integer>{
	
}
