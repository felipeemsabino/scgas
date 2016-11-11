package br.gov.scgas.service;

import java.util.Date;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.gov.scgas.dao.GenericDaoImpl;
import br.gov.scgas.entidade.UsuarioApp;
import br.gov.scgas.util.GenerateSHA;



@Path("/usuarioservice")
public class UsuarioService {

	private String say;

	@Inject
	private GenericDaoImpl<UsuarioApp, Long> dao;

	@Inject
	private Gson gson;


	@POST
	@Path("cadastrarusuario")
	public Response getMsg(@Context HttpServletRequest request,String json) {
		UsuarioApp usuarioApp = gson.fromJson(json, UsuarioApp.class);
		usuarioApp.setDataCadastro(new Date());

		try {
			if(usuarioApp.getId() == null){
				usuarioApp.setSenha(GenerateSHA.getSHA256SecurePassword(usuarioApp.getSenha()));
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
		return Response.status(200).entity(gson.toJson(say)).build();
	}





}
