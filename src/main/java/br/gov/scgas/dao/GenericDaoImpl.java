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
			T t = (T) getEntityManager().find(clazz, id);
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

			List<T> t = getEntityManager().createQuery(("FROM " + clazz.getName())).getResultList();
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
			getEntityManager().persist(entity);
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
			getEntityManager().merge(entity);
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
			entity = getEntityManager().merge(entity);
			getEntityManager().remove(entity);
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

		entityManager.getTransaction().commit();
		entityManager.close();



	}

	public EntityManager getEntityManager() {
		if(entityManager == null || !entityManager.isOpen()){
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
		//entityManager.flush();
		entityManager.getTransaction().rollback();			
		entityManager.close();

	}
	public void closeDao() throws HibernateException, Exception {
		if(entityManager != null && entityManager.isOpen()){
			entityManager.close();			
		}
		
	}


}
