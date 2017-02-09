package br.gov.scgas.entidade;

import java.io.Serializable;

public class FiltroPosto implements Serializable{
	
	private int inicio;
	private int fim;
	private String nomePosto;
	private String bandeiraPosto;
	private String enderecoPosto;
	
	public int getInicio() {
		return inicio;
	}
	public void setInicio(int inicio) {
		this.inicio = inicio;
	}
	public int getFim() {
		return fim;
	}
	public void setFim(int fim) {
		this.fim = fim;
	}
	public String getNomePosto() {
		return nomePosto;
	}
	public void setNomePosto(String nomePosto) {
		this.nomePosto = nomePosto;
	}
	public String getBandeiraPosto() {
		return bandeiraPosto;
	}
	public void setBandeiraPosto(String bandeiraPosto) {
		this.bandeiraPosto = bandeiraPosto;
	}
	public String getEnderecoPosto() {
		return enderecoPosto;
	}
	public void setEnderecoPosto(String enderecoPosto) {
		this.enderecoPosto = enderecoPosto;
	}

}
