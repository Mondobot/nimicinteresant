package model;

import networking.MsgHandler;

public class Transfer {
	int ID;
	MsgHandler cargo;
	User source;
	User dest;
	double progress;
	String status;
	
	public static String DWNLD = "Downloading";
	public static String UPLD = "Uploading";
	public static String CMPLT = "Completed";
	
	
	public Transfer(int ID, User source, User dest, MsgHandler cargo, int progress, String status) {
		this.ID = ID;
		this.cargo = cargo;
		this.source = source;
		this.dest = dest;
		this.progress = progress;
		
		if (status.equals(Transfer.DWNLD) || status.equals(Transfer.UPLD) || status.equals(Transfer.CMPLT))
			this.status = status;
		
		else
			System.out.println("Error! Invalid state for transfer");
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

	public MsgHandler getCargo() {
		return cargo;
	}

	public void setCargo(MsgHandler cargo) {
		this.cargo = cargo;
	}

	public User getDest() {
		return dest;
	}

	public void setDest(User dest) {
		this.dest = dest;
	}

	public double getProgress() {
		return progress;
	}

	public void setProgress(double d) {
		this.progress = d;
	}
	
	public String getStatus() {
		return this.status;
	}
	
	public void setStatus(String newStatus) {
		this.status = newStatus;
	}
		
}
