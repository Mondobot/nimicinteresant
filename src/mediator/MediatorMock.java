package mediator;

import java.util.ArrayList;

import model.File;
import model.Transfer;
import model.User;

public class MediatorMock implements IMediator {
	ArrayList<User> users;
	ArrayList<File> u1, u2, u3;
	ArrayList<Transfer> tr;
	
	
	public MediatorMock() {
		users.add(new User("Churchill", 0));
		users.add(new User("Hitler", 3));
		users.add(new User("Stalin", 1));
		
		u1.add(new File("Secret British stuff", 0, users.get(0)));
		u2.add(new File("We all can Hitler..", 1, users.get(1)));
		u3.add(new File("You can't read this, I have Enigma", 2, users.get(2)));
		u3.add(new File("I did Nazi that coming", 3, users.get(2)));
		
		tr.add(new Transfer(0, u1.get(0), users.get(0), users.get(2)));
		tr.add(new Transfer(1, u3.get(0), users.get(2), users.get(1)));
		
	}
	
	@Override
	public ArrayList<User> getUsers() {
		// TODO Auto-generated method stub
		return this.users;
	}

	@Override
	public ArrayList<File> getFiles(Integer userID) {
		// TODO Auto-generated method stub
		if (userID == 0)
			return this.u1;
		
		if (userID == 1)
			return this.u2;
		
		return this.u3;
	}

	@Override
	public ArrayList<Transfer> getTransfers(Integer userID) {
		// TODO Auto-generated method stub
		return this.tr;
	}
}
