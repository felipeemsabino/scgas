package br.gov.scgas.entidade;

import java.io.Serializable;
import java.util.Date;

public class ContaUsuario implements Serializable{

	private Long id;
	private String nome;
	private String email;
	private String senha;
	private String pinSenha;
	private String tokenFacebook;
	private String tokenGmail;
	private String tokenTwiter;
	private Date dataCadastro;

	public Date getDataCadastro() {
		return dataCadastro;
	}


	public void setDataCadastro(Date dataCadastro) {
		this.dataCadastro = dataCadastro;
	}


	public Long getId() {
		return id;
	}


	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}

	public void setId(Long id) {
		this.id = id;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getSenha() {
		return senha;
	}
	public void setSenha(String senha) {
		this.senha = senha;
	}
	public String getPinSenha() {
		return pinSenha;
	}
	public void setPinSenha(String pinSenha) {
		this.pinSenha = pinSenha;
	}
	public String getTokenFacebook() {
		return tokenFacebook;
	}
	public void setTokenFacebook(String tokenFacebook) {
		this.tokenFacebook = tokenFacebook;
	}
	public String getTokenGmail() {
		return tokenGmail;
	}
	public void setTokenGmail(String tokenGmail) {
		this.tokenGmail = tokenGmail;
	}
	public String getTokenTwiter() {
		return tokenTwiter;
	}
	public void setTokenTwiter(String tokenTwiter) {
		this.tokenTwiter = tokenTwiter;
	}



}
