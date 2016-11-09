package br.gov.scgas.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


public class Posto implements Serializable{
	
	private Long id;
	private String nome;
	private Date dataCadastro;
	private BandeiraPosto bandeiraPosto;
	private String coordenadaX;
	private String coordenadaY;
	private String numImovel;
	private List<PrecoGNV> listaPrecosGNV;
	
	public List<PrecoGNV> getListaPrecosGNV() {
		return listaPrecosGNV;
	}
	public void setListaPrecosGNV(List<PrecoGNV> listaPrecosGNV) {
		this.listaPrecosGNV = listaPrecosGNV;
	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	public BandeiraPosto getBandeiraPosto() {
		return bandeiraPosto;
	}
	public void setBandeiraPosto(BandeiraPosto bandeiraPosto) {
		this.bandeiraPosto = bandeiraPosto;
	}
	public String getCoordenadaX() {
		return coordenadaX;
	}
	public void setCoordenadaX(String coordenadaX) {
		this.coordenadaX = coordenadaX;
	}
	public String getCoordenadaY() {
		return coordenadaY;
	}
	public void setCoordenadaY(String coordenadaY) {
		this.coordenadaY = coordenadaY;
	}
	public String getNumImovel() {
		return numImovel;
	}
	public void setNumImovel(String numImovel) {
		this.numImovel = numImovel;
	}
	

}
