package com.example.springjpa.service;

import java.time.LocalDateTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.repository.CompanyRepository;

@Service
@Transactional
public class CompanyJPQLService {

	private static final Logger LOG = LoggerFactory.getLogger(CompanyJPQLService.class);

	@Autowired
	private CompanyRepository cmpRepo;

	public void updateCityWithJpqlAndPrint() {
		Company company = cmpRepo.findById(1)
				.orElseThrow(() -> new IllegalStateException("Company with id=1 not found"));
		LOG.info("Fetched company id=1, city from bean before JPQL update={}", company.getCity());

		String newCity = "jpql_city_" + LocalDateTime.now();
		int updatedRows = cmpRepo.updateCityById(newCity, 1);
		LOG.info("JPQL update completed. updatedRows={}, newCity={}", updatedRows, newCity);

		String nativeCity = cmpRepo.findCityByIdNative(1);
		System.out.println("cityname from native query: " + nativeCity);

		System.out.println("cityname from bean: " + company.getCity());
	}
}
