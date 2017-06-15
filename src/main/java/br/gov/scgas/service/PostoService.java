package br.gov.scgas.service;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.Response;

import org.hibernate.HibernateException;

import com.google.gson.Gson;

import br.gov.scgas.dao.BandeiraPostoDao;
import br.gov.scgas.dao.GenericDaoImpl;
import br.gov.scgas.dao.ParametrosGeraisDao;
import br.gov.scgas.dao.PostoDao;
import br.gov.scgas.entidade.BandeiraPosto;
import br.gov.scgas.entidade.FiltroPosto;
import br.gov.scgas.entidade.Noticias;
import br.gov.scgas.entidade.ParametrosGerais;
import br.gov.scgas.entidade.Posto;
import br.gov.scgas.entidade.PrecoGNV;
import br.gov.scgas.entidade.UsuarioApp;
import br.gov.scgas.util.BaseContantes;



@Path("/postoservice")
public class PostoService {


	@Inject
	private PostoDao<Posto, Long> dao;

	@Inject
	private PostoDao<PrecoGNV, Long> daoPrecoPosto;

	@Inject
	private BandeiraPostoDao<BandeiraPosto, Long> daoB;
	
	@Inject
	private ParametrosGeraisDao<ParametrosGerais, Long> daoParam;

	

	@Inject
	private Gson gson;


	@POST
	@Path("/cadastrarPosto")
	public Response cadastrarPosto(@Context HttpServletRequest request,String json) {
		Posto posto = gson.fromJson(json, Posto.class);
		posto.setDataCadastro(new Date());
		PrecoGNV price = new PrecoGNV();
		price.setValorGNV(posto.getPrecoGNV());


		try {
			if(posto.getId() == null){
				dao.save(posto);
			}else{
				dao.update(posto);
			}
			UsuarioApp usr= new UsuarioApp();
			usr.setId(1l);
			price.setUsuario(usr);
			Posto postoAux = new Posto();
			postoAux.setId(posto.getId());
			price.setPosto(postoAux);
			price.setDataHoraCadastro(new Date());
			daoPrecoPosto.save(price);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(gson.toJson(BaseContantes.msgAddPosto)).build();
		}

		posto.setListaPrecosGNV(null);
		return Response.status(200).entity(gson.toJson(posto)).build();
	}
	@POST
	@Path("/cadastrarBandeiraPosto")
	public Response cadastrarBandeiraPosto(@Context HttpServletRequest request,String json) {
		BandeiraPosto bandeiraPosto = gson.fromJson(json, BandeiraPosto.class);
		bandeiraPosto.setDataCadastro(new Date());

		try {
			if(bandeiraPosto.getId() == null){
				daoB.save(bandeiraPosto);
			}else{
				daoB.update(bandeiraPosto);
			}

		} catch (Exception e) {
			// TODO Auto-generated catch block

			e.printStackTrace();
			return Response.status(500).entity(gson.toJson(BaseContantes.msgErroCriarBandeira)).build();
		}

		return Response.status(200).entity(gson.toJson(bandeiraPosto)).build();
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
	@Path("/listaTodasBandeiras")
	public Response listaTodasBandeiras() {
		try{
			List<BandeiraPosto> listaBandeiras = new ArrayList<BandeiraPosto>();
			listaBandeiras = daoB.listAll(BandeiraPosto.class);
			return Response.status(200).entity(gson.toJson(listaBandeiras)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity(BaseContantes.msgErro404).build();																							
		}catch(Exception e){
			return Response.status(500).entity(BaseContantes.msgErro500).build();
		}
	}






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
	@SuppressWarnings("unchecked")
	@GET
	@Path("/listaPostos/{initialPosition}/{finalPosition}/{x}/{y}")
	public Response listaPostos(@PathParam("initialPosition") String initialPosition,@PathParam("finalPosition") String finalPosition,@PathParam("x") String x,@PathParam("y") String y) throws HibernateException, Exception {
		try{
			List listaPostos= new ArrayList();
			ParametrosGerais param = daoParam.getById(ParametrosGerais.class,1l);
			
			listaPostos = dao.listAllPosto(new Long(initialPosition),new Long(finalPosition));
			int cont = 0;
			for (Posto posto : (List<Posto>)listaPostos) {
				System.out.println("Linha : "+cont++);
				if(posto.getListaPrecosGNV() != null && !posto.getListaPrecosGNV().isEmpty()){
					PrecoGNV prc = posto.getListaPrecosGNV().get(0);
					prc.setPosto(null);
					long diferencaHoras = ( new Date().getTime() - prc.getDataHoraCadastro().getTime() ) / (1000*60*60);
					long diferencaDias = (  new Date().getTime() - prc.getDataHoraCadastro().getTime()) / (1000*60*60*24);

					if(diferencaHoras <= 0){
						prc.setTempoUltimaAtulizacao(BaseContantes.msgTempoAtualizacaoPreco);
					}else if(diferencaHoras >= 24){
						prc.setTempoUltimaAtulizacao(BaseContantes.txtAtualizada+ diferencaDias + BaseContantes.txtDiasAtras);
					}else{
						prc.setTempoUltimaAtulizacao(BaseContantes.txtAtualizada+ diferencaDias + BaseContantes.txtHorasAtras);			    	
					}
					prc.getUsuario().setSenha(null);
					prc.getUsuario().setEmail(null);
					prc.getUsuario().setTokenFacebook(null);
					prc.getUsuario().setTokenGmail(null);
					prc.getUsuario().setDataCadastro(null);

					posto.getListaPrecosGNV().clear();
					posto.getListaPrecosGNV().add(prc);
				}
				posto.setParametrosGerais(param);

				//Pega distancia
				Float distancia = (float) Math.round(distFrom(new Float(x.replace(",", ".")),new Float(y.replace(",", ".")), new Float(posto.getCoordenadaX().replace(",", ".")),new Float(posto.getCoordenadaY().replace(",", "."))));
				posto.setDistanciaParaOdernar(distancia);
				if(distancia > 1000){
					posto.setDistanciaPosto(distancia/1000+"KM");
				}else{
					posto.setDistanciaPosto(distancia+"KM");			    	
				}


			}
			Collections.sort(listaPostos);
			Integer contador = dao.contaLinhas();

			if(contador.longValue() <= new Long(finalPosition) ){
				listaPostos.add("{hasMore:"+0+"}");
			}else{
				listaPostos.add("{hasMore:"+1+"}");				
			}

			String json = gson.toJson(listaPostos);

			dao.closeDao();
			return Response.status(200).entity(json).build();			
		}catch(HibernateException e){
			return Response.status(404).entity(BaseContantes.msgErroRecPosto).build();																							
		}catch(Exception e){
			e.printStackTrace();
			dao.closeDao();
			return Response.status(500).entity(null).build();
		}




	}


	public  float distFrom(float lat1, float lng1 , float lat2, float lng2) {
		double earthRadius = 6371000; //meters
		double dLat = Math.toRadians(lat2-lat1);
		double dLng = Math.toRadians(lng2-lng1);
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) +
				Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) *
				Math.sin(dLng/2) * Math.sin(dLng/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a));
		float dist = (float) (earthRadius * c);

