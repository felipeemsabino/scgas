package br.gov.scgas.entidade;

import java.io.Serializable;

public class FirebaseData implements Serializable {
	private String titulo;
	private String descripcion;
	private String dataActivity;
	private String  idChat;
	private String  idUsuarioDestinatario;
	
	public String getIdUsuarioDestinatario() {
		return idUsuarioDestinatario;
	}
	public void setIdUsuarioDestinatario(String idUsuarioDestinatario) {
		this.idUsuarioDestinatario = idUsuarioDestinatario;
	}
	public String getIdChat() {
		return idChat;
	}
	public void setIdChat(String idChat) {
		this.idChat = idChat;
	}
	private String token;

	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public String getDescripcion() {
		return descripcion;
	}
	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}
	public String getDataActivity() {
		return dataActivity;
	}
	public void setDataActivity(String dataActivity) {
		this.dataActivity = dataActivity;
	}

}
