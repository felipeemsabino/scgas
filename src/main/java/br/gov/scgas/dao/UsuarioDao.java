package br.gov.scgas.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;

import br.gov.scgas.entidade.UsuarioApp;

public class UsuarioDao<T, ID extends Serializable>  extends GenericDaoImpl<T, ID> {

	@SuppressWarnings("unchecked")
	public T recuperaUsuarioPorFacebook(String idFace) throws HibernateException, Exception  {

		try{
			T propietario =  (T) getEntityManager().createNamedQuery("UsuarioApp.autenticacaoFacebook").
					setParameter("idFacebook",idFace).getSingleResult();
			//getEntityManager().close();
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
			//getEntityManager().close();
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
			//getEntityManager().close();
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
			UsuarioApp usr =  (UsuarioApp) getEntityManager().createNamedQuery("UsuarioApp.recuperaUsuarioEmail").setParameter("email",email).getSingleResult();
			//getEntityManager().close();
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
			UsuarioApp usr =  (UsuarioApp) getEntityManager().createNamedQuery("UsuarioApp.recuperaUsuarioEmail").setParameter("email",email).
					setParameter("pinSenha",pinSenha).getSingleResult();
			//getEntityManager().close();
			return usr;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			getEntityManager().close();
			throw new HibernateException(e);
		}

	}

}
