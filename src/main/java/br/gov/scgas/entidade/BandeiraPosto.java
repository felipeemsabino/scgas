package br.gov.scgas.entidade;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TB_BANDEIRA_POSTO") 
public class BandeiraPosto implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, length = 20)
	private Long id;
	
	@Column(name="NOME_BANDEIRA", nullable = false, length = 100) 
	private String nome;
	
	@Column(name="URL_IMG", nullable = true, length = 200) 
	private String urlImgBandeira;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CAD",nullable = false, length = 11) 
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
