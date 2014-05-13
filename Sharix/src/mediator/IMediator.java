package mediator;

import java.util.ArrayList;

import controller.Controller;

import model.File;
import model.Transfer;
import model.User;

public interface IMediator {
	void registerUser(String user);
	void getUsersFromServer();
	void getFilesFromServer(Integer userID);
	void getTransfersFromServer(Integer userID);	
	void newTransfer(Transfer newTransfer);
}
