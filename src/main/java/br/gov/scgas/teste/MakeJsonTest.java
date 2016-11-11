package br.gov.scgas.teste;

import java.io.IOException;

import javax.inject.Inject;
import javax.ws.rs.client.Client;

import org.apache.catalina.WebResource;
import org.apache.http.client.ClientProtocolException;

import com.google.gson.Gson;

import br.gov.scgas.entidade.UsuarioApp;

public class MakeJsonTest {

	@Inject
	private Gson gson;

	@Inject
	private UsuarioApp usuarioApp;


	public String geraJsonUsuarioApp(){

		usuarioApp.setNome("Fulano");
		usuarioApp.setEmail("email@email.com");
		usuarioApp.setSenha("123456");
		usuarioApp.setTokenFacebook("123456");
		usuarioApp.setTokenGmail("123456");
		String rst = gson.toJson(usuarioApp);
		System.out.println(rst);



		return rst;
	}


	public static void main(String[] args) throws ClientProtocolException, IOException {

		
	}






}
