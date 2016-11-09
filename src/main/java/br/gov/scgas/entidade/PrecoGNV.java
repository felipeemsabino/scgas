package br.gov.scgas.entidade;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

public class PrecoGNV implements Serializable {
	private Long id;
	private Date dataHoraCadastro;
	private Posto posto;
	private ContaUsuario usuario;
	
	public ContaUsuario getUsuario() {
		return usuario;
	}
	public void setUsuario(ContaUsuario usuario) {
		this.usuario = usuario;
	}
	private BigDecimal valorGNV;
	
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
