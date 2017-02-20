package br.gov.scgas.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.gov.scgas.entidade.FiltroUsuarioApp;
import br.gov.scgas.entidade.Noticias;
import br.gov.scgas.entidade.Posto;
import br.gov.scgas.entidade.UsuarioApp;

public class NoticiasDao<T, ID extends Serializable>  extends GenericDaoImpl<T, ID> {

	
	public List<Noticias> listAllNoticiasFiltro(FiltroUsuarioApp filtro) throws HibernateException,Exception{
		try{
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select obj from Noticias obj order by dataCadastro desc");


			Query query = getEntityManager().createQuery(strQuery.toString());
			List<Noticias> t = query.setFirstResult(filtro.getInicio()).setMaxResults(filtro.getFim()).
					getResultList();

			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}
	}

	public int countNoticiasFiltro(FiltroUsuarioApp filtro) throws HibernateException,Exception{
		try{
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select obj from Noticias obj");

			Query query = getEntityManager().createQuery(strQuery.toString());

			List<Noticias> t = query.getResultList();

			return t.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}


}
