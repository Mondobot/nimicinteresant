package ops;

import helper.MainHelper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.File;
import model.Transfer;
import model.User;

public class Main {
	int result;
	List<User> users = new ArrayList<>();
	Map<Integer, File> filesByUser = new HashMap<>();
	List<Transfer> transfers = new ArrayList<>();
	
	public synchronized void registerNewUser(int id, String name) {
		users.add(new User(MainHelper.generateUserId(), name));
	}
	
	public synchronized void registerFilesByUser(int idUser, String nameFile) {
		filesByUser.put(idUser, new File(MainHelper.generateFileId(), nameFile, idUser));
	}
	
	
	
	public synchronized int add(int a, int b) {
		result = a + b;
		System.out.println("result is " + result);
		return 1;
	}
	
	public synchronized int getResult(int x) {
		return result;
	}
}
