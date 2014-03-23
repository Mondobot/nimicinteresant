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
	
	String getName() {
		return this.name;
	}
	
	void setName(String newName) {
		this.name = newName;
	}
	
	Integer getID() {
		return this.ID;
	}
	
	void setID(Integer newID) {
		this.ID = newID;
	}
}