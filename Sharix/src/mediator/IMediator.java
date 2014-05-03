package mediator;

import java.util.ArrayList;

import controller.Controller;

import model.File;
import model.Transfer;
import model.User;

public interface IMediator {
	void registerUser(String user);
	void getUsers();
	void getFiles(Integer userID);
	void getTransfers(Integer userID);	
	void updateTransfer(Transfer newTransfer);
}
