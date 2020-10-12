package com.websystique.hibernate;

import java.io.Serializable;
import java.util.Iterator;

import org.hibernate.CallbackException;
import org.hibernate.EmptyInterceptor;
import org.hibernate.EntityMode;
import org.hibernate.Interceptor;
import org.hibernate.Transaction;
import org.hibernate.type.Type;

public class TestInterSeptor extends EmptyInterceptor {

	@Override
	public boolean onLoad(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		System.out.println("onLoad");
		return super.onLoad(entity, id, state, propertyNames, types);
	}

	@Override
	public boolean onFlushDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) throws CallbackException {
		System.out.println("onFLush");
		return super.onFlushDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public boolean onSave(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		System.out.println("OnSave");
		return super.onSave(entity, id, state, propertyNames, types);
	}

	@Override
	public void onDelete(Object entity, Serializable id, Object[] state, String[] propertyNames, Type[] types)
			throws CallbackException {
		System.out.println("delete");
		
	}

	@Override
	public void onCollectionRecreate(Object collection, Serializable key) throws CallbackException {
		System.out.println("onCollectionRecreate");
		
	}

	@Override
	public void onCollectionRemove(Object collection, Serializable key) throws CallbackException {
		System.out.println("onCollectionRemove");
		
	}

	@Override
	public void onCollectionUpdate(Object collection, Serializable key) throws CallbackException {
		System.out.println("onCollectionUpdate");
		
	}

	@Override
	public void preFlush(Iterator entities) throws CallbackException {
		System.out.println("preFlush");
		
	}

	@Override
	public void postFlush(Iterator entities) throws CallbackException {
		System.out.println("postFLush");
		
	}

	@Override
	public Boolean isTransient(Object entity) {
		System.out.println("isTransient");
		return super.isTransient(entity);
	}

	@Override
	public int[] findDirty(Object entity, Serializable id, Object[] currentState, Object[] previousState,
			String[] propertyNames, Type[] types) {
		System.out.println("findDirty");
		return super.findDirty(entity, id, currentState, previousState, propertyNames, types);
	}

	@Override
	public Object instantiate(String entityName, EntityMode entityMode, Serializable id) throws CallbackException {
		System.out.println("instantiate");
		return super.instantiate(entityName, entityMode, id);
	}

	@Override
	public String getEntityName(Object object) throws CallbackException {
		System.out.println("getEntityName");
		return super.getEntityName(object);
	}

	@Override
	public Object getEntity(String entityName, Serializable id) throws CallbackException {
		System.out.println("getENtity");
		return super.getEntity(entityName, id);
	}

	@Override
	public void afterTransactionBegin(Transaction tx) {
		System.out.println("afterTransactionBegin");
		
	}

	@Override
	public void beforeTransactionCompletion(Transaction tx) {
		System.out.println("beforeTransactionCompletion");
		
	}

	@Override
	public void afterTransactionCompletion(Transaction tx) {
		System.out.println("afterTransactionCompletion");
		
	}

	@Override
	public String onPrepareStatement(String sql) {
		System.out.println("onPrepareStatement : "+sql);
		return super.onPrepareStatement(sql);
	}

}
