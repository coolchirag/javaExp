package com.example.springjpa.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.example.springjpa.bean.Company;
import com.example.springjpa.dto.CustomCmpDto;

@Repository
public interface CompanyRepository extends JpaRepository<Company, Integer>, JpaSpecificationExecutor<Company> {

	@Query("select count(c.id), c.city from Company c group by c.city")
	List<Object> countDemo();

	Company findByCompanyName(String name);
	
	List<Company> findByCity(String city);
	
	@Query("from Company where companyName=:name")
	Company findCMTest(@Param("name") String name);
	
	Company findFirstByCompanyNameOrderByIdDesc(String name);
	
	@Query("select distinct c from Company c inner join fetch c.emp e where c.city = :city")
	List<Company> findByCityWithEmployee(@Param("city") String city);
	
	@Query("select c from Company c inner join fetch c.emp e where c.id in (:ids) order by c.id")
	List<Company> findByIdsWithEmployee(@Param("ids") Set<Integer> ids);
	
	@Query("select c.city from Company c group by c.city having count(1) > 1")
	List<Object> findByCityCount();
	
	@Query("select distinct c.city from Company c where c.companyName=:name")
	List<String> findDistinctCityByCompanyName(@Param("name") String name);
	
	@Query("select distinct c from Company c inner join c.emp e on e.salary > 10000 "
			+ "left join c.emp e2 on e2.salary <1000"
			+ "where  c.companyName=:name and c.isActive = true and e2.id is null")
	List<Company> findByNameWithEmployee(@Param("name") String name);
	
	@Query("select new com.example.springjpa.dto.CustomCmpDto(c.id as id, c.city as city) from Company c where c.companyName=:name")
	List<CustomCmpDto> findDtoByName(@Param("name") String name);

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
