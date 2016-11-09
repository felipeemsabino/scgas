package br.gov.scgas.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;

import org.hibernate.HibernateException;

public class GenericDaoImpl<T, ID extends Serializable>  implements GenericDao<T, ID> {

	@PersistenceUnit(unitName="orion")
	private  EntityManagerFactory emf;
	
	private  EntityManager entityManager;

	@Override
	public T getById(Class<T> clazz, ID id) throws HibernateException,Exception {
		beginTransaction();
		T t = (T) entityManager.find(clazz, id);
		commitTransaction();
		return t;
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll(Class<T> clazz) throws HibernateException,Exception{
		beginTransaction();
		List<T> t = entityManager.createQuery(("FROM " + clazz.getName())).getResultList();
		commitTransaction();
		return t;
	}

	@Override
	public void save(T entity) throws HibernateException,Exception{
		beginTransaction();
		entityManager.persist(entity);
		commitTransaction();
	}

	@Override
	public void update(T entity) throws HibernateException,Exception{
		beginTransaction();
		entityManager.merge(entity);
		commitTransaction();
	}

	@Override
	public void delete(T entity) throws HibernateException,Exception{
		beginTransaction();
		entityManager.remove(entity);
		commitTransaction();
	}

	private void beginTransaction() throws HibernateException,Exception{
		entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
	}

	private void commitTransaction() {
		entityManager.getTransaction().commit();
		entityManager.close();
	}


}
