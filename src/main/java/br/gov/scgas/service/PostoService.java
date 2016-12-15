package br.gov.scgas.service;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import com.google.gson.Gson;

import br.gov.scgas.dao.PostoDao;
import br.gov.scgas.entidade.Posto;
import br.gov.scgas.entidade.PrecoGNV;



@Path("/postoservice")
public class PostoService {


	@Inject
	private PostoDao<Posto, Long> dao;

	@Inject
	private Gson gson;

	/**
	 * @author robertosampaio
	 * @since 14/11/2016
	 * @return Response com json completo do objeto  e codigo do resultado da operacao
	 * Códigos possiveis
	 * 200 (OK, registro encontrado)
	 * 404 (Registro não encontrado)
	 * 500 (Exception lancada por algum motivo)
	 * 
	 **/
	@GET
	@Path("/listaPostos/{initialPosition}/{finalPosition}")
	public Response listaPostos(@PathParam("initialPosition") String initialPosition,@PathParam("finalPosition") String finalPosition) {
		try{
			List<Posto> listaPostos= new ArrayList<Posto>();
			listaPostos = dao.listAllPosto(new Long(initialPosition),new Long(finalPosition));
			
			for (Posto posto : listaPostos) {
				for (PrecoGNV prc : posto.getListaPrecosGNV()) {
					prc.setPosto(null);
				}
			}
			String json = gson.toJson(listaPostos);
			dao.closeDao();
			return Response.status(200).entity(json).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Erro ao recuperar Postos.").build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}
	}







}
