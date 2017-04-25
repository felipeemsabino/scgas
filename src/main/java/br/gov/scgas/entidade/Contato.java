package br.gov.scgas.entidade;

import com.google.gson.annotations.Expose;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;


public class Contato implements Serializable
{
	
	
	@Expose(serialize=true, deserialize=true)
	private String titulo;
	
	
	@Expose(serialize=true, deserialize=true)
	private String textoContato;


	public String getTitulo() {
		return titulo;
	}


	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}


	public String getTextoContato() {
		return textoContato;
	}


	public void setTextoContato(String textoContato) {
		this.textoContato = textoContato;
	}
	
	
	
	
	
}
