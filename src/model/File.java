package model;

public class File {
	String name;
	Integer ID;
	User owner;
	
	public File() {
		this.name = "None";
		this.ID = -1;
		this.owner = new User();
	}
	
	public File(String name, Integer ID, User owner) {
		this.name = name;
		this.ID = -1;
		this.owner = owner;
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
	
	User getOwner() {
		return this.owner;
	}
	
	void setOwner(User newOwner) {
		this.owner = newOwner;
	}
}
