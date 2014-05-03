package helper;

public class MainHelper {
	private static int userId;
	private static int fileId;
	
	public static int generateUserId() {
		return ++userId;
	}
	
	public static int generateFileId() {
		return ++fileId;
	}
}
