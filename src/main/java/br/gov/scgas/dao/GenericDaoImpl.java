package br.gov.scgas.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.PersistenceUnit;

import org.hibernate.HibernateException;

public class GenericDaoImpl<T, ID extends Serializable>  implements GenericDao<T, ID> {

	private static EntityManagerFactory emf;	
	private  EntityManager entityManager;



	@Override
	public T getById(Class<T> clazz, ID id) throws HibernateException,Exception {
		
		try {
			T t = (T) entityManager.find(clazz, id);
			entityManager.close();
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			entityManager.close();
			throw new HibernateException(e);
		}
		
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<T> listAll(Class<T> clazz) throws HibernateException,Exception{
		try{
			
			List<T> t = entityManager.createQuery(("FROM " + clazz.getName())).getResultList();
			entityManager.close();
			return t;
	} catch (Exception e) {
		// TODO Auto-generated catch block
		entityManager.close();
		throw new HibernateException(e);
	}
		
	}

	@Override
	public void save(T entity) throws HibernateException,Exception{
		try {

			beginTransaction();
			entityManager.persist(entity);
			commitTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			closeConnection();
			throw new HibernateException(e);
		}
	}

	@Override
	public void update(T entity) throws HibernateException,Exception{
		try {
			beginTransaction();
			entityManager.merge(entity);
			commitTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			closeConnection();
			throw new HibernateException(e);
		}
	}

	@Override
	public void delete(T entity) throws HibernateException,Exception{
		try {
			beginTransaction();
			entityManager.remove(entity);
			commitTransaction();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			closeConnection();
			throw new HibernateException(e);
		}
	}

	protected void beginTransaction() throws HibernateException,Exception{
		if(emf == null){
			emf = Persistence.createEntityManagerFactory("orion");			
		}
		entityManager = emf.createEntityManager();
		entityManager.getTransaction().begin();
	}

	protected void commitTransaction() {
		entityManager.flush();
		entityManager.getTransaction().commit();
		
		
	
	}

	public EntityManager getEntityManager() {
		if(entityManager == null){
			if(emf == null){
				emf = Persistence.createEntityManagerFactory("orion");			
			}
			entityManager = emf.createEntityManager();
		}
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	protected void closeConnection() throws HibernateException, Exception {
		
		entityManager.getTransaction().rollback();			
		entityManager.close();
		
	}


}
