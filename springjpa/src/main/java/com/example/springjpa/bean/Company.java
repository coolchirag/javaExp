package com.example.springjpa.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "company")
@Where(clause = "is_active=1")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private int id;

	@Column(name = "name")
	private String companyName;
	
	@Column(name = "city")
	private String city;
	
	@OneToMany(mappedBy = "compnayToEmpMap", cascade = CascadeType.ALL/* , fetch=FetchType.EAGER */)
	//@Where(clause = "test2=1")
	//@OneToMany(mappedBy="compnayToEmpMap")
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "company_id"/* , insertable = false, updatable = false */)
	private List<Employee> employeeList;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	@PrePersist
	private void onCreate() {
		isActive = true;
	}
	
	/*
	@PreUpdate
	void updateCompName()
	{
		if(employeeList != null && !employeeList.isEmpty())
		{
			for(Employee emp : employeeList)
			{
				emp.setCmpName(companyName);
			}
		}
		System.out.println("inside ipdate comapny");
	}*/
	
	@PreUpdate
	protected void onUpdate() {
		companyName = companyName+"5";
		System.out.println("on update");
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public List<Employee> getEmployeeList() {
		return employeeList;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		this.employeeList = employeeList;
	}
	
	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", city=" + city + ", employeeList="
				+ employeeList + "]";
	}
	

		
}
