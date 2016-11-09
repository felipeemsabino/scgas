package br.gov.scgas.service;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.core.Response;

import com.google.gson.Gson;



@Path("/hello")
public class FirstService {
	
	private String say;

	/**@Inject
	private GenericDao<Object, Long> genericDao;*/
	
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
		say = "Jersey say: ";
				 

		
		
		
		

	}
	
	
	

}
