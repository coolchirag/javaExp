package com.websystique.hibernate.model;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Where;
import org.hibernate.annotations.WhereJoinTable;

@Entity
@Table(name = "Company")
public class Company {

	@Id
	@GeneratedValue()
	private int id;

	@Column(name = "name")
	private String companyName;
	
	@Column(name = "city")
	private String city;
	
	@OneToMany(mappedBy="compnayToEmpMap", cascade=CascadeType.ALL, orphanRemoval=true, fetch=FetchType.EAGER)
	private List<Employee> employeeList;
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

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", city=" + city + ", employeeList="
				+ employeeList + "]";
	}
	

		
}
