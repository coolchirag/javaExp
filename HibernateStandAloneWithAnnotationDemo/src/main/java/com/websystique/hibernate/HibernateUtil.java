package com.websystique.hibernate;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.AnnotationConfiguration;

@SuppressWarnings("deprecation")
public class HibernateUtil {
	
	private static final SessionFactory sessionFactory;
	
	static{
		try{
			sessionFactory = new AnnotationConfiguration().configure().addPackage("com.websystique.hibernate.model").buildSessionFactory();

		}catch (Throwable ex) {
			System.err.println("Session Factory could not be created." + ex);
			throw new ExceptionInInitializerError(ex);
		}	
	}
	
	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}
	
	public static Session getSession()
	{
		//Session session = sessionFactory.openSession();
		Session session = sessionFactory.withOptions().interceptor(new TestInterSeptor()).openSession();
		session.beginTransaction();
		return session;
	}
	
	public static void commit(Session session) 
	{
		System.out.println("inside commit");
		session.getTransaction().commit();
		System.out.println("session commited");
		session.close();
		System.out.println("session closed");
	}
}
