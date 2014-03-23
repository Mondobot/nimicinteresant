package observer;

import java.util.List;
import model.Transfer;

public interface ITransferListener {
	public void transfersUpdated(List<Transfer> transfers);
}
