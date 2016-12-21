package br.gov.scgas.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="TB_PRECO_GNV") 
public class PrecoGNV implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, length = 20)
	private Long id;
	
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name="DATA_HORA_CAD",nullable = false, length = 20) 
	private Date dataHoraCadastro;
	
	@ManyToOne  
	@JoinColumn(name="ID_POSTO",referencedColumnName="ID") 
	private Posto posto;
	
	@ManyToOne  
	@JoinColumn(name="ID_USUARIO",referencedColumnName="ID") 
	private UsuarioApp usuario;
	
	@Transient
	private String tempoUltimaAtulizacao;
	
	@Column(name="valorgnv",nullable = false, length = 10,precision=10,scale=3) 
	private BigDecimal valorGNV;
	
	
	
	public String getTempoUltimaAtulizacao() {
		return tempoUltimaAtulizacao;
	}
	public void setTempoUltimaAtulizacao(String tempoUltimaAtulizacao) {
		this.tempoUltimaAtulizacao = tempoUltimaAtulizacao;
	}
	public UsuarioApp getUsuario() {
		return usuario;
	}
	public void setUsuario(UsuarioApp usuario) {
		this.usuario = usuario;
	}
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getDataHoraCadastro() {
		return dataHoraCadastro;
	}
	public void setDataHoraCadastro(Date dataHoraCadastro) {
		this.dataHoraCadastro = dataHoraCadastro;
	}
	public Posto getPosto() {
		return posto;
	}
	public void setPosto(Posto posto) {
		this.posto = posto;
	}
	public BigDecimal getValorGNV() {
		return valorGNV;
	}
	public void setValorGNV(BigDecimal valorGNV) {
		this.valorGNV = valorGNV;
	}
	
	
	
}
