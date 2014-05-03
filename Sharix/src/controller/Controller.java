package controller;

import java.util.ArrayList;
import java.util.List;

import worker.TransferTask;

import mediator.IMediator;
import model.*;

public class Controller {
	
	public Controller() {
	}
	
	public void registerMyUser(User myUser){
		Model.getInstance().setMyUser(myUser);
	}
	
	public void updateUsers(List<User> newUsers) {
		Model.getInstance().setUsers(newUsers);
	}
	
	public void updateFiles(List<File> newFiles) {
		Model.getInstance().setFiles(newFiles);
	}
	
	public void updateTransfers(List<Transfer> newTransfers) {
		Model.getInstance().setTransfers(newTransfers);
	}
	
	public void updateTransfer(Transfer newTransfer) {
		Model.getInstance().updateTransfer(newTransfer);
	}
}