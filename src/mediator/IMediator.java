package mediator;

import java.util.ArrayList;

import model.File;
import model.Transfer;
import model.User;

public interface IMediator {
	User registerUser(String user);
	ArrayList<User> getUsers();
	ArrayList<File> getFiles(Integer userID);
	ArrayList<Transfer> getTransfers(Integer userID);	
}
