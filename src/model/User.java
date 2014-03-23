package model;

public class User {
	String name;
	Integer ID;
	
	public User() {
		this.name = "None";
		this.ID = -1;
	}
	
	public User(String name, Integer ID) {
		this.name = name;
		this.ID = ID;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String newName) {
		this.name = newName;
	}
	
	public Integer getID() {
		return this.ID;
	}
	
	public void setID(Integer newID) {
		this.ID = newID;
	}
}