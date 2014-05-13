package mediator;

import java.util.ArrayList;
import java.util.List;

import staticlists.ReadLists;

import worker.TransferTask;

import controller.Controller;

import model.File;
import model.Transfer;
import model.User;

public class MediatorMock implements IMediator {
	List<User> users = new ArrayList<>();
	List<File> u1 = new ArrayList<>();
	List<File> u2 = new ArrayList<>();
	List<File> u3 = new ArrayList<>();
	List<Transfer> tr = new ArrayList<>();
	
	Controller controller;
	
	public MediatorMock(Controller controller) {
		new ReadLists(users, u1, u2, u3).read();
/*		users.add(new User(0, "Churchill"));
		users.add(new User(3, "Hitler"));
		users.add(new User(1, "Stalin"));
		
		u1.add(new File("Secret British stuff", 0, users.get(0)));
		u2.add(new File("We all can Hitler..", 1, users.get(1)));
		u3.add(new File("You can't read this, I have Enigma", 2, users.get(2)));
		u3.add(new File("I did Nazi that coming", 3, users.get(2)));
		
		tr.add(new Transfer(0, users.get(0), users.get(2), u1.get(0), 10, "Sending..."));
		tr.add(new Transfer(1, users.get(1), users.get(2), u3.get(0), 100, "Completed"));*/
		
		this.controller = controller;
	}
	
	@Override
	public void registerUser(String username) {
		User myUser = new User(-1, username);
		
		controller.registerMyUser(myUser);
	}
	
	@Override
	public void getUsersFromServer() {
		controller.updateUsers(this.users);		
	}

	@Override
	public void getFilesFromServer(Integer userID) {
		if (userID == 0)
			controller.updateFiles(this.u1);
		
		if (userID == 1)
			controller.updateFiles(this.u2);
		
		if (userID == 2)
			controller.updateFiles(this.u3);
		
		if (userID < 0 || userID > 2)
			System.out.println("whaaat?");
	}

	@Override
	public void getTransfersFromServer(Integer userID) {
		for (Transfer transfer : tr){
			TransferTask transferTask = new TransferTask(this, transfer);
			transferTask.execute();
		}
		this.controller.updateTransfers(tr);
	}
	
	
	
	@Override
	public void addTransfer(Transfer newTransfer){
		this.controller.addTransfer(newTransfer);
	}

	@Override
	public void updateTransfer(Transfer tr) {
		this.controller.updateTransfer(tr);
	}
}
