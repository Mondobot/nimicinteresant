package mediator;

import java.util.ArrayList;

import model.File;
import model.Transfer;
import model.User;

public interface IMediator {
	ArrayList<User> getUsers();
	ArrayList<File> getFiles(Integer userID);
	ArrayList<Transfer> getTransfers(Integer userID);	
}
