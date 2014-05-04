package model;

public class File {
	String name;
	Integer ID;
	int owner;
	
	public File(String name, Integer ID, int owner) {
		this.name = name;
		this.ID = -1;
		this.owner = owner;
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
	
	public int getOwner() {
		return this.owner;
	}
	
	public void setOwner(int newOwner) {
		this.owner = newOwner;
	}
	
	public String toString() {
		return this.getName();
	}
}
