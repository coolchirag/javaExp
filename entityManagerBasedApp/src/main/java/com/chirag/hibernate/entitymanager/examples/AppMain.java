package com.chirag.hibernate.entitymanager.examples;

import com.chirag.hibernate.entitymanager.examples.service.CompanyService;

public class AppMain {

    public static void main(String[] args) {
        CompanyService cs = new CompanyService();
        cs.getCompany();
    }

}
