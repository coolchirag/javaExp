package com.chirag.hibernate.entitymanager.examples;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class HibernateUtility {

	private static final EntityManagerFactory emf = Persistence.createEntityManagerFactory("cpersist");
	
	public EntityManagerFactory getEntityManagerFactory() {
		return emf;
	}
	
	public static EntityManager getEntityManager() {
		EntityManager em = emf.createEntityManager();
		em.getTransaction().begin();
		return em;
	}
	
	public static void commit(EntityManager em) {
		System.out.println("inside commit.");
		em.getTransaction().commit();
		System.out.println("Commited.");
	}
}
