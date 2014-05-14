package mediator;

import networking.Dispatcher;
import model.File;
import model.Transfer;
import model.User;

public interface IMediator {
	void registerUser(String user);
	void getUsersFromServer();
	void getFilesFromServer(Integer userID);
	void getTransfersFromServer(Integer userID);	
	void addTransfer(Transfer newTransfer);
	void updateTransfer(Transfer tr);
	void selectUser(User user);
	User getSelectedUser();
	void connectTo(String ip, int port);
	void downloadFile(String name);
	void setDispatcher(Dispatcher dispatcher);
}
