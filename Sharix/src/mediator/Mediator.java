package mediator;

import java.util.List;

import controller.Controller;

import model.*;

public class Mediator implements IMediator{
	Controller controller;
	
	public Mediator(Controller controller) {
		this.controller = controller;
	}
	
	//todo implement for the version 2.0
	public void registerUser(String username) {
		//Server call
		User myUser = null;
		this.controller.registerMyUser(myUser);
	}

	//todo implement for the version 2.0
	public void getUsersFromServer() {
		//Server call
		List<User> users = null;
		this.controller.updateUsers(users);	
	}
	
	//todo implement for the version 2.0
	public void getFilesFromServer(Integer userID) {
		//Server call
		List<File> files = null;
		controller.updateFiles(files);
	}
	
	//todo implement for the version 2.0
	public void getTransfersFromServer(Integer userID) {
		//Server call
		List<Transfer> tr = null;
		this.controller.updateTransfers(tr);
	}
	
	public void addTransfer(Transfer newTransfer) {
		this.controller.addTransfer(newTransfer);
	}

	public User getUser(int id) {
		return this.controller.getUser(id);
	}

	public User getMyUser() {
		return this.controller.getMyUser();
	}

	public boolean hasFile(String name) {
		return this.controller.hasFile(name);
	}

	@Override
	public void updateTransfer(Transfer tr) {
		this.controller.updateTransfer(tr);
	}
}