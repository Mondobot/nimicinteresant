package model;

public class User{
	int id;
	String name;
	String IP;
	int PORT;
	
	public User(int id, String name, String IP, int PORT) {
		this.name = name;
		this.id = id;
		this.IP = IP;
		this.PORT = PORT;
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
	
	public String getIP() {
		return this.IP;
	}
	
	public void setIP(String IP) {
		this.IP = IP;
	}
	
	public int getPORT() {
		return this.PORT;
	}
	
	public void setPORT(int PORT) {
		this.PORT = PORT;
	}
	
}