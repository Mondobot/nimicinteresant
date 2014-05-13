package controller;

import java.util.List;
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
	
	public void updateTransfer(Transfer tr) {
		Model.getInstance().updateTransfer(tr);
	}
	
	public void addTransfer(Transfer newTransfer) {
		Model.getInstance().updateTransfer(newTransfer);
	}

	public boolean hasFile(String name) {
		return Model.getInstance().hasFile(name);
	}

	public User getMyUser() {
		return Model.getInstance().getMyUser();
	}

	public User getUser(int id) {
		return Model.getInstance().getUser(id);
	}
}
