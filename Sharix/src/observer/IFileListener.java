package observer;

import java.util.List;
import model.File;

public interface IFileListener {
	public void filesUpdated(List<File> files);
}
