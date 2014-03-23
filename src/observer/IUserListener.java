package observer;

import java.util.List;
import model.User;

public interface IUserListener {
	public void usersUpdated(List<User> users);
}
