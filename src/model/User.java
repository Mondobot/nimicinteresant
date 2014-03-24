package model;

public class User{
	String name;
	int id;
	
	public User() {
		this.name = "None";
		this.id = -1;
	}
	
	public User(String name, int id) {
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
	
	public void setID(int newId) {
		this.id = newId;
	}
}