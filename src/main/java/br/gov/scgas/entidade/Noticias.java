package br.gov.scgas.entidade;

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

@Entity
@Table(name="TB_NOTICIA") 
public class Noticias implements Serializable {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, length = 20)
	private Long id;
	
	@Column(name="TITULO", nullable = false, length = 100) 
	private String titulo;
	
	@Column(name="TEXTO_NOTICIA", nullable = false, length = 200) 
	private String textoNoticia;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CAD",nullable = false, length = 11)  
	private Date dataCadastro;
	
	@Enumerated(EnumType.STRING)
	@Column(name="NOTIFICA", nullable = true)
	private SimNao notifica;
	
	public SimNao getNotifica() {
		return notifica;
	}
	public void setNotifica(SimNao notifica) {
		this.notifica = notifica;
	}
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
