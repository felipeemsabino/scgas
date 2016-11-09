package br.gov.scgas.dao;

import java.util.List;

import org.hibernate.HibernateException;

public interface GenericDao<T, ID> {
	
	public T getById(Class<T> clazz, ID id) throws HibernateException,Exception;

	public List<T> listAll(Class<T> clazz) throws HibernateException,Exception;

	public void save(T entity) throws HibernateException,Exception;

	public void update(T entity) throws HibernateException,Exception;

	public void delete(T entity) throws HibernateException,Exception;


}
