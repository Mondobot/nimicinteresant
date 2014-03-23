package observer;

import java.util.ArrayList;
import java.util.List;

public interface IFileSubject {
	List<IFileListener> fileListeners = new ArrayList<>();
	
	public void addFileListener(IFileListener fileListener);
	public void notifyListenersFilesUpdated();
}
