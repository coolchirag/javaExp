package com.example.springjpa.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.Company;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer> {

	@Query("select count(c.id), c.city from Company c group by c.city")
	List<Object> countDemo();

	Company findByCompanyName(String name);
	
	@Query("from Company where companyName=:name")
	Company findCMTest(@Param("name") String name);
	
	Company findFirstByCompanyNameOrderByIdDesc(String name);
	
	@Query("select c.city from Company c group by c.city having count(1) > 1")
	List<Object> findByCityCount();
	
	@Query("select distinct c.city from Company c where c.companyName=:name")
	List<String> findDistinctCityByCompanyName(@Param("name") String name);
	
	@Query("select distinct c from Company c inner join c.emp e on e.salary > 10000 "
			+ "left join c.emp e2 on e2.salary <1000"
			+ "where  c.companyName=:name and c.isActive = true and e2.id is null")
	List<Company> findByNameWithEmployee(@Param("name") String name);

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

	

}
