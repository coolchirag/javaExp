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

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.SQLDelete;
import org.hibernate.annotations.Where;

@Entity
@Table(name = "employee")
//@Where(clause = " is_active = 1 ")
@SQLDelete(sql = "update employee set is_active = null where id = ? ")
public class Employee {

	@Id
	//@GeneratedValue(strategy = GenerationType.IDENTITY)
	@GeneratedValue(generator = "abc")
	@GenericGenerator(name = "abc", strategy = "increment")
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
	private List<Project> empProject;
	
	@Column(name = "is_active")
	private Integer isActive;
	
	@Column(name = "emp_column1")
	private String empColumn1;
	@Column(name = "emp_column2")
	private String empColumn2;
	@Column(name = "emp_column3")
	private String empColumn3;
	@Column(name = "emp_column4")
	private String empColumn4;
	@Column(name = "emp_column5")
	private String empColumn5;
	@Column(name = "emp_column6")
	private String empColumn6;
	@Column(name = "emp_column7")
	private String empColumn7;
	@Column(name = "emp_column8")
	private String empColumn8;
	@Column(name = "emp_column9")
	private String empColumn9;
	@Column(name = "emp_column10")
	private String empColumn10;
	
	@Column(name = "emp_column11")
	private String empColumn11;
	@Column(name = "emp_column12")
	private String empColumn12;
	@Column(name = "emp_column13")
	private String empColumn13;
	@Column(name = "emp_column14")
	private String empColumn14;
	@Column(name = "emp_column15")
	private String empColumn15;
	@Column(name = "emp_column16")
	private String empColumn16;
	@Column(name = "emp_column17")
	private String empColumn17;
	@Column(name = "emp_column18")
	private String empColumn18;
	@Column(name = "emp_column19")
	private String empColumn19;
	@Column(name = "emp_column20")
	private String empColumn20;
	
	@Column(name = "emp_column21")
	private String empColumn21;
	@Column(name = "emp_column22")
	private String empColumn22;
	@Column(name = "emp_column23")
	private String empColumn23;
	@Column(name = "emp_column24")
	private String empColumn24;
	@Column(name = "emp_column25")
	private String empColumn25;
	@Column(name = "emp_column26")
	private String empColumn26;
	@Column(name = "emp_column27")
	private String empColumn27;
	@Column(name = "emp_column28")
	private String empColumn28;
	@Column(name = "emp_column29")
	private String empColumn29;
	@Column(name = "emp_column30")
	private String empColumn30;
	
	@Column(name = "emp_column31")
	private String empColumn31;
	@Column(name = "emp_column32")
	private String empColumn32;
	@Column(name = "emp_column33")
	private String empColumn33;
	@Column(name = "emp_column34")
	private String empColumn34;
	@Column(name = "emp_column35")
	private String empColumn35;
	@Column(name = "emp_column36")
	private String empColumn36;
	@Column(name = "emp_column37")
	private String empColumn37;
	@Column(name = "emp_column38")
	private String empColumn38;
	@Column(name = "emp_column39")
	private String empColumn39;
	@Column(name = "emp_column40")
	private String empColumn40;
	
	@Column(name = "emp_column41")
	private String empColumn41;
	@Column(name = "emp_column42")
	private String empColumn42;
	@Column(name = "emp_column43")
	private String empColumn43;
	@Column(name = "emp_column44")
	private String empColumn44;
	@Column(name = "emp_column45")
	private String empColumn45;
	@Column(name = "emp_column46")
	private String empColumn46;
	@Column(name = "emp_column47")
	private String empColumn47;
	@Column(name = "emp_column48")
	private String empColumn48;
	@Column(name = "emp_column49")
	private String empColumn49;
	@Column(name = "emp_column50")
	private String empColumn50;
	
	
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



	public List<Project> getEmpProject() {
		return empProject;
	}



	public void setEmpProject(List<Project> empProject) {
		this.empProject = empProject;
	}



	public String getEmpColumn1() {
		return empColumn1;
	}



	public void setEmpColumn1(String empColumn1) {
		this.empColumn1 = empColumn1;
	}



	public String getEmpColumn2() {
		return empColumn2;
	}



