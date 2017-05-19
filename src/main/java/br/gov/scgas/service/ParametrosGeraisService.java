package br.gov.scgas.service;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import com.google.gson.Gson;

import br.gov.scgas.annotation.Seguranca;
import br.gov.scgas.dao.ParametrosGeraisDao;
import br.gov.scgas.entidade.ParametrosGerais;
import br.gov.scgas.util.BaseContantes;


@Seguranca
@Path("/parametrosservice")
public class ParametrosGeraisService {



	@Inject
	private ParametrosGeraisDao<ParametrosGerais, Long>  dao;

	@Inject
	private Gson gson;


	
	@POST
	@Path("/cadastrarParametrosGerais")
	public Response cadastrarNoticias(@Context HttpHeaders headers,@Context HttpServletRequest request,String json) {
		ParametrosGerais param = gson.fromJson(json, ParametrosGerais.class);

		try {
			if(param.getValorMaxGNV() == null || param.getValorMaxGNV().compareTo(new BigDecimal(0.000)) == -1 ){
				return Response.status(500).entity(gson.toJson(BaseContantes.msgValorMaior0)).build();
			}
			
			if(param.getValorMinGnv() == null || param.getValorMinGnv().compareTo(new BigDecimal(0.000)) == -1 ){
				return Response.status(500).entity(gson.toJson(BaseContantes.msgValorMaior0)).build();
			}
			dao.update(param);
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
	public Response recuperaNoticiaPorId(@Context HttpHeaders headers) {
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
