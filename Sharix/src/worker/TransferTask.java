package worker;

import java.util.List;

import javax.swing.SwingWorker;

import mediator.IMediator;
import model.Transfer;

public class TransferTask extends SwingWorker<Integer, Integer> {
	  private static final int DELAY = 1000;
	  private Transfer transfer;
	  private IMediator mediator;

	  public TransferTask(IMediator mediator, Transfer transfer) {
		  this.mediator = mediator;
		  this.transfer = transfer;
	  }

	  @Override
	  protected Integer doInBackground() throws Exception {
		  boolean done = false;
		  while (!done) {
			  Thread.sleep(DELAY);
			  transfer.setProgress(transfer.getProgress() + 5);
			  if (transfer.getProgress() >= 100) {
				  transfer.setProgress(100);
				  transfer.setStatus("Completed");
				  done = true;
			  }
			  publish();
		  }
		  
		  return 0;
	  }
	   
	  protected void process(List<Integer> chunks) {
		  mediator.updateTransfer(transfer);
	  }

	  @Override
	  protected void done() {
	    if (isCancelled())
	      System.out.println("Cancelled !");
	    else
	      System.out.println("Done !");
	  }
	}