package br.gov.scgas.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import org.hibernate.HibernateException;

import br.gov.scgas.entidade.Posto;

public class PostoDao<T, ID extends Serializable>  extends GenericDaoImpl<T, ID> {

	
	
	public List<Posto> listAllPosto(Long initPosition,Long finalPosition) throws HibernateException,Exception{
		try{

			List<Posto> t = getEntityManager().createQuery("select obj from Posto obj ").setFirstResult(initPosition.intValue()).setMaxResults(finalPosition.intValue()).
					getResultList();
			
			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}
	
	@SuppressWarnings("unchecked")
	public BigInteger contaLinhas() throws HibernateException, Exception {
	  StringBuilder	query = new StringBuilder(" select count(*) from tb_posto ");
		
	  BigInteger total = (BigInteger) getEntityManager().createNativeQuery(query.toString())
				
				.getSingleResult();
	 
		
		return total;
	}
	
	

}