	public void setEmpColumn2(String empColumn2) {
		this.empColumn2 = empColumn2;
	}



	public String getEmpColumn3() {
		return empColumn3;
	}



	public void setEmpColumn3(String empColumn3) {
		this.empColumn3 = empColumn3;
	}



	public String getEmpColumn4() {
		return empColumn4;
	}



	public void setEmpColumn4(String empColumn4) {
		this.empColumn4 = empColumn4;
	}



	public String getEmpColumn5() {
		return empColumn5;
	}



	public void setEmpColumn5(String empColumn5) {
		this.empColumn5 = empColumn5;
	}



	public String getEmpColumn6() {
		return empColumn6;
	}



	public void setEmpColumn6(String empColumn6) {
		this.empColumn6 = empColumn6;
	}



	public String getEmpColumn7() {
		return empColumn7;
	}



	public void setEmpColumn7(String empColumn7) {
		this.empColumn7 = empColumn7;
	}



	public String getEmpColumn8() {
		return empColumn8;
	}



	public void setEmpColumn8(String empColumn8) {
		this.empColumn8 = empColumn8;
	}



	public String getEmpColumn9() {
		return empColumn9;
	}



	public void setEmpColumn9(String empColumn9) {
		this.empColumn9 = empColumn9;
	}



	public String getEmpColumn10() {
		return empColumn10;
	}



	public void setEmpColumn10(String empColumn10) {
		this.empColumn10 = empColumn10;
	}



	public String getEmpColumn11() {
		return empColumn11;
	}



	public void setEmpColumn11(String empColumn11) {
		this.empColumn11 = empColumn11;
	}



	public String getEmpColumn12() {
		return empColumn12;
	}



	public void setEmpColumn12(String empColumn12) {
		this.empColumn12 = empColumn12;
	}



	public String getEmpColumn13() {
		return empColumn13;
	}



	public void setEmpColumn13(String empColumn13) {
		this.empColumn13 = empColumn13;
	}



	public String getEmpColumn14() {
		return empColumn14;
	}



	public void setEmpColumn14(String empColumn14) {
		this.empColumn14 = empColumn14;
	}



	public String getEmpColumn15() {
		return empColumn15;
	}



	public void setEmpColumn15(String empColumn15) {
		this.empColumn15 = empColumn15;
	}



	public String getEmpColumn16() {
		return empColumn16;
	}



	public void setEmpColumn16(String empColumn16) {
		this.empColumn16 = empColumn16;
	}



	public String getEmpColumn17() {
		return empColumn17;
	}



	public void setEmpColumn17(String empColumn17) {
		this.empColumn17 = empColumn17;
	}



	public String getEmpColumn18() {
		return empColumn18;
	}



	public void setEmpColumn18(String empColumn18) {
		this.empColumn18 = empColumn18;
	}



	public String getEmpColumn19() {
		return empColumn19;
	}



	public void setEmpColumn19(String empColumn19) {
		this.empColumn19 = empColumn19;
	}



	public String getEmpColumn20() {
		return empColumn20;
	}



	public void setEmpColumn20(String empColumn20) {
		this.empColumn20 = empColumn20;
	}



	public String getEmpColumn21() {
		return empColumn21;
	}



	public void setEmpColumn21(String empColumn21) {
		this.empColumn21 = empColumn21;
	}



	public String getEmpColumn22() {
		return empColumn22;
	}



	public void setEmpColumn22(String empColumn22) {
		this.empColumn22 = empColumn22;
	}



	public String getEmpColumn23() {
		return empColumn23;
	}



	public void setEmpColumn23(String empColumn23) {
		this.empColumn23 = empColumn23;
	}



	public String getEmpColumn24() {
		return empColumn24;
	}



	public void setEmpColumn24(String empColumn24) {
		this.empColumn24 = empColumn24;
	}



	public String getEmpColumn25() {
		return empColumn25;
	}



	public void setEmpColumn25(String empColumn25) {
		this.empColumn25 = empColumn25;
	}



	public String getEmpColumn26() {
		return empColumn26;
	}



	public void setEmpColumn26(String empColumn26) {
		this.empColumn26 = empColumn26;
	}



	public String getEmpColumn27() {
		return empColumn27;
	}



