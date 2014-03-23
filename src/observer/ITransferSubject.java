package observer;

import java.util.ArrayList;
import java.util.List;

public interface ITransferSubject {
	List<ITransferListener> transferListeners = new ArrayList<>();
	
	public void addTransferListener(ITransferListener transferListener);
	public void notifyListenersTransfersUpdated();
}
