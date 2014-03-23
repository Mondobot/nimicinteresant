package model;

import java.util.List;

import observer.IUserListener;
import observer.IUserSubject;

public class Model implements IUserSubject {
	private static Model model;
	public List<User> users;
	
	private Model(){
		
	}
	
	public static Model getInstance(){
		if (model != null)
			model = new Model();
		return model;
	}

	public void setUsers() {
		
		notifyListenersUsersUpdated();
	}
	
	@Override
	public void addUserListener(IUserListener userListener) {
		userListeners.add(userListener);
		
	}

	@Override
	public void removeUserListener(IUserListener userListener) {
		userListeners.remove(userListener);		
	}

	@Override
	public void notifyListenersUsersUpdated() {
		for (IUserListener userListener : userListeners)
			userListener.usersUpdated(users);
	}
}
