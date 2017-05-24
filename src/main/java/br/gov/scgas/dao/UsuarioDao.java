package br.gov.scgas.dao;

import java.io.Serializable;
import java.util.List;

import javax.persistence.Query;

import org.hibernate.HibernateException;

import br.gov.scgas.entidade.FiltroPosto;
import br.gov.scgas.entidade.FiltroUsuarioApp;
import br.gov.scgas.entidade.Posto;
import br.gov.scgas.entidade.UsuarioApp;

public class UsuarioDao<T, ID extends Serializable>  extends GenericDaoImpl<T, ID> {

	@SuppressWarnings("unchecked")
	public T recuperaUsuarioPorFacebook(String idFace) throws HibernateException, Exception  {

		try{
			T propietario =  (T) getEntityManager().createNamedQuery("UsuarioApp.autenticacaoFacebook").
					setParameter("idFacebook",idFace).getSingleResult();
			getEntityManager().close();
			return propietario;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEntityManager().close();
			throw new HibernateException(e);
		}


	}
	@SuppressWarnings("unchecked")
	public T recuperaUsuarioPorGmail(String idGmail) throws HibernateException, Exception {
		try{

			T propietario =  (T) getEntityManager().createNamedQuery("UsuarioApp.autenticacaoGmail").
					setParameter("idGmail",idGmail).getSingleResult();
			getEntityManager().close();
			return propietario;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}


	@SuppressWarnings("unchecked")
	public UsuarioApp  autenticaUsuario(String email,String senha) throws HibernateException, Exception {

		try{
			UsuarioApp usr =  (UsuarioApp) getEntityManager().createNamedQuery("UsuarioApp.autentica").setParameter("email",email).setParameter("senha",senha).getSingleResult();
			getEntityManager().close();
			return usr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}
	@SuppressWarnings("unchecked")
	public UsuarioApp  recuperaUsuarioEmail(String email) throws HibernateException, Exception {

		try{
			UsuarioApp usr =  (UsuarioApp) getEntityManager().createNamedQuery("UsuarioApp.recuperaUsuarioEmail").setParameter("email",email).getResultList().get(0);
			getEntityManager().close();
			return usr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}
	@SuppressWarnings("unchecked")
	public UsuarioApp  recuperaSenhaPorPIN(String email,String pinSenha) throws HibernateException, Exception {

		try{
			UsuarioApp usr =  (UsuarioApp) getEntityManager().createNamedQuery("UsuarioApp.recuperaSenhaPorPIN").setParameter("email",email).
					setParameter("pinSenha",pinSenha).getSingleResult();
			getEntityManager().close();
			return usr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}

	public List<UsuarioApp> listAllUsrFiltro(FiltroUsuarioApp filtro) throws HibernateException,Exception{
		try{
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select obj from UsuarioApp obj where 1 = 1 ");

			if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
				strQuery.append(" and obj.nome like :nome");
			}

			if(filtro.getEmail() != null && !filtro.getEmail().isEmpty()){
				strQuery.append(" and obj.email like :email");
			}

			Query query = getEntityManager().createQuery(strQuery.toString());

			if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
				query.setParameter("nome", "%"+filtro.getNome()+"%");
			}

			if(filtro.getEmail() != null && !filtro.getEmail().isEmpty()){
				query.setParameter("email", "%"+filtro.getEmail()+"%");
			}



			List<UsuarioApp> t = query.setFirstResult(filtro.getInicio()).setMaxResults(filtro.getFim()).
					getResultList();

			return t;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}
	}

	public int countUsrFiltro(FiltroUsuarioApp filtro) throws HibernateException,Exception{
		try{
			StringBuilder strQuery = new StringBuilder();
			strQuery.append("select obj from UsuarioApp obj where 1 = 1 ");

			if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
				strQuery.append(" and obj.nome like :nome");
			}

			if(filtro.getEmail() != null && !filtro.getEmail().isEmpty()){
				strQuery.append(" and obj.email = :email");
			}
			
			strQuery.append(" and (obj.ativo is null or obj.ativo = 'S')  and (obj.excluido is null or obj.excluido = 'N' )");

			Query query = getEntityManager().createQuery(strQuery.toString());

			if(filtro.getNome() != null && !filtro.getNome().isEmpty()){
				query.setParameter("nome", "%"+filtro.getNome()+"%");
			}

			if(filtro.getEmail() != null && !filtro.getEmail().isEmpty()){
				query.setParameter("email", "%"+filtro.getEmail()+"%");
			}
			



			List<UsuarioApp> t = query.getResultList();

			return t.size();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}

}
