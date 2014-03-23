package controller;

import java.util.ArrayList;

import javax.print.attribute.standard.Media;

import mediator.Mediator;
import model.*;

public class Controller {
	private static Controller con;
	
	private Controller() {
	}
	
	public static Controller getInstance() {
		if (con == null)
			con = new Controller();
		
		return con;
	}
	
	public void updateUsers() {
		ArrayList<User> newUsers = Mediator.getInstance().getUsers();
		Model.getInstance().setUsers(newUsers);
	}
	
	public void updateFiles() {
		Integer myID = Model.getInstance().getMyUser().getID();
		ArrayList<File> newFiles = Mediator.getInstance().getFiles(myID);
		Model.getInstance().setFiles(newFiles);
	}
	
	public void updateTransfers() {
		Integer myID = Model.getInstance().getMyUser().getID();
		ArrayList<Transfer> newTrans = Mediator.getInstance().getTransfers(myID);
		Model.getInstance().setTransfers(newTrans);
	}
}
