package com.example.springjpa.bean;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.PostRemove;
import javax.persistence.PrePersist;
import javax.persistence.PreRemove;
import javax.persistence.PreUpdate;
import javax.persistence.Table;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "company")
//@Where(clause = "is_active=1")
@SQLDelete(sql = "update company set is_active = null where id = ? ")
public class Company {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private int id;

	@Column(name = "name")
	private String companyName;
	
	@Column(name = "city")
	private String city;
	
	//@OneToMany(mappedBy = "compnayToEmpMap", cascade = CascadeType.ALL/* , fetch=FetchType.EAGER */)
	//@Where(clause = "test2=1")
	//@OneToMany(mappedBy="compnayToEmpMap")
	//@OneToMany(cascade = CascadeType.ALL)
	//@JoinColumn(name = "company_id"/* , insertable = false, updatable = false */)
	//private List<Employee> employeeList;
	
	@OneToMany(mappedBy = "compnayToEmpMap", fetch = FetchType.LAZY)
	//@Fetch(FetchMode.JOIN)
	@Where(clause = "is_active = 1")
	private List<Employee> emp;
	
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
		//companyName = companyName+"5";
		System.out.println("on update");
	}

	

	public List<Employee> getEmp() {
		return emp;
	}

	public void setEmp(List<Employee> emp) {
		this.emp = emp;
	}

	@PreRemove
	private void preDelete() {
		System.out.println("Pre Delete");
		setCity("predeleted");
	}
	
	@PostRemove
	private void postDelete() {
		System.out.println("Post Delete");
		setCompanyName("postdeleted");
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
		return null;
	}

	public void setEmployeeList(List<Employee> employeeList) {
		//this.employeeList = employeeList;
	}
	
	

	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	/*
	 * @Override public String toString() { return "Company [id=" + id +
	 * ", companyName=" + companyName + ", city=" + city + ", employeeList=" +
	 * employeeList + "]"; }
	 */
	

		
}
