package model;

import java.io.Serializable;

public class UserSrv implements Serializable{
	private static final long serialVersionUID = 1000000004L;
	
	int id;
	String name;
	String ip;
	String port;
	
	public UserSrv() {
	}
	
	public UserSrv(int id, String name, String ip, String port) {
		this.name = name;
		this.id = id;
		this.ip = ip;
		this.port = port;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getIp() {
		return this.ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public String getPort() {
		return this.port;
	}
	
	public void setPort(String port) {
		this.port = port;
	}
}