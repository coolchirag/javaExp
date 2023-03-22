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
	
	
	@JoinColumn(name="cmp_id"/*, insertable = false, updatable = false*/)
	//@Transient
	@Where(clause=" is_active = 1 ")
	
	//@OneToOne
	//@JoinColumn(name = "cmp_id", referencedColumnName = "id" /*, insertable = false, updatable = false*/)
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
