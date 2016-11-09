package br.gov.scgas.entidade;

import java.io.Serializable;
import java.util.Date;

public class Noticias implements Serializable {
	
	private Long id;
	private String titulo;
	private String textoNoticia;
	private Date dataCadastro;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getTextoNoticia() {
		return textoNoticia;
	}
	public void setTextoNoticia(String textoNoticia) {
		this.textoNoticia = textoNoticia;
	}
	public Date getDataCadastro() {
		return dataCadastro;
	}
	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}
	

}
