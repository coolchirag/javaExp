package com.example.springjpa.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.PrePersist;
import javax.persistence.Table;

import org.hibernate.annotations.Where;

@Entity
@Table(name = "project")
@Where(clause = " is_active = 1 ")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "cmp_id", insertable = false, updatable = false )
	//@Transient
	private Integer companyId;
	
	
	@ManyToOne
	@JoinColumn(name="cmp_id"/*, insertable = false, updatable = false*/)
	@Where(clause=" is_active = 1 ")
	private Company projectCompany;
	
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

	
	public String getProjectName() {
		return projectName;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	
	
	

	public Company getProjectCompany() {
		return projectCompany;
	}



	public void setProjectCompany(Company projectCompany) {
		this.projectCompany = projectCompany;
	}



	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}



	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", companyId=" + companyId + ", projectCompany="
				+ projectCompany + ", isActive=" + isActive + "]";
	}

	
	
}
