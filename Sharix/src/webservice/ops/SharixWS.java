package webservice.ops;


import webservice.handlers.SharixServiceStub;
import webservice.handlers.SharixServiceStub.FileSrv;
import webservice.handlers.SharixServiceStub.GetConnectedUsers;
import webservice.handlers.SharixServiceStub.GetFilesByUser;
import webservice.handlers.SharixServiceStub.RegisterFilesByUser;
import webservice.handlers.SharixServiceStub.RegisterNewUser;
import webservice.handlers.SharixServiceStub.UnRegisterUser;
import webservice.handlers.SharixServiceStub.UserSrv;
import model.File;
import model.User;

public class SharixWS {
	SharixServiceStub serviceStub;
	private static SharixWS instance;
	
	private SharixWS() {
		try {
			serviceStub = new SharixServiceStub();
		} catch (AxisFault e) {
			e.printStackTrace();
		}
	}
	
	public static SharixWS getInstanceOf() {
		if (instance == null)
			instance = new SharixWS();
		
		return instance;
	}
	
	public User registerUser(String username) {
		RegisterNewUser registerUser = new RegisterNewUser();
		registerUser.setName(username);
		
		try {
			UserSrv userSrv = serviceStub.registerNewUser(registerUser).get_return();
			
			if (userSrv != null)
				return new User(userSrv.getId(), userSrv.getName());
			
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<User> getUsers() {
		GetConnectedUsers getConnectedUsers = new GetConnectedUsers();	
		
		try {
			UserSrv []usersSrv = serviceStub.getConnectedUsers(getConnectedUsers).get_return();
			List<User> users = new ArrayList<>();
			
			if (usersSrv != null) {
				for (UserSrv userSrv : usersSrv) {
					User u = new User(userSrv.getId(), userSrv.getName());
					users.add(u);
				}
				
				return users;
			}
			
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<File> registerFilesByUser(int userId, List<String> fileNames) {
		RegisterFilesByUser registerFilesByUser = new RegisterFilesByUser();

		registerFilesByUser.setIdUser(userId);
		registerFilesByUser.setFileNames(fileNames.toArray(new String[fileNames.size()]));
		
		try {
			FileSrv []filesSrv = serviceStub.registerFilesByUser(registerFilesByUser).get_return();
			List<File> files = new ArrayList<>();
			
			if (filesSrv != null) {
				for (FileSrv fileSrv : filesSrv) {
					File file = new File(fileSrv.getName(), fileSrv.getId(), fileSrv.getOwner());			
					files.add(file);
				}
				
				return files;
			}
			
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public List<File> getFilesByUser(int userId) {
		GetFilesByUser getFilesByUser = new GetFilesByUser();
		getFilesByUser.setIdUser(userId);
		
		try {
			FileSrv []filesSrv = serviceStub.getFilesByUser(getFilesByUser).get_return();
			List<File> files = new ArrayList<>();
			
			if (filesSrv != null) {
				for (FileSrv fileSrv : filesSrv) {
					File file = new File(fileSrv.getName(), fileSrv.getId(), fileSrv.getOwner());				
					files.add(file);
				}
				
				return files;
			}
			
			return null;
		} catch (RemoteException e) {
			e.printStackTrace();
			return null;
		}
	}
	
	public boolean unRegisterUser(int userId) {
		UnRegisterUser unRegisterUser = new UnRegisterUser();
		unRegisterUser.setId(userId);
		
		try {
			return serviceStub.unRegisterUser(unRegisterUser).get_return();
		} catch (RemoteException e) {
			e.printStackTrace();
			return false;
		}
	}
}
