package model;

import java.util.ArrayList;
import java.util.List;

import observer.*;

public class Model implements IUserSubject, IFileSubject, ITransferSubject {
	private static Model model;
	private User myUser;
	private List<User> users;
	private List<File> files;
	private List<Transfer> transfers;
	
	private Model(){
		users = new ArrayList<>();
		files = new ArrayList<>();
		transfers = new ArrayList<>();
	}
	
	public static Model getInstance(){
		if (model == null)
			model = new Model();
		return model;
	}
	
	public User getMyUser() {
		return myUser;
	}

	public void setMyUser(User myUser) {
		this.myUser = myUser;
	}

	public void setUsers(List<User> users) {
		this.users = users;
		System.out.println(this.users.size() + " model");
		notifyListenersUsersUpdated();
	}
	
	public List<User> getUsers() {
		return users;
	}

	public void setFiles(List<File> files) {
		this.files = files;
		notifyListenersFilesUpdated();
	}
	
	public List<File> getFiles() {
		return files;
	}
	
	public void setTransfers(List<Transfer> transfers) {
		this.transfers = transfers;
		notifyListenersTransfersUpdated();
	}

	public List<Transfer> getTransfers() {
		return transfers;
	}
	
	public void updateTransfer(Transfer newTransfer) {
		for (Transfer transfer: transfers)
			if (transfer.getID() == newTransfer.getID()) {
				transfer = newTransfer;
				notifyListenersTransfersUpdated();
			}
	}

	@Override
	public void addUserListener(IUserListener userListener) {
		userListeners.add(userListener);
	}

	@Override
	public void notifyListenersUsersUpdated() {
		for (IUserListener userListener : userListeners)
			userListener.usersUpdated(users);
	}

	@Override
	public void addFileListener(IFileListener fileListener) {
		fileListeners.add(fileListener);
	}

	@Override
	public void notifyListenersFilesUpdated() {
		for (IFileListener fileListener : fileListeners)
			fileListener.filesUpdated(files);		
	}

	@Override
	public void addTransferListener(ITransferListener transferListener) {
		transferListeners.add(transferListener);		
	}

	@Override
	public void notifyListenersTransfersUpdated() {
		for (ITransferListener transferListener : transferListeners)
			transferListener.transfersUpdated(transfers);
	}
	
	public int getID(String name) {
		for (User i:users) {
			if (i.getName().equals(name))
				return i.getId();
		}
		
		return -1;
	}

	public boolean hasFile(String name) {
		for (File i:files) {
			if (i.getName().equals(name))
				return true;
		}
			
		return false;	
	}

	public User getUser(int id) {
		for (User i:users) {
			if (i.getId() == id)
				return i;
		}

		return null;
	}
	
	
}
