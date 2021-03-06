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
import br.gov.scgas.entidade.Contato;
import br.gov.scgas.entidade.FiltroUsuarioApp;
import br.gov.scgas.entidade.FirebaseData;
import br.gov.scgas.entidade.FirebaseMensagem;
import br.gov.scgas.entidade.FirebaseNotification;
import br.gov.scgas.entidade.Noticias;
import br.gov.scgas.entidade.SimNao;
import br.gov.scgas.entidade.UsuarioApp;
import br.gov.scgas.util.BaseContantes;
import br.gov.scgas.util.SendEmail;
import br.gov.scgas.util.SendNotificationFirebase;



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
			if(noticias.getNotifica().equals(SimNao.S)){
				Runnable runnable = new Runnable() {
					@Override
					public void run() {
						// TODO Auto-generated method stub
						sendNotification(noticias);
					}
				};
				new java.lang.Thread(runnable).start();
				
			}
			
		} catch (Exception e) {
			// TODO Auto-generated catch block
			
			e.printStackTrace();
			return Response.status(500).entity(gson.toJson(BaseContantes.msg500Noticia)).build();
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
	 * @throws Exception 
	 * @throws HibernateException 
	 * 
	 **/
	@GET
	@Path("/listaTodasNoticias")
	public Response listaTodasNoticias(@QueryParam("inicio") String inicio,
			@QueryParam("fim") String fim) throws HibernateException, Exception {
		try{
			FiltroUsuarioApp filtro = new FiltroUsuarioApp();
			filtro.setInicio(new Integer(inicio));
			filtro.setFim(new Integer(fim));
			List listaNoticias = new ArrayList();
			int contador = dao.countNoticiasFiltro(filtro);
			listaNoticias.addAll(dao.listAllNoticiasFiltro(filtro));
			listaNoticias.add("{numRows:"+contador+"}");
			dao.closeDao();
			return Response.status(200).entity(gson.toJson(listaNoticias)).build();			
		}catch(HibernateException e){
			dao.closeDao();
			return Response.status(404).entity(BaseContantes.msgErro404).build();																							
		}catch(Exception e){
			dao.closeDao();
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
	 * @throws Exception 
	 * @throws HibernateException 
	 * 
	 **/
	@GET
	@Path("/listaTodasNoticiasApp/{inicio}/{fim}")
	public Response listaTodasNoticiasApp(@PathParam("inicio") String inicio,
			@PathParam("fim") String fim) throws HibernateException, Exception {
		try{
			FiltroUsuarioApp filtro = new FiltroUsuarioApp();
			filtro.setInicio(new Integer(inicio));
			filtro.setFim(new Integer(fim));
			List listaNoticias = new ArrayList();
			//int contador = dao.countNoticiasFiltro(filtro);
			//listaNoticias.add("{numRows:"+contador+"}");
			listaNoticias.addAll(dao.listAllNoticiasFiltro(filtro));
			dao.closeDao();
			return Response.status(200).entity(gson.toJson(listaNoticias)).build();			
		}catch(HibernateException e){
			dao.closeDao();
			return Response.status(404).entity(BaseContantes.msgErro404).build();																							
		}catch(Exception e){
			dao.closeDao();
			return Response.status(500).entity(BaseContantes.msgErro500).build();
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
			return Response.status(404).entity(BaseContantes.msgErro404).build();																							
		}catch(Exception e){
			return Response.status(500).entity(BaseContantes.msgErro500).build();
		}
	}
	
	public String sendNotification(Noticias noticia) {
		Gson gson = new Gson();
		FirebaseMensagem fbmensagem = new FirebaseMensagem();
		FirebaseData data = new FirebaseData();
		data.setDescripcion(BaseContantes.lblDica);
		data.setTitulo(BaseContantes.appName);
		
		
		FirebaseNotification notification = new FirebaseNotification();
		notification.setTitle(BaseContantes.appName);
		//notification.setIcon("logo_home");
		fbmensagem.setTo(BaseContantes.topicNotification);	
		notification.setBody(noticia.getTitulo());
		//data.setIdUsuarioDestinatario(chat.getUsuarioApp().getId().toString());
		fbmensagem.setData(data);
		fbmensagem.setNotification(notification);

		String conteudo = gson.toJson(fbmensagem);
		// envia push
		try {
			SendNotificationFirebase.enviaFirebase(conteudo);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return conteudo;
	}
	
	/**public static void main(String [] args){
		NoticiasService noti=new NoticiasService();
		noti.sendNotification(new Noticias());
	}*/
	
	
	
	/**
	 * @author robertosampaio
	 * @since 11/11/2016
	 * @param ID do  Facebook
	 * @return Response com json completo do objeto  e codigo do resultado da operacao
	 * Códigos possiveis
	 * 200 (OK, registro encontrado)
	 * 404 (Registro não encontrado)
	 * 500 (Exception lancada por algum motivo)
	 * 
	 **/
	@POST
	@Path("/contato")
	public Response sendContact(@Context HttpServletRequest request,String json) {
		try{
			Contato contato = gson.fromJson(json, Contato.class);
			//Contato contato = new Contato();
			//contato.setTextoContato("Contato");
			//contato.setTitulo("Titulo Contato");
			SendEmail sendEmail=new SendEmail();
			sendEmail.sendContact(contato);
			return Response.status(200).entity(BaseContantes.msgEmailEnviado).build();			
																								
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity(e.getMessage()).build();
		}
	}



	
	
	








}
