package br.gov.scgas.entidade;

import java.io.Serializable;

public class FirebaseNotification implements Serializable {
	private String title;
	private String body;
	private String icon;
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getIcon() {
		return icon;
	}
	public void setIcon(String icon) {
		this.icon = icon;
	}
}
