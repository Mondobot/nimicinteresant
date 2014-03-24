package mediator;

import java.util.ArrayList;

import model.*;

public class Mediator implements IMediator{

	public Mediator() {
	}
	
	//todo implement for the version 2.0
	public User registerUser(String username) {
		//register user to the web service in the version 2.0
		return null;
	}

	//todo implement for the version 2.0
	public ArrayList<User> getUsers() {
		return new ArrayList<User>();
	}
	
	//todo implement for the version 2.0
	public ArrayList<File> getFiles(Integer userID) {
		return new ArrayList<File>();
	}
	
	//todo implement for the version 2.0
	public ArrayList<Transfer> getTransfers(Integer userID) {
		return new ArrayList<Transfer>();
	}
}