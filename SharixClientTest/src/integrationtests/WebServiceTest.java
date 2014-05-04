package integrationtests;

import java.rmi.RemoteException;

import org.apache.axis2.AxisFault;

import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestResult;

import ops.SharixServiceStub;
import ops.SharixServiceStub.*;

public class WebServiceTest {
	static SharixServiceStub serviceStub;
	static UserSrv userSrv;
	
	public static void main(String []args) throws Exception{
		serviceStub = new SharixServiceStub();
		
		testRegisterUser();
		testRegisterSameUser();
		testGetConnectedUsers();
		testRegisterFilesByUser();
		testGetFilesByUser();
		testUnRegisterUser();
	}
	
	public static void testRegisterUser() throws Exception {
		RegisterNewUser registerUser = new RegisterNewUser();
		registerUser.setName("user_proba");
		
		userSrv = serviceStub.registerNewUser(registerUser).get_return();
		
		Assert.assertTrue(userSrv.getName().equals("user_proba"));
	}
	
	public static void testRegisterSameUser() throws Exception {
		RegisterNewUser registerUser = new RegisterNewUser();
		registerUser.setName("user_proba");
		
		UserSrv result = serviceStub.registerNewUser(registerUser).get_return();
		
		Assert.assertTrue(result == null);
	}
	
	public static void testGetConnectedUsers() throws Exception {
		GetConnectedUsers getConnectedUsers = new GetConnectedUsers();
		
		UserSrv []usersSrv = serviceStub.getConnectedUsers(getConnectedUsers).get_return();
		
		Assert.assertTrue(usersSrv.length == 1);
	}
	
	public static void testRegisterFilesByUser() throws Exception {
		RegisterFilesByUser registerFilesByUser = new RegisterFilesByUser();
		String []fileNames = new String[]{"file1", "file2", "file3"};

		registerFilesByUser.setIdUser(userSrv.getId());
		registerFilesByUser.setFileNames(fileNames);
		
		FileSrv []filesSrv = serviceStub.registerFilesByUser(registerFilesByUser).get_return();
			
		Assert.assertTrue(filesSrv.length == 3);
		Assert.assertTrue(filesSrv[0].getName().equals("file1"));
	}
	
	public static void testGetFilesByUser() throws Exception {
		GetFilesByUser getFilesByUser = new GetFilesByUser();
		getFilesByUser.setIdUser(userSrv.getId());
		
		FileSrv []filesSrv = serviceStub.getFilesByUser(getFilesByUser).get_return();
		
		Assert.assertTrue(filesSrv.length == 3);
		Assert.assertTrue(filesSrv[0].getName().equals("file1"));
	}
	
	public static void testUnRegisterUser() throws Exception {
		UnRegisterUser unRegisterUser = new UnRegisterUser();
		unRegisterUser.setId(userSrv.getId());
		
		boolean result = serviceStub.unRegisterUser(unRegisterUser).get_return();
		
		Assert.assertTrue(result == true);
	}
	
}