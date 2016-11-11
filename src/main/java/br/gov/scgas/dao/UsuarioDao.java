package br.gov.scgas.dao;

import java.io.Serializable;

import org.hibernate.HibernateException;

import br.gov.scgas.entidade.UsuarioApp;

public class UsuarioDao<T, ID extends Serializable>  extends GenericDaoImpl<T, ID> {
	
	@SuppressWarnings("unchecked")
	public T recuperaUsuarioPorFacebook(String idFace) throws HibernateException, Exception {
		beginTransaction();
		T propietario =  (T) getEntityManager().createNamedQuery("UsuarioApp.autenticacaoFacebook").
				setParameter("idFacebook",idFace).getSingleResult();
		commitTransaction();
		return propietario;
	}
	@SuppressWarnings("unchecked")
	public T recuperaUsuarioPorGmail(String idGmail) throws HibernateException, Exception {
		beginTransaction();
		T propietario =  (T) getEntityManager().createNamedQuery("UsuarioApp.autenticacaoGmail").
				setParameter("idGmail",idGmail).getSingleResult();
		commitTransaction();
		return propietario;
	}
	
	
	@SuppressWarnings("unchecked")
	public UsuarioApp  autenticaUsuario(String email,String senha) throws HibernateException, Exception {
		beginTransaction();
		UsuarioApp usr =  (UsuarioApp) getEntityManager().createNamedQuery("UsuarioApp.autentica").setParameter("email",email).setParameter("senha",senha).getSingleResult();
		commitTransaction();
		return usr;
	}

}
