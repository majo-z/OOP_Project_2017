package ie.gmit.sw;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

public class Launcher {
	public static void launch(String f1, String f2, int shingleSize, int kNum, int poolSize)
			throws InterruptedException {
		// doc1, doc2
		// shingleSize
		// k - number of minHashes
		// BlockingQueueSize
		BlockingQueue<Shingle> q = new LinkedBlockingQueue<>();// LinkedBlockingQueue<>(size)

		Thread t1 = new Thread(new DocumentParser(q, f1, shingleSize, kNum, 0));
		Thread t2 = new Thread(new DocumentParser(q, f2, shingleSize, kNum, 1));

		t1.start();
		t2.start();
		// t1.join(); //no need to wait here because Consumer consumes blocking queue
		// and waits for second Poison in the queue
		// t2.join();

		Thread consumerThread = new Thread(new Consumer(q, kNum, poolSize));
		consumerThread.start();
		System.out.println("\n\n\n  STARTED  \n\n\n");
		consumerThread.join(); // wait till consumer process last Poison

		System.out.println("\n\n\n  FINISHED  \n\n\n");
		//System.exit(0);
	}
	
	
}
