package br.gov.scgas.entidade;

import java.io.Serializable;

public class FirebaseMensagem implements Serializable {
	private String to;
	private int time_to_live;
	private boolean delay_while_idle;
	private String collapse_key;
	private String priority;
	private FirebaseNotification notification;
	private FirebaseData data;


	public FirebaseMensagem(){

	}
	public FirebaseMensagem(String to, FirebaseNotification notification, FirebaseData data){
		this.to = to;
		this.notification = notification;
		this.data = data;
	}

	public String getTo() {
		return to;
	}

	public void setTo(String to) {
		this.to = to;
	}

	public FirebaseNotification getNotification() {
		return notification;
	}

	public void setNotification(FirebaseNotification notification) {
		this.notification = notification;
	}

	public FirebaseData getData() {
		return data;
	}

	public void setData(FirebaseData data) {
		this.data = data;
	}

	public int getTime_to_live() {
		return time_to_live;
	}

	public void setTime_to_live(int time_to_live) {
		this.time_to_live = time_to_live;
	}

	public boolean isDelay_while_idle() {
		return delay_while_idle;
	}

	public void setDelay_while_idle(boolean delay_while_idle) {
		this.delay_while_idle = delay_while_idle;
	}

	public String getCollapse_key() {
		return collapse_key;
	}

	public void setCollapse_key(String collapse_key) {
		this.collapse_key = collapse_key;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}


}
