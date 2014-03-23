package controller;

public class Controller {
	private static Controller con;
	
	private Controller() {
	}
	
	public static Controller getInstance() {
		if (con == null)
			con = new Controller();
		
		return con;
	}
}
