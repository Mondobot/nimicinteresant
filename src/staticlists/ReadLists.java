package staticlists;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import model.User;


public class ReadLists {
	List<User> users = new ArrayList<>();
	List<model.File> u1 = new ArrayList<>();
	List<model.File> u2 = new ArrayList<>();
	List<model.File> u3 = new ArrayList<>();
	
	public ReadLists(List<User> users, List<model.File> u1, List<model.File> u2, List<model.File> u3) {
		this.users = users;
		this.u1 = u1;
		this.u2 = u2;
		this.u3 = u3;
	}
	
	public void read(){
		String filesPath = getFilesPath();
		getUsersAndFiles(filesPath);
		
	}
	
	public String getFilesPath(){
		File file = new File("edi.txt");
		
		try {
			BufferedReader br = new BufferedReader(new FileReader(file));
			String filesFolder = br.readLine();
			String path = filesFolder.substring(filesFolder.indexOf("=") + 1);
			
			return path;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}	
	}
	
	public void getUsersAndFiles(String path) {
		File dir = new File(path);
		File[] files = dir.listFiles();
		
		int id = 0;
		for (File file: files) {
			String fileName = file.getName();
			String username = fileName.substring(0, fileName.lastIndexOf("."));

			User newUser = new User(id, username);
			users.add(newUser);	
			
			try {
				BufferedReader br = new BufferedReader(new FileReader(file));
				String filename = br.readLine();
				while (filename != null) {
					if (id == 0)
						u1.add(new model.File(filename, 0, newUser));
					if (id == 1)
						u2.add(new model.File(filename, 0, newUser));
					if (id == 2)
						u3.add(new model.File(filename, 0, newUser));
					filename = br.readLine();
				}
				
				id++;
			} catch (Exception e) {
				e.printStackTrace();
			}	
		}
	}
}
