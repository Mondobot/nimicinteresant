package webservice.testing.integration;

import java.util.ArrayList;
import java.util.List;

import model.File;
import model.User;

import junit.framework.Assert;

import webservice.handlers.SharixServiceStub;
import webservice.ops.SharixWS;

public class WebServiceTest {
	static SharixServiceStub serviceStub;
	static User user;
	
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
		user = SharixWS.getInstanceOf().registerUser("user_proba", "192.168.1.1", "1020");
		
		Assert.assertTrue(user.getName().equals("user_proba"));
	}
	
	public static void testRegisterSameUser() throws Exception {
		User sameUser = SharixWS.getInstanceOf().registerUser("user_proba", "192.168.1.1", "1020");
		
		Assert.assertTrue(sameUser == null);
	}
	
	public static void testGetConnectedUsers() throws Exception {
		List<User> users = SharixWS.getInstanceOf().getUsers();
		
		Assert.assertTrue(users.size() == 1);
	}
	
	public static void testRegisterFilesByUser() throws Exception {
		List <String> fileNames = new ArrayList<>();
		fileNames.add("file1");
		fileNames.add("file2");
		fileNames.add("file3");

		List<File> files = SharixWS.getInstanceOf().registerFilesByUser(user.getId(), fileNames);
			
		Assert.assertTrue(files.size() == 3);
		Assert.assertTrue(files.get(0).getName().equals("file1"));
	}
	
	public static void testGetFilesByUser() throws Exception {
		List<File> files = SharixWS.getInstanceOf().getFilesByUser(user.getId());
		
		Assert.assertTrue(files.size() == 3);
		Assert.assertTrue(files.get(0).getName().equals("file1"));
	}
	
	public static void testUnRegisterUser() throws Exception {
		boolean result = SharixWS.getInstanceOf().unRegisterUser(user.getId());
		
		Assert.assertTrue(result == true);
	}
	
}