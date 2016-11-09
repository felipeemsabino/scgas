package br.gov.scgas.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;

import br.gov.scgas.dao.GenericDaoImpl;
import br.gov.scgas.entidade.Posto;



@Path("/hello")
public class FirstService {

	private String say;

	@Inject
	private GenericDaoImpl<Object, Long> dao;

	/**@Inject
	private PropietarioPetDaoImpl<PropietarioPets,Long> daoProp;
	 */

	@Inject
	private Gson gson;


	@GET
	@Path("/{param}")
	public Response getMsg(@PathParam("param") String msg) {

		return Response.status(200).entity(gson.toJson(say)).build();
	}

	@PostConstruct
	private void meteAMaeJones() {
		try {
			dao.delete(new Posto());
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		say = "Jersey say: Teste ";







	}




}
