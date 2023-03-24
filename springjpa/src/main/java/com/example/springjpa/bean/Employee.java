package com.example.springjpa.bean;

import java.util.List;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "employee")
//@Where(clause = " is_active = 1 ")
@SQLDelete(sql = "update employee set is_active = null where id = ? ")
public class Employee {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "emp_name")
	private String employeeName;
	
	@Column(name = "salary")
	private Integer salary;
	
	@Column(name = "cmp_id", insertable = false, updatable = false )
	//@Transient
	private Integer companyId;
	
	@ManyToOne(/* cascade = CascadeType.ALL */)
	//@Fetch(FetchMode.SELECT)
	
	@JoinColumn(name="cmp_id"/*, insertable = false, updatable = false*/)
	//@Transient
	@Where(clause=" is_active = 1 ")
	
	//@OneToOne
	//@JoinColumn(name = "cmp_id", referencedColumnName = "id" /*, insertable = false, updatable = false*/)
	private Company compnayToEmpMap;
	
	@OneToMany(mappedBy = "projectEmpMapEmployee")
	//@Transient
	private Set<ProjectEmpMap> empProjectEmp;
	
	@OneToMany
	@JoinTable(name = "project_emp_map",
			joinColumns =  {@JoinColumn(name="emp_id")},
			inverseJoinColumns = {@JoinColumn(name="project_id")})
	private Set<Project> empProject;
	
	@Column(name = "is_active")
	private Integer isActive;
	
	
	@PrePersist
	private void onCreate() {
		isActive = 1;
	}
	
	

	public Integer getIsActive() {
		return isActive;
	}



	public void setIsActive(Integer isActive) {
		this.isActive = isActive;
	}



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

	

	public Set<ProjectEmpMap> getEmpProjectEmp() {
		return empProjectEmp;
	}



	public void setEmpProjectEmp(Set<ProjectEmpMap> empProjectEmp) {
		this.empProjectEmp = empProjectEmp;
	}



	public Set<Project> getEmpProject() {
		return empProject;
	}



	public void setEmpProject(Set<Project> empProject) {
		this.empProject = empProject;
	}



	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", salary=" + salary + ", companyId="
				+ companyId + ", isActive=" + isActive + "]";
	}



}
