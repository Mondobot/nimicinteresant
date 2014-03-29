package worker;

import java.util.List;

import javax.swing.SwingWorker;

public class TransferTask extends SwingWorker<Integer, Integer> {
	  private static final int DELAY = 1000;
	  private int progress;

	  public TransferTask(int progress) {
		  this.progress = progress;
	  }

	  @Override
	  protected Integer doInBackground() throws Exception {
		  boolean done = false;
		  while (!done) {
			  Thread.sleep(DELAY);
			  progress += 5;
			  if (progress > 100) {
				  progress = 100;
				  done = true;
			  }
			  publish();
		  }
		  
		  return 0;
	  }
	   
	  protected void process(List<Integer> chunks) {
		  
	  }

	  @Override
	  protected void done() {
	    if (isCancelled())
	      System.out.println("Cancelled !");
	    else
	      System.out.println("Done !");
	  }
	}