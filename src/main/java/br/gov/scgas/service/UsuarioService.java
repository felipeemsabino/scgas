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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import com.google.gson.Gson;

import br.gov.scgas.dao.UsuarioDao;
import br.gov.scgas.entidade.FiltroUsuarioApp;
import br.gov.scgas.entidade.UsuarioApp;
import br.gov.scgas.util.GenerateSHA;
import br.gov.scgas.util.SendEmail;



@Path("/usuarioservice")
public class UsuarioService {


	@Inject
	private UsuarioDao<UsuarioApp, Long> dao;

	@Inject
	private Gson gson;


	@POST
	@Path("/cadastrarusuario")
	public Response cadastroUsuario(@Context HttpServletRequest request,String json) {
		UsuarioApp usuarioApp = gson.fromJson(json, UsuarioApp.class);
		usuarioApp.setDataCadastro(new Date());

		try {
			if(usuarioApp.getId() == null){
				if(usuarioApp.getSenha() != null){
					usuarioApp.setSenha(GenerateSHA.getSHA256SecurePassword(usuarioApp.getSenha()));					
				}
				dao.save(usuarioApp);
				usuarioApp.setSenha(null);
			}else{
				if((usuarioApp.getSenha() == null) || (usuarioApp.getSenha() != null && usuarioApp.getSenha().isEmpty())){
					UsuarioApp aux = dao.getById(UsuarioApp.class, usuarioApp.getId());
					usuarioApp.setSenha(aux.getSenha());
				}else{
					usuarioApp.setSenha(GenerateSHA.getSHA256SecurePassword(usuarioApp.getSenha()));				
				}

				dao.update(usuarioApp);
				usuarioApp.setSenha(null);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return Response.status(500).entity(gson.toJson("Erro ao criar usuario!")).build();
		}

		return Response.status(200).entity(gson.toJson(usuarioApp)).build();
	}

	/**
	 * @author robertosampaio
	 * @since 11/11/2016
	 * @return Response com json completo do objeto  e codigo do resultado da operacao
	 * Códigos possiveis
	 * 200 (OK, registro encontrado)
	 * 404 (Registro não encontrado)
	 * 500 (Exception lancada por algum motivo)
	 * 
	 **/
	@POST
	@Path("/autentica")
	public Response autenticaSenha(@Context HttpServletRequest request,String json) {
		UsuarioApp usr=null;
		try {
			usr = gson.fromJson(json,UsuarioApp.class);
			String pass = GenerateSHA.getSHA256SecurePassword(usr.getSenha());
			usr = dao.autenticaUsuario(usr.getEmail(), pass);

			if(usr == null){
				return Response.status(404).entity(gson.toJson("Usuario não cadastrado")).build();							
			}

			usr.setSenha(null);
			return Response.status(200).entity(gson.toJson(usr)).build();
		}catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(404).entity("Usuario não cadastrado").build();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(null).build();
		}
	}


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
	@GET
	@Path("/autenticaFacebook/{id}")
	public Response autenticaFacebook(@PathParam("id") String idParam) {
		try{
			UsuarioApp usuarioApp = null;
			if(idParam != null){
				usuarioApp = dao.recuperaUsuarioPorFacebook(idParam);
			}

			if(usuarioApp == null){
				return Response.status(404).entity(gson.toJson("Usuario não cadastrado")).build();							
			}

			usuarioApp.setSenha(null);

			return Response.status(200).entity(gson.toJson(usuarioApp)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Usuario não cadastrado").build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}
	}

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
	@GET
	@Path("/autenticaGmail/{id}")
	public Response autenticaGmail(@PathParam("id") String idParam) {
		try{
			UsuarioApp usuarioApp = null;
			if(idParam != null){
				usuarioApp = dao.recuperaUsuarioPorGmail(idParam);
			}
			if(usuarioApp == null){
				return Response.status(404).entity(gson.toJson("Usuario não cadastrado")).build();							
			}

			usuarioApp.setSenha(null);

			return Response.status(200).entity(gson.toJson(usuarioApp)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Usuario não cadastrado").build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}
	}





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
	@GET
	@Path("/geraPinSenha/{email}")
	public Response geraPinSenha(@PathParam("email") String email) {
		try{
			UsuarioApp usuarioApp = null;
			if(email != null){
				usuarioApp = dao.recuperaUsuarioEmail(email);
			}
			if(usuarioApp == null){
				return Response.status(404).entity(gson.toJson("Usuario não cadastrado")).build();							
			}
			Random gerador = new Random(new Date().getTime());
			String numPin="0";
			numPin+=gerador.nextInt()*-1;

			if(numPin.length() > 4){
				numPin = numPin.substring(0,4);
			}

			usuarioApp.setPinSenha(numPin);
			dao.update(usuarioApp);
			usuarioApp.setSenha(null);
			SendEmail sendEmail=new SendEmail();
			sendEmail.generateAndSendEmail(usuarioApp.getEmail(),numPin);
			return Response.status(200).entity("Foi enviado ao seu email o PIN, digite para recuperar sua senha.").build();			
		}catch(HibernateException e){
			e.printStackTrace();
			return Response.status(404).entity("Usuario não cadastrado").build();																							
		}catch(Exception e){
			e.printStackTrace();
			return Response.status(500).entity(null).build();
		}
	}



	@POST
	@Path("/recuperaSenhaPorPIN")
	public Response recuperaSenhaPorPIN(@Context HttpServletRequest request,String json) {
		UsuarioApp aux = gson.fromJson(json, UsuarioApp.class);
		aux.setDataCadastro(new Date());

		try {
			aux = dao.recuperaSenhaPorPIN(aux.getEmail(),aux.getPinSenha());
			if(aux != null){
				aux.setPinSenha(null);				
				dao.update(aux);
				aux.setSenha(null);				
			}else{
				return Response.status(404).entity(gson.toJson("PIN de recuperação de senha não encontrado!")).build();
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(gson.toJson("Erro ao recuperar senha do usuario!")).build();
		}
		return Response.status(200).entity(gson.toJson(aux)).build();
	}



	@GET
	@Path("/listaUsuarios/{inicio}/{fim}/{nome}/{email}")
	public Response listaUsuarios(@PathParam("inicio") String inicio,
			@PathParam("fim") String fim,
			@PathParam("nome") String nome,
			@PathParam("email") String email) {
		try{
			FiltroUsuarioApp filtro = new FiltroUsuarioApp();
			filtro.setInicio(new Integer(inicio));
			filtro.setFim(new Integer(fim));
			filtro.setNome(nome);
			filtro.setEmail(email);



			List listaUsr = new ArrayList();
			int contador = dao.countUsrFiltro(filtro);
			listaUsr.add("{numRows:"+contador+"}");
			listaUsr.addAll(dao.listAllUsrFiltro(filtro));
			String jsonAux = gson.toJson(listaUsr);
			dao.closeDao();
			return Response.status(200).entity(jsonAux).build();			
		}catch(HibernateException e){
			return Response.status(404).entity("Registro não encontrado.").build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}finally{
			try {
				dao.closeDao();
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}














}
