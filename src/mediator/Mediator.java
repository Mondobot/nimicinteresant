package mediator;

import java.util.ArrayList;

import model.*;

public class Mediator {
	private static Mediator med;

	private Mediator() {
	}
	
	public static Mediator getInstance() {
		if (med == null)
			med = new Mediator();
	
		return med;
	}
	
	public ArrayList<User> getUsers() {
		System.out.println("Getting users");
		return new ArrayList<User>();
	}
	
	public ArrayList<File> getFiles(Integer userID) {
		System.out.println("Getting files for " + userID);
		return new ArrayList<File>();
	}
	
	public ArrayList<Transfer> getTransfers(Integer userID) {
		System.out.println("Getting transfers for " + userID);
		return new ArrayList<Transfer>();
	}
}