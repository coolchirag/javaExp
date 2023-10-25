package com.example.springjpa.bean;

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

import org.hibernate.annotations.Where;

@Entity
@Table(name = "company_hierarchy")
public class CompanyHierarchy {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@Column(name = "p_cmp_id")
	private int pCmpId;
	
	@Column(name = "c_cmp_id")
	private int cCmpId;
	
	
	
	@Transient
	//@OneToOne
	//@JoinColumn(name = "c_cmp_id", insertable = false, updatable = false)
	private Company childCompany;
	


	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getpCmpId() {
		return pCmpId;
	}

	public void setpCmpId(int pCmpId) {
		this.pCmpId = pCmpId;
	}

	public int getcCmpId() {
		return cCmpId;
	}

	public void setcCmpId(int cCmpId) {
		this.cCmpId = cCmpId;
	}

	public Company getChildCompany() {
		return childCompany;
	}

	public void setChildCompany(Company childCompany) {
		this.childCompany = childCompany;
	}

	@Override
	public String toString() {
		return "CompanyHierarchy [id=" + id + ", pCmpId=" + pCmpId + ", cCmpId=" + cCmpId + ", childCompany="
				+ childCompany + "]";
	}

	
	


	
	
}
