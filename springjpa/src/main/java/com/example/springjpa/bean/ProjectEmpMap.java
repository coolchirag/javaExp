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
import javax.persistence.OneToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "project_emp_map")
@Where(clause = " is_active = 1 ")
public class ProjectEmpMap {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "project_id", insertable = false, updatable = false )
	//@Transient
	private Integer projectId;
	
	@Column(name = "emp_id", insertable = false, updatable = false )
	//@Transient
	private Integer empId;
	
	
	@JoinColumn(name="emp_id"/*, insertable = false, updatable = false*/)
	//@Transient
	@Where(clause=" is_active = 1 ")
	
	//@OneToOne
	//@JoinColumn(name = "cmp_id", referencedColumnName = "id" /*, insertable = false, updatable = false*/)
	private Employee projectMapEmployee;
	
	@JoinColumn(name="project_id"/*, insertable = false, updatable = false*/)
	//@Transient
	@Where(clause=" is_active = 1 ")
	
	//@OneToOne
	//@JoinColumn(name = "cmp_id", referencedColumnName = "id" /*, insertable = false, updatable = false*/)
	private Project projectMapProject;
	
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



	public Integer getProjectId() {
		return projectId;
	}



	public void setProjectId(Integer projectId) {
		this.projectId = projectId;
	}



	public Integer getEmpId() {
		return empId;
	}



	public void setEmpId(Integer empId) {
		this.empId = empId;
	}



	public Employee getProjectMapEmployee() {
		return projectMapEmployee;
	}



	public void setProjectMapEmployee(Employee projectMapEmployee) {
		this.projectMapEmployee = projectMapEmployee;
	}



	public Project getProjectMapProject() {
		return projectMapProject;
	}



	public void setProjectMapProject(Project projectMapProject) {
		this.projectMapProject = projectMapProject;
	}

	@Override
	public String toString() {
		return "ProjectEmpMap [id=" + id + ", projectId=" + projectId + ", empId=" + empId + ", projectMapEmployee="
				+ projectMapEmployee + ", projectMapProject=" + projectMapProject + ", isActive=" + isActive + "]";
	}

}
