package ops;

import helper.SharixServiceHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.FileSrv;
import model.UserSrv;

public class SharixService {
	int result;
	List<UserSrv> users = new ArrayList<>();
	Map<Integer, List<FileSrv>> filesByUser = new HashMap<>();

	//return userSrv if username is unique, null otherwise
	public synchronized UserSrv registerNewUser(String name, String ip, String port) {
		for (UserSrv u : users)
			if (u.getName().equalsIgnoreCase(name))
				return null;
		
		UserSrv userSrv = new UserSrv(SharixServiceHelper.generateUserId(), name, ip, port);
		users.add(userSrv);
		
		return userSrv;
	}
	
	//return true if unregistered successful
	public synchronized boolean unRegisterUser(int id) {
		for (UserSrv u: users)
			if (u.getId() == id) {
				users.remove(u);
				return true;
			}
		
		return false;
	}
	
	public synchronized List<UserSrv> getConnectedUsers() {
		return users;
	}
	
	//return fileSrv registered
	public synchronized List<FileSrv> registerFilesByUser(int idUser, String[] fileNames) {
		List<FileSrv> filesSrv = new ArrayList<>();
		
		for (String fileName : fileNames) {
			FileSrv file = new FileSrv(SharixServiceHelper.generateFileId(), fileName, idUser);
			filesSrv.add(file);
		}
 		
		filesByUser.put(idUser, filesSrv);
		
		return filesSrv;
	}
	
	public synchronized List<FileSrv> getFilesByUser(int idUser) {
		return filesByUser.get(idUser);
	}
	
}
