package com.websystique.hibernate;

import com.websystique.hibernate.dao.CompanyDao;
import com.websystique.hibernate.dao.EmpDao;

public class TestApp {

	public static void main(String[] args) {
		
		EmpDao dao = new EmpDao();
		dao.getAllEmpDetails();
		//dao.saveEmp();
		//dao.updateEmp();
		//dao.deleteEmp();
		//dao.getEmployee();
	}
	
	public static void main2(String[] args) {
		CompanyDao companyDao = new CompanyDao();
		companyDao.getCompany();
		//companyDao.updateCompanyEmployee();
		//companyDao.insertComapny();
		//companyDao.mergeCompany();
	}
	
	
	
}
