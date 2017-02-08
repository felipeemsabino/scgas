package br.gov.scgas.service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import com.google.gson.Gson;

import br.gov.scgas.dao.NoticiasDao;
import br.gov.scgas.entidade.Noticias;
import br.gov.scgas.entidade.UsuarioApp;



@Path("/noticiasservice")
public class NoticiasService {


	@Inject
	private NoticiasDao<Noticias, Long> dao;
	
	@Inject
	private Gson gson;


	@POST
	@Path("/cadastrarNoticias")
	public Response cadastrarNoticias(@Context HttpServletRequest request,String json) {
		Noticias noticias = gson.fromJson(json, Noticias.class);
		noticias.setDataCadastro(new Date());
		try {
			if(noticias.getId() == null){
				dao.save(noticias);
			}else{
				dao.update(noticias);
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return Response.status(500).entity(gson.toJson("Erro ao criar notícia!")).build();
		}
		
		return Response.status(200).entity(gson.toJson(noticias)).build();
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
	@Path("/listaTodasNoticias")
	public Response listaTodasNoticias() {
		try{
			List<Noticias> listaNoticias = new ArrayList<Noticias>();
			listaNoticias = dao.listAll(Noticias.class);
			
			return Response.status(200).entity(gson.toJson(listaNoticias)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Registro não encontrado.").build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}
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
	@Path("/recuperaNoticiaPorId/{id}")
	public Response recuperaNoticiaPorId(@PathParam("id") String idParam) {
		try{
			Noticias noticia = null;
			if(idParam != null){
				noticia = dao.getById(Noticias.class,new Long(idParam));
			}
			
			if(noticia == null){
				return Response.status(404).entity(gson.toJson("Notícia não cadastrado")).build();							
			}
			
			return Response.status(200).entity(gson.toJson(noticia)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Notícia não cadastrado").build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}
	}

	
	
	








}
