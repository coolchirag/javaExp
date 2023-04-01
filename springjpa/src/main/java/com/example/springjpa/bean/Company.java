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
import javax.persistence.Transient;

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
	
	@OneToMany(mappedBy = "compnayToEmpMap", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	//@Fetch(FetchMode.JOIN)
	@Where(clause = "is_active = 1")
	private List<Employee> emp;
	
	@OneToMany(mappedBy = "projectCompany", fetch = FetchType.LAZY)
	//@Fetch(FetchMode.JOIN)
	@Where(clause = "is_active = 1")
	private List<Project> cmpProject;
	
	@Column(name = "is_active")
	private Boolean isActive;
	
	/*
	 * public Company(C List<Employee> emp, List<Project> cmpProject) { super();
	 * this.emp = emp; this.cmpProject = cmpProject; }
	 */

	/*@Column(name = "cmp_column1")
	private String cmpColumn1;
	@Column(name = "cmp_column2")
	private String cmpColumn2;
	@Column(name = "cmp_column3")
	private String cmpColumn3;
	@Column(name = "cmp_column4")
	private String cmpColumn4;
	@Column(name = "cmp_column5")
	private String cmpColumn5;
	@Column(name = "cmp_column6")
	private String cmpColumn6;
	@Column(name = "cmp_column7")
	private String cmpColumn7;
	@Column(name = "cmp_column8")
	private String cmpColumn8;
	@Column(name = "cmp_column9")
	private String cmpColumn9;
	@Column(name = "cmp_column10")
	private String cmpColumn10;
	
	@Column(name = "cmp_column11")
	private String cmpColumn11;
	@Column(name = "cmp_column12")
	private String cmpColumn12;
	@Column(name = "cmp_column13")
	private String cmpColumn13;
	@Column(name = "cmp_column14")
	private String cmpColumn14;
	@Column(name = "cmp_column15")
	private String cmpColumn15;
	@Column(name = "cmp_column16")
	private String cmpColumn16;
	@Column(name = "cmp_column17")
	private String cmpColumn17;
	@Column(name = "cmp_column18")
	private String cmpColumn18;
	@Column(name = "cmp_column19")
	private String cmpColumn19;
	@Column(name = "cmp_column20")
	private String cmpColumn20;
	
	@Column(name = "cmp_column21")
	private String cmpColumn21;
	@Column(name = "cmp_column22")
	private String cmpColumn22;
	@Column(name = "cmp_column23")
	private String cmpColumn23;
	@Column(name = "cmp_column24")
	private String cmpColumn24;
	@Column(name = "cmp_column25")
	private String cmpColumn25;
	@Column(name = "cmp_column26")
	private String cmpColumn26;
	@Column(name = "cmp_column27")
	private String cmpColumn27;
	@Column(name = "cmp_column28")
	private String cmpColumn28;
	@Column(name = "cmp_column29")
	private String cmpColumn29;
	@Column(name = "cmp_column30")
	private String cmpColumn30;
	
	@Column(name = "cmp_column31")
	private String cmpColumn31;
	@Column(name = "cmp_column32")
	private String cmpColumn32;
	@Column(name = "cmp_column33")
	private String cmpColumn33;
	@Column(name = "cmp_column34")
	private String cmpColumn34;
	@Column(name = "cmp_column35")
	private String cmpColumn35;
	@Column(name = "cmp_column36")
	private String cmpColumn36;
	@Column(name = "cmp_column37")
	private String cmpColumn37;
	@Column(name = "cmp_column38")
	private String cmpColumn38;
	@Column(name = "cmp_column39")
	private String cmpColumn39;
	@Column(name = "cmp_column40")
	private String cmpColumn40;
	
	@Column(name = "cmp_column41")
	private String cmpColumn41;
	@Column(name = "cmp_column42")
	private String cmpColumn42;
	@Column(name = "cmp_column43")
	private String cmpColumn43;
	@Column(name = "cmp_column44")
	private String cmpColumn44;
	@Column(name = "cmp_column45")
	private String cmpColumn45;
	@Column(name = "cmp_column46")
	private String cmpColumn46;
	@Column(name = "cmp_column47")
	private String cmpColumn47;
	@Column(name = "cmp_column48")
	private String cmpColumn48;
	@Column(name = "cmp_column49")
	private String cmpColumn49;
	@Column(name = "cmp_column50")
	private String cmpColumn50;*/
	
	
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
		emp.stream().forEach(e -> e.setCompnayToEmpMap(this));
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


	public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public List<Project> getCmpProject() {
		return cmpProject;
	}

	public void setCmpProject(List<Project> cmpProject) {
		this.cmpProject = cmpProject;
	}

	/*public String getCmpColumn1() {
		return cmpColumn1;
	}

	public void setCmpColumn1(String cmpColumn1) {
		this.cmpColumn1 = cmpColumn1;
	}

	public String getCmpColumn2() {
		return cmpColumn2;
	}

	public void setCmpColumn2(String cmpColumn2) {
		this.cmpColumn2 = cmpColumn2;
	}

	public String getCmpColumn3() {
		return cmpColumn3;
	}

	public void setCmpColumn3(String cmpColumn3) {
		this.cmpColumn3 = cmpColumn3;
	}

	public String getCmpColumn4() {
		return cmpColumn4;
	}

	public void setCmpColumn4(String cmpColumn4) {
		this.cmpColumn4 = cmpColumn4;
	}

	public String getCmpColumn5() {
		return cmpColumn5;
	}

	public void setCmpColumn5(String cmpColumn5) {
		this.cmpColumn5 = cmpColumn5;
	}

	public String getCmpColumn6() {
		return cmpColumn6;
	}

	public void setCmpColumn6(String cmpColumn6) {
		this.cmpColumn6 = cmpColumn6;
	}

	public String getCmpColumn7() {
		return cmpColumn7;
	}

	public void setCmpColumn7(String cmpColumn7) {
		this.cmpColumn7 = cmpColumn7;
	}

	public String getCmpColumn8() {
		return cmpColumn8;
	}

	public void setCmpColumn8(String cmpColumn8) {
		this.cmpColumn8 = cmpColumn8;
	}

	public String getCmpColumn9() {
		return cmpColumn9;
	}

	public void setCmpColumn9(String cmpColumn9) {
		this.cmpColumn9 = cmpColumn9;
	}

	public String getCmpColumn10() {
		return cmpColumn10;
	}

	public void setCmpColumn10(String cmpColumn10) {
		this.cmpColumn10 = cmpColumn10;
	}

	public String getCmpColumn11() {
		return cmpColumn11;
	}

	public void setCmpColumn11(String cmpColumn11) {
		this.cmpColumn11 = cmpColumn11;
	}

	public String getCmpColumn12() {
		return cmpColumn12;
	}

	public void setCmpColumn12(String cmpColumn12) {
		this.cmpColumn12 = cmpColumn12;
	}

	public String getCmpColumn13() {
		return cmpColumn13;
	}

	public void setCmpColumn13(String cmpColumn13) {
		this.cmpColumn13 = cmpColumn13;
	}

	public String getCmpColumn14() {
		return cmpColumn14;
	}

	public void setCmpColumn14(String cmpColumn14) {
		this.cmpColumn14 = cmpColumn14;
	}

	public String getCmpColumn15() {
		return cmpColumn15;
	}

	public void setCmpColumn15(String cmpColumn15) {
		this.cmpColumn15 = cmpColumn15;
	}

	public String getCmpColumn16() {
		return cmpColumn16;
	}

	public void setCmpColumn16(String cmpColumn16) {
		this.cmpColumn16 = cmpColumn16;
	}

	public String getCmpColumn17() {
		return cmpColumn17;
	}

	public void setCmpColumn17(String cmpColumn17) {
		this.cmpColumn17 = cmpColumn17;
	}

	public String getCmpColumn18() {
		return cmpColumn18;
	}

	public void setCmpColumn18(String cmpColumn18) {
		this.cmpColumn18 = cmpColumn18;
	}

	public String getCmpColumn19() {
		return cmpColumn19;
	}

	public void setCmpColumn19(String cmpColumn19) {
		this.cmpColumn19 = cmpColumn19;
	}

	public String getCmpColumn20() {
		return cmpColumn20;
	}

	public void setCmpColumn20(String cmpColumn20) {
		this.cmpColumn20 = cmpColumn20;
	}

	public String getCmpColumn21() {
		return cmpColumn21;
	}

	public void setCmpColumn21(String cmpColumn21) {
		this.cmpColumn21 = cmpColumn21;
	}

	public String getCmpColumn22() {
		return cmpColumn22;
	}

	public void setCmpColumn22(String cmpColumn22) {
		this.cmpColumn22 = cmpColumn22;
	}

	public String getCmpColumn23() {
		return cmpColumn23;
	}

	public void setCmpColumn23(String cmpColumn23) {
		this.cmpColumn23 = cmpColumn23;
	}

	public String getCmpColumn24() {
		return cmpColumn24;
	}

	public void setCmpColumn24(String cmpColumn24) {
		this.cmpColumn24 = cmpColumn24;
	}

	public String getCmpColumn25() {
		return cmpColumn25;
	}

	public void setCmpColumn25(String cmpColumn25) {
		this.cmpColumn25 = cmpColumn25;
	}

	public String getCmpColumn26() {
		return cmpColumn26;
	}

	public void setCmpColumn26(String cmpColumn26) {
		this.cmpColumn26 = cmpColumn26;
	}

	public String getCmpColumn27() {
		return cmpColumn27;
	}

	public void setCmpColumn27(String cmpColumn27) {
		this.cmpColumn27 = cmpColumn27;
	}

	public String getCmpColumn28() {
		return cmpColumn28;
	}

	public void setCmpColumn28(String cmpColumn28) {
		this.cmpColumn28 = cmpColumn28;
	}

	public String getCmpColumn29() {
		return cmpColumn29;
	}

	public void setCmpColumn29(String cmpColumn29) {
		this.cmpColumn29 = cmpColumn29;
	}

	public String getCmpColumn30() {
		return cmpColumn30;
	}

	public void setCmpColumn30(String cmpColumn30) {
		this.cmpColumn30 = cmpColumn30;
	}

	public String getCmpColumn31() {
		return cmpColumn31;
	}

	public void setCmpColumn31(String cmpColumn31) {
		this.cmpColumn31 = cmpColumn31;
	}

	public String getCmpColumn32() {
		return cmpColumn32;
	}

	public void setCmpColumn32(String cmpColumn32) {
		this.cmpColumn32 = cmpColumn32;
	}

	public String getCmpColumn33() {
		return cmpColumn33;
	}

	public void setCmpColumn33(String cmpColumn33) {
		this.cmpColumn33 = cmpColumn33;
	}

	public String getCmpColumn34() {
		return cmpColumn34;
	}

	public void setCmpColumn34(String cmpColumn34) {
		this.cmpColumn34 = cmpColumn34;
	}

	public String getCmpColumn35() {
		return cmpColumn35;
	}

	public void setCmpColumn35(String cmpColumn35) {
		this.cmpColumn35 = cmpColumn35;
	}

	public String getCmpColumn36() {
		return cmpColumn36;
	}

	public void setCmpColumn36(String cmpColumn36) {
		this.cmpColumn36 = cmpColumn36;
	}

	public String getCmpColumn37() {
		return cmpColumn37;
	}

	public void setCmpColumn37(String cmpColumn37) {
		this.cmpColumn37 = cmpColumn37;
	}

	public String getCmpColumn38() {
		return cmpColumn38;
	}

	public void setCmpColumn38(String cmpColumn38) {
		this.cmpColumn38 = cmpColumn38;
	}

	public String getCmpColumn39() {
		return cmpColumn39;
	}

	public void setCmpColumn39(String cmpColumn39) {
		this.cmpColumn39 = cmpColumn39;
	}

	public String getCmpColumn40() {
		return cmpColumn40;
	}

	public void setCmpColumn40(String cmpColumn40) {
		this.cmpColumn40 = cmpColumn40;
	}

	public String getCmpColumn41() {
		return cmpColumn41;
	}

	public void setCmpColumn41(String cmpColumn41) {
		this.cmpColumn41 = cmpColumn41;
	}

	public String getCmpColumn42() {
		return cmpColumn42;
	}

	public void setCmpColumn42(String cmpColumn42) {
		this.cmpColumn42 = cmpColumn42;
	}

	public String getCmpColumn43() {
		return cmpColumn43;
	}

	public void setCmpColumn43(String cmpColumn43) {
		this.cmpColumn43 = cmpColumn43;
	}

	public String getCmpColumn44() {
		return cmpColumn44;
	}

	public void setCmpColumn44(String cmpColumn44) {
		this.cmpColumn44 = cmpColumn44;
	}

	public String getCmpColumn45() {
		return cmpColumn45;
	}

	public void setCmpColumn45(String cmpColumn45) {
		this.cmpColumn45 = cmpColumn45;
	}

	public String getCmpColumn46() {
		return cmpColumn46;
	}

	public void setCmpColumn46(String cmpColumn46) {
		this.cmpColumn46 = cmpColumn46;
	}

	public String getCmpColumn47() {
		return cmpColumn47;
	}

	public void setCmpColumn47(String cmpColumn47) {
		this.cmpColumn47 = cmpColumn47;
	}

	public String getCmpColumn48() {
		return cmpColumn48;
	}

	public void setCmpColumn48(String cmpColumn48) {
		this.cmpColumn48 = cmpColumn48;
	}

	public String getCmpColumn49() {
		return cmpColumn49;
	}

	public void setCmpColumn49(String cmpColumn49) {
		this.cmpColumn49 = cmpColumn49;
	}

	public String getCmpColumn50() {
		return cmpColumn50;
	}

	public void setCmpColumn50(String cmpColumn50) {
		this.cmpColumn50 = cmpColumn50;
	}*/

	@Override
	public String toString() {
		return "Company [id=" + id + ", companyName=" + companyName + ", city=" + city + ", isActive=" + isActive + "]";
	}

	

}
