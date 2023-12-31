package com.example.springjpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springjpa.bean.Project;
import com.example.springjpa.repository.ProjectRepository;

@Service
@Transactional
public class ProjectService {

	@Autowired
	private ProjectRepository projectRepository;

	public void getProject() {
		List<Project> projects = projectRepository.findAll((root, query, criteriaBuilder) -> null);
		System.out.println(projects);
	}
}
