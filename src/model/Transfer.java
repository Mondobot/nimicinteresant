package model;

public class Transfer {
	Integer ID;
	File cargo;
	User source;
	User dest;
	Integer progress;
	
	public Transfer() {
		this.ID = -1;
		this.cargo = new File();
		this.source = new User();
		this.dest = new User();
		this.progress = 0;
	}
	
	public Transfer(Integer ID, File cargo, User source, User dest) {
		this.ID = ID;
		this.cargo = cargo;
		this.source = source;
		this.dest = dest;
		this.progress = 0;
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
}
