package br.gov.scgas.dao;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.gov.scgas.entidade.FiltroPosto;
import br.gov.scgas.entidade.Posto;

public class PostoDao<T, ID extends Serializable>  extends GenericDaoImpl<T, ID> {



	public List<Posto> listAllPosto(Long initPosition,Long finalPosition) throws HibernateException,Exception{
		try{

			List<Posto> t = getEntityManager().createQuery("select obj from Posto obj where obj.ativo = 'S' ").setFirstResult(initPosition.intValue()).setMaxResults(finalPosition.intValue()).
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

		getEntityManager().close();
		return total;
	}

	public List<Posto> listAllPostoFiltro(FiltroPosto filtro) throws HibernateException,Exception{
		try{
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select obj from Posto obj where 1 = 1 ");

			if(filtro.getNomePosto() != null && !filtro.getNomePosto().isEmpty()){
				strQuery.append(" and obj.nome like :nome");
			}

			if(filtro.getBandeiraPosto() != null && !filtro.getBandeiraPosto().isEmpty()){
				strQuery.append(" and obj.bandeiraPosto.id = :idBandeira");
			}

			if(filtro.getEnderecoPosto() != null && !filtro.getEnderecoPosto().isEmpty()){
				strQuery.append(" and obj.endereco like :endereco");
			}

			Query query = getEntityManager().createQuery(strQuery.toString());

			if(filtro.getNomePosto() != null && !filtro.getNomePosto().isEmpty()){
				query.setParameter("nome", filtro.getNomePosto());
			}

			if(filtro.getBandeiraPosto() != null && !filtro.getBandeiraPosto().isEmpty()){
				query.setParameter("idBandeira", filtro.getBandeiraPosto());
			}

			if(filtro.getEnderecoPosto() != null && !filtro.getEnderecoPosto().isEmpty()){
				query.setParameter("endereco", filtro.getEnderecoPosto());
			}


			List<Posto> t = query.setFirstResult(filtro.getInicio()).setMaxResults(filtro.getFim()).
					getResultList();

			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}
	}
	public int countPostoFiltro(FiltroPosto filtro) throws HibernateException,Exception{
		try{
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select obj from Posto obj where 1 = 1 ");

			if(filtro.getNomePosto() != null && !filtro.getNomePosto().isEmpty()){
				strQuery.append(" and obj.nome like :nome");
			}

			if(filtro.getBandeiraPosto() != null && !filtro.getBandeiraPosto().isEmpty()){
				strQuery.append(" and obj.bandeiraPosto.id = :idBandeira");
			}

			if(filtro.getEnderecoPosto() != null && !filtro.getEnderecoPosto().isEmpty()){
				strQuery.append(" and obj.endereco like :endereco");
			}

			Query query = getEntityManager().createQuery(strQuery.toString());

			if(filtro.getNomePosto() != null && !filtro.getNomePosto().isEmpty()){
				query.setParameter("nome", filtro.getNomePosto());
			}

			if(filtro.getBandeiraPosto() != null && !filtro.getBandeiraPosto().isEmpty()){
				query.setParameter("idBandeira", filtro.getBandeiraPosto());
			}

			if(filtro.getEnderecoPosto() != null && !filtro.getEnderecoPosto().isEmpty()){
				query.setParameter("endereco", filtro.getEnderecoPosto());
			}


			List<Posto> t = query.getResultList();;

			return t.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}




}
