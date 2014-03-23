package observer;

import java.util.List;
import java.util.ArrayList;


public interface IUserSubject {
	List<IUserListener> userListeners = new ArrayList<>();
	
	public void addUserListener(IUserListener userListener);
	public void notifyListenersUsersUpdated();
}
