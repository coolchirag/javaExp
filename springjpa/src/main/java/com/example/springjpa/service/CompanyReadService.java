package com.example.springjpa.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.repository.CompanyRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

@Service
@Transactional(readOnly = true)
public class CompanyReadService {
	
	@Autowired
	private CompanyRepository cmpRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Transactional(readOnly = true)
	public void fetchAllCompanyRead() {
		System.out.println("Inside fetchAllCompanyRead");
		List<Company> all = cmpRepo.findAll();
		Object[] transactionDetails = (Object[]) entityManager.createNativeQuery(
				"SELECT conn.processlist_id, ps.thread_id, trx.trx_id "
						+ "FROM (SELECT CONNECTION_ID() AS processlist_id) conn "
						+ "LEFT JOIN performance_schema.threads ps "
						+ "ON ps.processlist_id = conn.processlist_id "
						+ "LEFT JOIN information_schema.innodb_trx trx "
						+ "ON trx.trx_mysql_thread_id = conn.processlist_id")
				.getSingleResult();
		System.out.println("MySQL process/connection ID: " + transactionDetails[0]);
		System.out.println("Performance Schema thread ID: " + transactionDetails[1]);
		System.out.println("Database transaction ID: " + transactionDetails[2]);
		all.forEach(cmp -> System.out.println(cmp));
		System.out.println(all);
		
	}
}
