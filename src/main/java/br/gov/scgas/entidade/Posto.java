package br.gov.scgas.entidade;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OrderBy;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

@Entity
@Table(name="TB_POSTO") 
public class Posto implements Serializable{
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, length = 20)
	private Long id;
	
	@Column(name="NOME", nullable = false, length = 100,unique = true) 
	private String nome;
	
	@Column(name="ENDERECO", nullable = false, length = 200,unique = true) 
	private String endereco;
	
	public String getEndereco() {
		return endereco;
	}
	public void setEndereco(String endereco) {
		this.endereco = endereco;
	}
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CAD",nullable = false, length = 11) 
	private Date dataCadastro;
	
	@ManyToOne  
	@JoinColumn(name="ID_BANDEIRA",referencedColumnName="ID") 
	private BandeiraPosto bandeiraPosto;
	
	@Column(name="COORDENADAX", nullable = false, length = 20)
	private String coordenadaX;
	
	@Column(name="COORDENADAY", nullable = false, length = 20)
	private String coordenadaY;
	
	@Column(name="NUM_IMOVEL",length = 20,nullable=false)
	private String numImovel;
	
	@OneToMany(cascade = CascadeType.ALL, targetEntity = PrecoGNV.class,fetch= FetchType.LAZY ,mappedBy = "posto")
	@OrderBy("DATA_HORA_CAD desc")
	private List<PrecoGNV> listaPrecosGNV;
	
	@Transient
	private String tempoUltimaAtulizacao;
	
	public String getTempoUltimaAtulizacao() {
		return tempoUltimaAtulizacao;
	}
	public void setTempoUltimaAtulizacao(String tempoUltimaAtulizacao) {
		this.tempoUltimaAtulizacao = tempoUltimaAtulizacao;
	}
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