		return dist;
	}

	@POST
	@Path("/atualizaPrecoCombustivel")
	public Response atualizaPrecoCombustivel(@Context HttpServletRequest request,String json) {
		PrecoGNV price = gson.fromJson(json, PrecoGNV.class);
		price.setDataHoraCadastro(new Date());
		/**UsuarioApp usr = new UsuarioApp();
		usr.setId(new Long(435));
		Posto posto=new Posto();
		posto.setId(new Long(1));
		price.setUsuario(usr);
		price.setPosto(posto);
		price.setValorGNV(new BigDecimal("2.599"));

		String rst = getGson().toJson(price);*/


		try {
			ParametrosGerais param = daoParam.getById(ParametrosGerais.class,1l);
			
			if(param.getValorMaxGnv().compareTo(price.getValorGNV()) < 0 || param.getValorMinGnv().compareTo(price.getValorGNV()) > 0 ){
				return Response.status(500).entity(gson.toJson(BaseContantes.msgPriceOutRange1+param.getValorMinGnv()+
						BaseContantes.msgPriceOutRange2+param.getValorMaxGnv())).build();
			}
			
			daoPrecoPosto.save(price);

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(gson.toJson(BaseContantes.msgErroUpdatePrice)).build();
		}
		return Response.status(200).entity(gson.toJson(price)).build();
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
	@Path("/listaBandeiras")
	public Response listaBandeiras() {
		try{
			List<BandeiraPosto> bandeiraPostos = new ArrayList<BandeiraPosto>();
			bandeiraPostos = daoB.listAll(BandeiraPosto.class);

			return Response.status(200).entity(gson.toJson(bandeiraPostos)).build();			
		}catch(HibernateException e){
			return Response.status(404).entity(BaseContantes.msgErro404).build();																							
		}catch(Exception e){
			return Response.status(500).entity(null).build();
		}
	}



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
	@SuppressWarnings("unchecked")
	@GET
	@Path("/listaTodosPostos")
	public Response listaTodosPostos(@QueryParam("inicio") String inicio,
			@QueryParam("fim") String fim,
			@QueryParam("nomePosto") String nomePosto,
			@QueryParam("bandeiraPosto") String bandeiraPosto,
			@QueryParam("enderecoPosto") String enderecoPosto) throws HibernateException, Exception {
		try{

			FiltroPosto filtro = new FiltroPosto();
			filtro.setInicio(new Integer(inicio));
			filtro.setFim(new Integer(fim));
			filtro.setNomePosto(nomePosto);
			filtro.setEnderecoPosto(enderecoPosto);
			filtro.setBandeiraPosto(bandeiraPosto);

			List listaPostos= new ArrayList();
			listaPostos = dao.listAllPostoFiltro(filtro);

			for (Posto posto : (List<Posto>)listaPostos) {
				if(posto.getListaPrecosGNV() != null && !posto.getListaPrecosGNV().isEmpty()){
					PrecoGNV prc = posto.getListaPrecosGNV().get(0);
					posto.setPrecoGNV(prc.getValorGNV());
					prc.setPosto(null);
					long diferencaHoras = ( new Date().getTime() - prc.getDataHoraCadastro().getTime() ) / (1000*60*60);
					long diferencaDias = (  new Date().getTime() - prc.getDataHoraCadastro().getTime()) / (1000*60*60*24);

					if(diferencaHoras <= 0){
						prc.setTempoUltimaAtulizacao(BaseContantes.msgTempoAtualizacaoPreco);
					}else if(diferencaHoras >= 24){
						prc.setTempoUltimaAtulizacao(BaseContantes.txtAtualizada+ diferencaDias + BaseContantes.txtDiasAtras);
					}else{
						prc.setTempoUltimaAtulizacao(BaseContantes.txtAtualizada+ diferencaDias + BaseContantes.txtHorasAtras);			    	
					}
					prc.getUsuario().setSenha(null);
					prc.getUsuario().setEmail(null);
					prc.getUsuario().setTokenFacebook(null);
					prc.getUsuario().setTokenGmail(null);
					prc.getUsuario().setDataCadastro(null);

					posto.getListaPrecosGNV().clear();
					posto.getListaPrecosGNV().add(prc);
				}

			}
			Collections.sort(listaPostos);
			int contador = dao.countPostoFiltro(filtro);
			listaPostos.add("{numRows:"+contador+"}");
			String jsonRst = gson.toJson(listaPostos);

			dao.closeDao();
			return Response.status(200).entity(jsonRst).build();			
		}catch(HibernateException e){

			return Response.status(404).entity(BaseContantes.msgErroRecPosto).build();																							
		}catch(Exception e){
			e.printStackTrace();
			dao.closeDao();
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

	@POST
	@Path("/deletaPosto")
	public Response deletaPosto(@Context HttpServletRequest request,String json) {
		Posto posto = gson.fromJson(json, Posto.class);
		try {
			dao.delete(posto);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return Response.status(500).entity(gson.toJson(BaseContantes.msgErroDelPosto)).build();
		}

		return Response.status(200).entity(gson.toJson(BaseContantes.msgDelPosto)).build();


	}

	public PostoDao<Posto, Long> getDao() {
		return dao;
	}


	public void setDao(PostoDao<Posto, Long> dao) {
		this.dao = dao;
	}


	public PostoDao<PrecoGNV, Long> getDaoPrecoPosto() {
		return daoPrecoPosto;
	}


	public void setDaoPrecoPosto(PostoDao<PrecoGNV, Long> daoPrecoPosto) {
		this.daoPrecoPosto = daoPrecoPosto;
	}


	public Gson getGson() {
		return gson;
	}


	public void setGson(Gson gson) {
		this.gson = gson;
	}
	
	public BandeiraPostoDao<BandeiraPosto, Long> getDaoB() {
		return daoB;
	}
	public void setDaoB(BandeiraPostoDao<BandeiraPosto, Long> daoB) {
		this.daoB = daoB;
	}
	public ParametrosGeraisDao<ParametrosGerais, Long> getDaoParam() {
		return daoParam;
	}
	public void setDaoParam(ParametrosGeraisDao<ParametrosGerais, Long> daoParam) {
		this.daoParam = daoParam;
	}
	





}
