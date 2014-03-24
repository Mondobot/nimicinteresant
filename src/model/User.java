package model;

public class User{
	int id;
	String name;
	
	public User(int id, String name) {
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
	
	public String toString() {
		return this.getName();
	}
}