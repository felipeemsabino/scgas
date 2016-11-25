package br.gov.scgas.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.mail.MessagingException;
import javax.mail.internet.AddressException;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.gov.scgas.dao.GenericDaoImpl;
import br.gov.scgas.entidade.Posto;
import br.gov.scgas.teste.MakeJsonTest;
import br.gov.scgas.util.SendEmail;



@Path("/hello")
public class FirstService {

	private String say;

	
	
	@Inject
	private MakeJsonTest geraJsonUsuarioApp;

	/**@Inject
	private PropietarioPetDaoImpl<PropietarioPets,Long> daoProp;
	 */

	@Inject
	private Gson gson;


	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {
		SendEmail sendEmail=new SendEmail();
		try {
			sendEmail.generateAndSendEmail("roberto2011.junior@gmail.com","123456");
		} catch (AddressException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return Response.status(200).entity(gson.toJson(say)).build();
	}

	@PostConstruct
	private void innit() {
		say = "Jersey say: "+geraJsonUsuarioApp.geraJsonUsuarioApp();
	}




}
