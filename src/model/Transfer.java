package model;

public class Transfer {
	Integer ID;
	File cargo;
	User source;
	User dest;
	Integer progress;
	String status;
	
	public Transfer(Integer ID, File cargo, User source, User dest) {
		this.ID = ID;
		this.cargo = cargo;
		this.source = source;
		this.dest = dest;
		this.progress = 0;
		this.status = "None";
	}
	
	public Integer getID() {
		return this.ID;
	}
	
	public void setID(Integer newID) {
		this.ID = newID;
	}
	
	public User getSource() {
		return this.source;
	}
	
	public void setSource(User newSource) {
		this.source = newSource;
	}

	public File getCargo() {
		return cargo;
	}

	public void setCargo(File cargo) {
		this.cargo = cargo;
	}

	public User getDest() {
		return dest;
	}

	public void setDest(User dest) {
		this.dest = dest;
	}

	public Integer getProgress() {
		return progress;
	}

	public void setProgress(Integer progress) {
		this.progress = progress;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
		
}
