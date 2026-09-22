package com.example.springjpa.service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.sql.DataSource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.example.springjpa.bean.Company;
import com.example.springjpa.bean.Employee;
import com.example.springjpa.repository.CompanyRepository;

@Service
@Transactional
public class BulkOperationService {

	private static final Logger LOG = LoggerFactory.getLogger(BulkOperationService.class);

	@Autowired
	private CompanyRepository cmpRepo;

	@PersistenceContext
	private EntityManager entityManager;

	@Autowired
	private DataSource dataSource;

	public void insertCompaniesWithEmployees() {
		long timestamp = System.currentTimeMillis();
		long totalStartTime = System.currentTimeMillis();
		for (int i = 1; i <= 32828; i++) {
		//for (int i = 1; i <= 2; i++) {
			long companyStartTime = System.currentTimeMillis();
			Company company = new Company();
			String companyName = "bulk_cmp_" + i + "_" + timestamp;
			company.setCompanyName(companyName);
			company.setCity("bulk_city");
			company.setIsActive(true);

			List<Employee> employees = new ArrayList<>();
			for (int j = 1; j <= 15; j++) {
				Employee employee = new Employee();
				employee.setEmployeeName(companyName + "_emp_" + j);
				employee.setSalary(100000);
				employees.add(employee);
			}
			company.setEmp(employees);
			LOG.info("Company insert Before save. index={}, name={}, id={}, timeMs={}",
					i, companyName, company.getId(), System.currentTimeMillis() - companyStartTime);
			cmpRepo.save(company);
			LOG.info("Company insert completed. index={}, name={}, id={}, timeMs={}",
					i, companyName, company.getId(), System.currentTimeMillis() - companyStartTime);
		}
		LOG.info("Bulk company insert completed. companyCount=100, employeePerCompany=100, totalTimeMs={}",
				System.currentTimeMillis() - totalStartTime);
	}

	public void insertCompaniesWithEmployeesNative() {
		long timestamp = System.currentTimeMillis();
		long totalStartTime = System.currentTimeMillis();

		Number maxCompanyId = (Number) entityManager
				.createNativeQuery("select coalesce(max(id), 0) from company")
				.getSingleResult();
		Number maxEmployeeId = (Number) entityManager
				.createNativeQuery("select coalesce(max(id), 0) from employee")
				.getSingleResult();

		int nextCompanyId = maxCompanyId.intValue();
		int nextEmployeeId = maxEmployeeId.intValue();

		for (int i = 1; i <= 32828; i++) {
			long companyStartTime = System.currentTimeMillis();
			nextCompanyId++;
			String companyName = "bulk_cmp_" + i + "_" + timestamp;

			entityManager.createNativeQuery(
					"insert into company (id, name, city, is_active) values (?, ?, ?, ?)")
					.setParameter(1, nextCompanyId)
					.setParameter(2, companyName)
					.setParameter(3, "bulk_city")
					.setParameter(4, 1)
					.executeUpdate();

			for (int j = 1; j <= 15; j++) {
				nextEmployeeId++;
				entityManager.createNativeQuery(
						"insert into employee (id, emp_name, salary, cmp_id, is_active) values (?, ?, ?, ?, ?)")
						.setParameter(1, nextEmployeeId)
						.setParameter(2, companyName + "_emp_" + j)
						.setParameter(3, 100000)
						.setParameter(4, nextCompanyId)
						.setParameter(5, 1)
						.executeUpdate();
			}

			LOG.info("Native company insert completed. index={}, name={}, id={}, timeMs={}",
					i, companyName, nextCompanyId, System.currentTimeMillis() - companyStartTime);
		}

		LOG.info("Native bulk company insert completed. companyCount=32828, employeePerCompany=15, totalTimeMs={}",
				System.currentTimeMillis() - totalStartTime);
	}

	@Transactional(propagation = Propagation.NOT_SUPPORTED)
	public void insertCompaniesWithEmployeesJdbc() {
		long timestamp = System.currentTimeMillis();
		long totalStartTime = System.currentTimeMillis();

		try (Connection connection = dataSource.getConnection()) {
			boolean previousAutoCommit = connection.getAutoCommit();
			connection.setAutoCommit(false);
			try {
				int nextCompanyId = fetchMaxId(connection, "company_bulk");
				int nextEmployeeId = fetchMaxId(connection, "employee_bulk");

				try (PreparedStatement companyInsert = connection.prepareStatement(
						"insert into company_bulk (id, name, city, is_active) values (?, ?, ?, ?)");
						PreparedStatement employeeInsert = connection.prepareStatement(
								"insert into employee_bulk (id, emp_name, salary, cmp_id, is_active) values (?, ?, ?, ?, ?)")) {

					for (int i = 1; i <= 32828; i++) {
						long companyStartTime = System.currentTimeMillis();
						nextCompanyId++;
						String companyName = "bulk_cmp_" + i + "_" + timestamp;

						companyInsert.setInt(1, nextCompanyId);
						companyInsert.setString(2, companyName);
						companyInsert.setString(3, "bulk_city");
						companyInsert.setInt(4, 1);
						companyInsert.executeUpdate();

						for (int j = 1; j <= 15; j++) {
							nextEmployeeId++;
							employeeInsert.setInt(1, nextEmployeeId);
							employeeInsert.setString(2, companyName + "_emp_" + j);
							employeeInsert.setInt(3, 100000);
							employeeInsert.setInt(4, nextCompanyId);
							employeeInsert.setInt(5, 1);
							employeeInsert.executeUpdate();
						}

						LOG.info("JDBC company insert completed. index={}, name={}, id={}, timeMs={}",
								i, companyName, nextCompanyId, System.currentTimeMillis() - companyStartTime);
					}
				}

				connection.commit();
				LOG.info("JDBC bulk company insert completed. companyCount=32828, employeePerCompany=15, totalTimeMs={}",
						System.currentTimeMillis() - totalStartTime);
			} catch (Exception e) {
				connection.rollback();
				throw e;
			} finally {
				connection.setAutoCommit(previousAutoCommit);
			}
		} catch (SQLException e) {
			throw new RuntimeException("JDBC bulk insert failed", e);
		}
	}

	private int fetchMaxId(Connection connection, String tableName) throws SQLException {
		try (PreparedStatement statement = connection.prepareStatement(
				"select coalesce(max(id), 0) from " + tableName);
				ResultSet resultSet = statement.executeQuery()) {
			resultSet.next();
			return resultSet.getInt(1);
		}
	}
}
