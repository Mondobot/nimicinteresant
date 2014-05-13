package mediator;

import controller.Controller;

import model.*;

public class Mediator implements IMediator{
	Controller controller;
	
	public Mediator(Controller controller) {
		this.controller = controller;
	}
	
	//todo implement for the version 2.0
	public void registerUser(String username) {
		
	}

	//todo implement for the version 2.0
	public void getUsersFromServer() {
		
	}
	
	//todo implement for the version 2.0
	public void getFilesFromServer(Integer userID) {
		
	}
	
	//todo implement for the version 2.0
	public void getTransfersFromServer(Integer userID) {
		
	}
	
	public void newTransfer(Transfer newTr) {
		
	}

	public User getUser(int id) {
		return Model.getInstance().getUser(id);
	}

	public User getOwnUser() {
		return Model.getInstance().getMyUser();
	}

	public boolean hasFile(String name) {
		return Model.getInstance().hasFile(name);
	}
}