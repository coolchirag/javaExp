package com.example.springjpa.bean;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.persistence.Transient;
@Entity
@Table(name = "project")
public class Project {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", insertable = false, updatable = false)
	private Integer id;

	@Column(name = "project_name")
	private String projectName;
	
	@Column(name = "cmp_id")
	//@Transient
	private Integer companyId;
	
	
	//@ManyToOne(fetch = FetchType.LAZY)
	//@JoinColumn(name="cmp_id"/*, insertable = false, updatable = false*/)
	//@Where(clause=" is_active = 1 ")
	@Transient
	//private Company projectCompany;
	
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

	



	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public String getProjectName() {
		return projectName;
	}



	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}



	
	
	

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}



	@Override
	public String toString() {
		return "Project [id=" + id + ", projectName=" + projectName + ", companyId=" + companyId + ", isActive="
				+ isActive + "]";
	}



	
	
}
