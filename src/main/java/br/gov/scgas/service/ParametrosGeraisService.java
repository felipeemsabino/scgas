package br.gov.scgas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.apache.tomcat.jni.Thread;
import org.hibernate.HibernateException;

import com.google.gson.Gson;

import br.gov.scgas.dao.NoticiasDao;
import br.gov.scgas.dao.ParametrosGeraisDao;
import br.gov.scgas.entidade.Contato;
import br.gov.scgas.entidade.FiltroUsuarioApp;
import br.gov.scgas.entidade.FirebaseData;
import br.gov.scgas.entidade.FirebaseMensagem;
import br.gov.scgas.entidade.FirebaseNotification;
import br.gov.scgas.entidade.Noticias;
import br.gov.scgas.entidade.ParametrosGerais;
import br.gov.scgas.entidade.SimNao;
import br.gov.scgas.entidade.UsuarioApp;
import br.gov.scgas.util.BaseContantes;
import br.gov.scgas.util.SendEmail;
import br.gov.scgas.util.SendNotificationFirebase;



@Path("/parametrosservice")
public class ParametrosGeraisService {



	@Inject
	private ParametrosGeraisDao<ParametrosGerais, Long>  dao;

	@Inject
	private Gson gson;


	@POST
	@Path("/cadastrarParametrosGerais")
	public Response cadastrarNoticias(@Context HttpServletRequest request,String json) {
		ParametrosGerais param = gson.fromJson(json, ParametrosGerais.class);

		try {
			if(param.getId() == null){
				dao.save(param);
			}else{
				dao.update(param);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return Response.status(500).entity(gson.toJson(BaseContantes.msg500Noticia)).build();
		}

		return Response.status(200).entity(gson.toJson(param)).build();
	}



	/**
	 * @author robertosampaio
	 * @since 05/02/2017
	 * @return Response com json completo do objeto  e codigo do resultado da operacao
	 * Códigos possiveis
	 * 200 (OK, registro encontrado)
	 * 404 (Registro não encontrado)
	 * 500 (Exception lancada por algum motivo)
	 * 
	 **/
	@GET
	@Path("/recuperaParametrosGerais")
	public Response recuperaNoticiaPorId() {
		try{
			ParametrosGerais param = null;

			param = dao.getById(ParametrosGerais.class,new Long(1l));

			if(param == null){
				return Response.status(404).entity(gson.toJson("Parametros não cadastrado")).build();							
			}

			return Response.status(200).entity(gson.toJson(param)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity(BaseContantes.msgErro404).build();																							
		}catch(Exception e){
			return Response.status(500).entity(BaseContantes.msgErro500).build();
		}
	}


	public ParametrosGeraisDao<ParametrosGerais, Long> getDaoParam() {
		return dao;
	}
	public void setDaoParam(ParametrosGeraisDao<ParametrosGerais, Long> daoParam) {
		this.dao = daoParam;
	}















}
