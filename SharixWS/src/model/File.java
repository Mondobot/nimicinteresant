package model;

public class File {
	int id;
	String name;
	int owner;
	
	public File(int id, String name, int owner) {
		this.name = name;
		this.id = id;
		this.owner = owner;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public Integer getId() {
		return this.id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public int getOwner() {
		return this.owner;
	}
	
	public void setOwner(int owner) {
		this.owner = owner;
	}
}
