package br.gov.scgas.entidade;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Entity
@Table(name="TB_PARAMETROS_GERAL") 
@NamedQueries({
	@NamedQuery(name = "ParametrosGerais.recuperaParametros", 
			query = "select obj.valorMinGnv, obj.valorMaxGnv from ParametrosGerais obj "
			)

})

public class ParametrosGerais implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, length = 20)
	private Long id;
	
	@Column(name="VALOR_GNV_MIN",nullable = false, length = 10,precision=10,scale=3) 
	private BigDecimal valorMinGnv;
	
	@Column(name="VALOR_GNV_MAX",nullable = false, length = 10,precision=10,scale=3) 
	private BigDecimal valorMaxGnv;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BigDecimal getValorMinGnv() {
		return valorMinGnv;
	}

	public void setValorMinGnv(BigDecimal valorMinGnv) {
		this.valorMinGnv = valorMinGnv;
	}

	public BigDecimal getValorMaxGnv() {
		return valorMaxGnv;
	}

	public void setValorMaxGnv(BigDecimal valorMaxGnv) {
		this.valorMaxGnv = valorMaxGnv;
	}

	

	
	
	
	

}
