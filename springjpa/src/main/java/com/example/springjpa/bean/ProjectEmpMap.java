package com.example.springjpa.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

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
	
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="emp_id"/*, insertable = false, updatable = false*/)
	@Where(clause=" is_active = 1 ")
	//@Transient
	private Employee projectEmpMapEmployee;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name="project_id"/*, insertable = false, updatable = false*/)
	//@Transient
	@Where(clause=" is_active = 1 ")
	private Project projectEmpMapProject;
	
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



	

	public Employee getProjectEmpMapEmployee() {
		return projectEmpMapEmployee;
	}



	public void setProjectEmpMapEmployee(Employee projectEmpMapEmployee) {
		this.projectEmpMapEmployee = projectEmpMapEmployee;
	}



	public Project getProjectEmpMapProject() {
		return projectEmpMapProject;
	}



	public void setProjectEmpMapProject(Project projectEmpMapProject) {
		this.projectEmpMapProject = projectEmpMapProject;
	}



	@Override
	public String toString() {
		return "ProjectEmpMap [id=" + id + ", projectId=" + projectId + ", empId=" + empId + ", isActive=" + isActive
				+ "]";
	}



	



	
	

}
