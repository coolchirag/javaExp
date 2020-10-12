package com.chirag.hibernate.entitymanager.examples.service;

import javax.persistence.EntityManager;

import com.chirag.hibernate.entitymanager.examples.HibernateUtility;
import com.chirag.hibernate.entitymanager.examples.model.Company;

public class CompanyService {

	public void getCompany() {
		EntityManager em = HibernateUtility.getEntityManager();
		Company c = em.find(Company.class, 1);
		System.out.println("Compnay fetched");
		System.out.println(c);
	}
}
