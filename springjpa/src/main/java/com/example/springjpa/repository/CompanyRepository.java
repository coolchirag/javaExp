package com.example.springjpa.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.EntityGraph;
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
	
	@Query("from Company")
	List<Company> findAllCmp();
	
	@Query("select c.id as id1_0_,c.city as city2_0_,c.cmpColumn1 as cmp_colu3_0_,c.cmpColumn10 as cmp_colu4_0_,c.cmpColumn11 as cmp_colu5_0_,c.cmpColumn12 as cmp_colu6_0_,c.cmpColumn13 as cmp_colu7_0_,c.cmpColumn14 as cmp_colu8_0_,c.cmpColumn15 as cmp_colu9_0_,c.cmpColumn16 as cmp_col10_0_,c.cmpColumn17 as cmp_col11_0_,c.cmpColumn18 as cmp_col12_0_,c.cmpColumn19 as cmp_col13_0_,c.cmpColumn2 as cmp_col14_0_,c.cmpColumn20 as cmp_col15_0_,c.cmpColumn21 as cmp_col16_0_,c.cmpColumn22 as cmp_col17_0_,c.cmpColumn23 as cmp_col18_0_,c.cmpColumn24 as cmp_col19_0_,c.cmpColumn25 as cmp_col20_0_,c.cmpColumn26 as cmp_col21_0_,c.cmpColumn27 as cmp_col22_0_,c.cmpColumn28 as cmp_col23_0_,c.cmpColumn29 as cmp_col24_0_,c.cmpColumn3 as cmp_col25_0_,c.cmpColumn30 as cmp_col26_0_,c.cmpColumn31 as cmp_col27_0_,c.cmpColumn32 as cmp_col28_0_,c.cmpColumn33 as cmp_col29_0_,c.cmpColumn34 as cmp_col30_0_,c.cmpColumn35 as cmp_col31_0_,c.cmpColumn36 as cmp_col32_0_,c.cmpColumn37 as cmp_col33_0_,c.cmpColumn38 as cmp_col34_0_,c.cmpColumn39 as cmp_col35_0_,c.cmpColumn4 as cmp_col36_0_,c.cmpColumn40 as cmp_col37_0_,c.cmpColumn41 as cmp_col38_0_,c.cmpColumn42 as cmp_col39_0_,c.cmpColumn43 as cmp_col40_0_,c.cmpColumn44 as cmp_col41_0_,c.cmpColumn45 as cmp_col42_0_,c.cmpColumn46 as cmp_col43_0_,c.cmpColumn47 as cmp_col44_0_,c.cmpColumn48 as cmp_col45_0_,c.cmpColumn49 as cmp_col46_0_,c.cmpColumn5 as cmp_col47_0_,c.cmpColumn50 as cmp_col48_0_,c.cmpColumn6 as cmp_col49_0_,c.cmpColumn7 as cmp_col50_0_,c.cmpColumn8 as cmp_col51_0_,c.cmpColumn9 as cmp_col52_0_,c.companyName as name53_0_,c.isActive as is_acti54_0_  from Company c")
	List<Object[]> findAllCmp2();
	
	@Query("select new com.example.springjpa.bean.Company(c.id as id1_0_,c.city as city2_0_,c.cmpColumn1 as cmp_colu3_0_,c.cmpColumn10 as cmp_colu4_0_,c.cmpColumn11 as cmp_colu5_0_,c.cmpColumn12 as cmp_colu6_0_,c.cmpColumn13 as cmp_colu7_0_,c.cmpColumn14 as cmp_colu8_0_,c.cmpColumn15 as cmp_colu9_0_,c.cmpColumn16 as cmp_col10_0_,c.cmpColumn17 as cmp_col11_0_,c.cmpColumn18 as cmp_col12_0_,c.cmpColumn19 as cmp_col13_0_,c.cmpColumn2 as cmp_col14_0_,c.cmpColumn20 as cmp_col15_0_,c.cmpColumn21 as cmp_col16_0_,c.cmpColumn22 as cmp_col17_0_,c.cmpColumn23 as cmp_col18_0_,c.cmpColumn24 as cmp_col19_0_,c.cmpColumn25 as cmp_col20_0_,c.cmpColumn26 as cmp_col21_0_,c.cmpColumn27 as cmp_col22_0_,c.cmpColumn28 as cmp_col23_0_,c.cmpColumn29 as cmp_col24_0_,c.cmpColumn3 as cmp_col25_0_,c.cmpColumn30 as cmp_col26_0_,c.cmpColumn31 as cmp_col27_0_,c.cmpColumn32 as cmp_col28_0_,c.cmpColumn33 as cmp_col29_0_,c.cmpColumn34 as cmp_col30_0_,c.cmpColumn35 as cmp_col31_0_,c.cmpColumn36 as cmp_col32_0_,c.cmpColumn37 as cmp_col33_0_,c.cmpColumn38 as cmp_col34_0_,c.cmpColumn39 as cmp_col35_0_,c.cmpColumn4 as cmp_col36_0_,c.cmpColumn40 as cmp_col37_0_,c.cmpColumn41 as cmp_col38_0_,c.cmpColumn42 as cmp_col39_0_,c.cmpColumn43 as cmp_col40_0_,c.cmpColumn44 as cmp_col41_0_,c.cmpColumn45 as cmp_col42_0_,c.cmpColumn46 as cmp_col43_0_,c.cmpColumn47 as cmp_col44_0_,c.cmpColumn48 as cmp_col45_0_,c.cmpColumn49 as cmp_col46_0_,c.cmpColumn5 as cmp_col47_0_,c.cmpColumn50 as cmp_col48_0_,c.cmpColumn6 as cmp_col49_0_,c.cmpColumn7 as cmp_col50_0_,c.cmpColumn8 as cmp_col51_0_,c.cmpColumn9 as cmp_col52_0_,c.companyName as name53_0_,c.isActive as is_acti54_0_)  from Company c")
	List<Company> findAllCmp3();

	Company findByCompanyName(String name);
	
	List<Company> findByCity(String city);
	
	//@EntityGraph("cmpwithemp")
	@EntityGraph("cmpwithempwithprojectmst")
	List<Company> findAll();
	
	@Query("from Company where companyName=:name")
	Company findCMTest(@Param("name") String name);
	
	Company findFirstByCompanyNameOrderByIdDesc(String name);
	
	@Query("select distinct c from Company c inner join fetch c.emp e where c.city = :city")
	List<Company> findByCityWithEmployee(@Param("city") String city);
	
	@Query("select c from Company c inner join fetch c.emp e where c.id in (:ids) order by c.id")
	List<Company> findByIdsWithEmployee(@Param("ids") Set<Integer> ids);
	
	@Query("select c.city from Company c group by c.city having count(1) > 1")
	List<Object> findByCityCount();
	
	@Query("select group_concat(c.city) from Company c group by c.city")
	List<Object> findByCityCountGroup();
	
	@Query("select distinct c.city from Company c where c.companyName=:name")
	List<String> findDistinctCityByCompanyName(@Param("name") String name);
	
	@Query("select distinct c from Company c inner join c.emp e on e.salary > 10000 "
			+ "left join c.emp e2 on e2.salary <1000"
			+ "where  c.companyName=:name and c.isActive = true and e2.id is null")
	List<Company> findByNameWithEmployee(@Param("name") String name);
	
	@Query("select distinct c from Company c inner join c.emp e on e.salary >:salary "
			+ "where c.isActive = true")
	List<Company> findByNameWithEmployeeSalary(@Param("salary") Integer salary);
	
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
