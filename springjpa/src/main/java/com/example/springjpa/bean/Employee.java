package com.example.springjpa.bean;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "Employee")
//@Where(clause = " salary = 1000 ")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "emp_name")
	private String employeeName;
	
	@Column(name = "salary")
	private Integer salary;
	
	@Column(name = "cmp_name")
	private String cmpName;
	
	@Column(name="company_id", insertable = false, updatable = false)
	//@Transient
	private Integer companyId;
	
	@ManyToOne
	//@Fetch(FetchMode.SELECT)
	@JoinColumn(name="company_id")
	//@Transient
	//@Where(clause=" city = 'ahmedabad' ")
	private Company compnayToEmpMap;
	
	

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getEmployeeName() {
		return employeeName;
	}

	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}

	public Integer getSalary() {
		return salary;
	}

	public void setSalary(Integer salary) {
		this.salary = salary;
	}

	public Company getCompnayToEmpMap() {
		return compnayToEmpMap;
	}

	public void setCompnayToEmpMap(Company compnayToEmpMap) {
		//this.companyId = compnayToEmpMap.getId();
		this.compnayToEmpMap = compnayToEmpMap;
	}
	
	

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public String getCmpName() {
		return cmpName;
	}

	public void setCmpName(String cmpName) {
		this.cmpName = cmpName;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", salary=" + salary + ", cmpName=" + cmpName
				+ ", companyId=" + companyId +"]";
	}

	
	
	
	
	
}
