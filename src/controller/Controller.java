package controller;

import java.util.ArrayList;

import javax.jws.WebParam.Mode;
import javax.print.attribute.standard.Media;

import mediator.IMediator;
import mediator.Mediator;
import model.*;

public class Controller {
	private IMediator mediator;
	
	public Controller(IMediator mediator) {
		this.mediator = mediator;
	}
	
	public void registerMyUser(String name){
		User myUser = mediator.registerUser(name);
		Model.getInstance().setMyUser(myUser);
	}
	
	public void updateUsers() {
		ArrayList<User> newUsers = mediator.getUsers();
		Model.getInstance().setUsers(newUsers);
	}
	
	public void updateFiles(String name) {
		ArrayList<File> newFiles = mediator.getFiles(Model.getInstance().getID(name));
		Model.getInstance().setFiles(newFiles);
	}
	
	public void updateTransfers() {
		Integer myID = Model.getInstance().getMyUser().getId();
		ArrayList<Transfer> newTrans = mediator.getTransfers(myID);
		Model.getInstance().setTransfers(newTrans);
	}
}
