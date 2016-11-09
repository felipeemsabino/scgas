package br.gov.scgas.entidade;

import java.io.Serializable;
import java.util.Date;


public class BandeiraPosto implements Serializable {
	private Long id;
	private String nome;
	private String urlImgBandeira;
	private Date dataCadastro;
	
	
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
	public String getUrlImgBandeira() {
		return urlImgBandeira;
	}
	public void setUrlImgBandeira(String urlImgBandeira) {
		this.urlImgBandeira = urlImgBandeira;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	
	
	

}
