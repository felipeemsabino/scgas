
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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name="TB_USUARIO_APP") 
@NamedQueries({
	@NamedQuery(name = "UsuarioApp.autenticacaoFacebook", 
			query = "select obj  from UsuarioApp obj  where obj.tokenFacebook = :idFacebook and (obj.ativo is null or obj.ativo = 'S') "
					+ " and (obj.excluido is null or obj.excluido = 'N')"
			),
	@NamedQuery(name = "UsuarioApp.autenticacaoGmail", 
	query = "select obj  from UsuarioApp obj  where obj.tokenGmail = :idGmail and (obj.ativo is null or obj.ativo = 'S') "
					+ " and (obj.excluido is null or obj.excluido = 'N')"
	),
	@NamedQuery(name = "UsuarioApp.autentica", query = "select obj  from UsuarioApp obj"
			+ " where obj.email = :email and obj.senha = :senha and (obj.ativo is null or obj.ativo = 'S') "
					+ " and (obj.excluido is null or obj.excluido = 'N')"
			),
	@NamedQuery(name = "UsuarioApp.recuperaUsuarioEmail", query = "select obj  from UsuarioApp obj"
			+ " where obj.email = :email and (obj.ativo is null or obj.ativo = 'S') "
					+ " and (obj.excluido is null or obj.excluido = 'N')"
			),
	@NamedQuery(name = "UsuarioApp.recuperaSenhaPorPIN", query = "select obj  from UsuarioApp obj"
			+ " where obj.email = :email and obj.pinSenha = :pinSenha and (obj.ativo is null or obj.ativo = 'S') "
					+ " and (obj.excluido is null or obj.excluido = 'N')"
			)


})

public class UsuarioApp implements Serializable{

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "ID", nullable = false, length = 20)
	private Long id;
	
	
	@Column(name="NOME_USUARIO", nullable = false, length = 100) 
	private String nome;
	
	@Column(name="EMAIL",nullable=true,length = 100)
	private String email;
	
	@Column(name="SENHA", nullable = true, length = 200) 
	private String senha;
	
	@Column(name="PIN_SENHA", nullable = true, length = 200) 
	private String pinSenha;
	
	@Column(name="TK_FACEBOOK", nullable = true, length = 200,unique = true) 
	private String tokenFacebook;
	
	@Column(name="TK_GMAIL", nullable = true, length = 200,unique = true) 
	private String tokenGmail;
	
	@Column(name="TK_TWITER", nullable = true, length = 200,unique = true) 
	private String tokenTwiter;
	
	@Temporal(TemporalType.DATE)
	@Column(name="DATA_CAD",nullable = false, length = 11) 
	private Date dataCadastro;
	
	@Enumerated(EnumType.STRING)
	@Column(name="ATIVO", nullable = true)
	private SimNao ativo;
	
	@Enumerated(EnumType.STRING)
	@Column(name="EXCLUIDO", nullable = true)
	private SimNao excluido;
	
	public SimNao getExcluido() {
		return excluido;
	}


	public void setExcluido(SimNao excluido) {
		this.excluido = excluido;
	}
	@Column(name="TK_NOTIFICACAO", nullable = true, length = 200,unique = true) 
	private String tokenNotificacao;


	public String getTokenNotificacao() {
		return tokenNotificacao;
	}


	public void setTokenNotificacao(String tokenNotificacao) {
		this.tokenNotificacao = tokenNotificacao;
	}


	public SimNao getAtivo() {
		return ativo;
	}


	public void setAtivo(SimNao ativo) {
		this.ativo = ativo;
	}


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
