package model;

import java.io.Serializable;

public class UserSrv implements Serializable{
	private static final long serialVersionUID = 1000000003L;
	
	int id;
	String name;
	
	public UserSrv() {
	}
	
	public UserSrv(int id, String name) {
		this.name = name;
		this.id = id;
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
}