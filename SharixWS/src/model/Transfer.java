package model;

public class Transfer {
	int ID;
	User source;
	User dest;
	int progress;
	String status;	
	
	public Transfer(int ID, User source, User dest, int progress, String status) {
		this.ID = ID;
		this.source = source;
		this.dest = dest;
		this.progress = progress;
		
	}
	
	public int getID() {
		return this.ID;
	}
	
	public void setID(int newID) {
		this.ID = newID;
	}
	
	public User getSource() {
		return this.source;
	}
	
	public void setSource(User newSource) {
		this.source = newSource;
	}

	public User getDest() {
		return dest;
	}

	public void setDest(User dest) {
		this.dest = dest;
	}

	public int getProgress() {
		return progress;
	}

	public void setProgress(int progress) {
		this.progress = progress;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
		
}