	public void setEmpColumn27(String empColumn27) {
		this.empColumn27 = empColumn27;
	}



	public String getEmpColumn28() {
		return empColumn28;
	}



	public void setEmpColumn28(String empColumn28) {
		this.empColumn28 = empColumn28;
	}



	public String getEmpColumn29() {
		return empColumn29;
	}



	public void setEmpColumn29(String empColumn29) {
		this.empColumn29 = empColumn29;
	}



	public String getEmpColumn30() {
		return empColumn30;
	}



	public void setEmpColumn30(String empColumn30) {
		this.empColumn30 = empColumn30;
	}



	public String getEmpColumn31() {
		return empColumn31;
	}



	public void setEmpColumn31(String empColumn31) {
		this.empColumn31 = empColumn31;
	}



	public String getEmpColumn32() {
		return empColumn32;
	}



	public void setEmpColumn32(String empColumn32) {
		this.empColumn32 = empColumn32;
	}



	public String getEmpColumn33() {
		return empColumn33;
	}



	public void setEmpColumn33(String empColumn33) {
		this.empColumn33 = empColumn33;
	}



	public String getEmpColumn34() {
		return empColumn34;
	}



	public void setEmpColumn34(String empColumn34) {
		this.empColumn34 = empColumn34;
	}



	public String getEmpColumn35() {
		return empColumn35;
	}



	public void setEmpColumn35(String empColumn35) {
		this.empColumn35 = empColumn35;
	}



	public String getEmpColumn36() {
		return empColumn36;
	}



	public void setEmpColumn36(String empColumn36) {
		this.empColumn36 = empColumn36;
	}



	public String getEmpColumn37() {
		return empColumn37;
	}



	public void setEmpColumn37(String empColumn37) {
		this.empColumn37 = empColumn37;
	}



	public String getEmpColumn38() {
		return empColumn38;
	}



	public void setEmpColumn38(String empColumn38) {
		this.empColumn38 = empColumn38;
	}



	public String getEmpColumn39() {
		return empColumn39;
	}



	public void setEmpColumn39(String empColumn39) {
		this.empColumn39 = empColumn39;
	}



	public String getEmpColumn40() {
		return empColumn40;
	}



	public void setEmpColumn40(String empColumn40) {
		this.empColumn40 = empColumn40;
	}



	public String getEmpColumn41() {
		return empColumn41;
	}



	public void setEmpColumn41(String empColumn41) {
		this.empColumn41 = empColumn41;
	}



	public String getEmpColumn42() {
		return empColumn42;
	}



	public void setEmpColumn42(String empColumn42) {
		this.empColumn42 = empColumn42;
	}



	public String getEmpColumn43() {
		return empColumn43;
	}



	public void setEmpColumn43(String empColumn43) {
		this.empColumn43 = empColumn43;
	}



	public String getEmpColumn44() {
		return empColumn44;
	}



	public void setEmpColumn44(String empColumn44) {
		this.empColumn44 = empColumn44;
	}



	public String getEmpColumn45() {
		return empColumn45;
	}



	public void setEmpColumn45(String empColumn45) {
		this.empColumn45 = empColumn45;
	}



	public String getEmpColumn46() {
		return empColumn46;
	}



	public void setEmpColumn46(String empColumn46) {
		this.empColumn46 = empColumn46;
	}



	public String getEmpColumn47() {
		return empColumn47;
	}



	public void setEmpColumn47(String empColumn47) {
		this.empColumn47 = empColumn47;
	}



	public String getEmpColumn48() {
		return empColumn48;
	}



	public void setEmpColumn48(String empColumn48) {
		this.empColumn48 = empColumn48;
	}



	public String getEmpColumn49() {
		return empColumn49;
	}



	public void setEmpColumn49(String empColumn49) {
		this.empColumn49 = empColumn49;
	}



	public String getEmpColumn50() {
		return empColumn50;
	}



	public void setEmpColumn50(String empColumn50) {
		this.empColumn50 = empColumn50;
	}



	@Override
	public String toString() {
		return "Employee [id=" + id + ", employeeName=" + employeeName + ", salary=" + salary + ", companyId="
				+ companyId + ", isActive=" + isActive + "]";
	}



}
