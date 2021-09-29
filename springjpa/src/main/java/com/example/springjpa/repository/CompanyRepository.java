package com.example.springjpa.repository;

import java.util.List;

import javax.persistence.EntityManager;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springjpa.AppContextAware;
import com.example.springjpa.bean.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select count(c.id), c.city from Company c group by c.city")
	List<Object> countDemo();

	Company findByCompanyName(String name);
	
	@Query("select c from Company c where c.companyName=:name")
	Company findCMTest(@Param("name") String name);

	/*
	 * @Override default void delete(Integer id) { delete(findOne(id));
	 * 
	 * }
	 * 
	 * @Override default void delete(Company entity) { entity.setIsActive(null);
	 * 
	 * }
	 * 
	 * @Override default void delete(Iterable<? extends Company> entities) {
	 * entities.forEach(this::delete);
	 * 
	 * }
	 */

	@Override
	default void delete(Company entity) {
		EntityManager entityManager = AppContextAware.getApplicationContext().getBean(EntityManager.class);
		entity.setIsActive(null);
		//entityManager.flush();
		entityManager.remove(entity);
	}

}
