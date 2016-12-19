package br.gov.scgas.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
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
	 * @throws Exception 
	 * @throws HibernateException 
	 * 
	 **/
	@GET
	@Path("/listaPostos/{initialPosition}/{finalPosition}")
	public Response listaPostos(@PathParam("initialPosition") String initialPosition,@PathParam("finalPosition") String finalPosition) throws HibernateException, Exception {
		try{
			List listaPostos= new ArrayList();
			listaPostos = dao.listAllPosto(new Long(initialPosition),new Long(finalPosition));
			
			for (Posto posto : (List<Posto>)listaPostos) {
				for (PrecoGNV prc : posto.getListaPrecosGNV()) {
					prc.setPosto(null);
					long diferencaHoras = ( new Date().getTime() - prc.getDataHoraCadastro().getTime() ) / (1000*60*60);
				    long diferencaDias = (  new Date().getTime() - prc.getDataHoraCadastro().getTime()) / (1000*60*60*24);
				    
				    if(diferencaHoras >= 24){
				    	prc.setTempoUltimaAtulizacao("Atualizado a "+ diferencaDias + " dia(s) atrás");
				    }else{
				    	prc.setTempoUltimaAtulizacao("Atualizado a "+ diferencaHoras + " hora(s) atrás");			    	
				    }
				}
				
			}
			
			BigInteger contador = dao.contaLinhas();
			
			if(contador.longValue() <= new Long(finalPosition) ){
				listaPostos.add("{hasMore:"+0+"}");
			}else{
				listaPostos.add("{hasMore:"+1+"}");				
			}
			
			String json = gson.toJson(listaPostos);
			
			dao.closeDao();
			return Response.status(200).entity(json).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Erro ao recuperar Postos.").build();																							
		}catch(Exception e){
			dao.closeDao();
			return Response.status(500).entity(null).build();
		}
	}







}
