package com.example.springjpa.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.springjpa.bean.CompanyHierarchy;
import com.example.springjpa.repository.CompanyHierarchyRepository;

@Service
@Transactional
public class CompanyHierarchyService {

	@Autowired
	private CompanyHierarchyRepository companyHierarchyRepo;
	
	public void getChildCompanyes() {
		List<CompanyHierarchy> childCmps = companyHierarchyRepo.findAll();
		childCmps.forEach(bean -> System.out.println(bean));
	}
}
