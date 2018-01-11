package ie.gmit.sw;
//Who creates consumer class? - Launcher

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Consumer implements Runnable {// Everything is in God class - has to be broken to different classes

	private BlockingQueue<Shingle> queue;
	private int k;
	private int[] minHashes;// the random stuff
	private Map<Integer, List<Integer>> minListForDocs = new HashMap<>();

	private ExecutorService pool;// the thread pool size in menu to specify

	public Consumer(BlockingQueue<Shingle> q, int k, int poolSize) {
		this.queue = q;
		this.k = k;
		pool = Executors.newFixedThreadPool(poolSize);
		init();// initialize the minhash array and fill it up with random stuff
	}

	private void init() {
		Random random = new Random();
		minHashes = new int[k];// k = 200 ~ 300
		for (int i = 0; i < minHashes.length; i++) {
			minHashes[i] = random.nextInt();// generates random int
		}
	}// init

	static int counter = 0;

	public void run() {
		int docCount = 2;// fix this, this is hard coded
		while (docCount > 0) {
			try {
				int count = queue.size();
				Shingle s = queue.take();// blocking method (wait while other threads add at least one shingle to this
											// queue) - pick one shingle and process its hash

				if (s.isPoison()) {
					docCount--;
				}

				List<Integer> list;
				synchronized (minListForDocs) { // block the map while the work with it is finished
					list = minListForDocs.get(s.getDocID());
					if (list == null) { // doc has not got its own list yet
						list = new ArrayList<>(k); // initialize once for each document
						for (int i = 0; i < k; i++) {
							list.add(Integer.MAX_VALUE);
						}
						minListForDocs.put(s.getDocID(), list);
					}
				}

				final List<Integer> hashList = list;

				pool.execute(new Runnable() {

					@Override
					public void run() {
						int privateCounter = counter++;
						System.out.println(privateCounter + " start");

						for (int i = 0; i < minHashes.length; i++) {// loop over minHashes
							int value = s.getHashCode() ^ minHashes[i];// xor

							if (hashList.get(i) > 0) {
								hashList.set(i, value);
							}
						}
						System.out.println(privateCounter + " done");
					}

				});
			} catch (InterruptedException ex) {
				Logger.getLogger(Consumer.class.getName()).log(Level.SEVERE, null, ex);
			}
		}
		;// while
		System.out.println("\n\n\n  RUN DONE  \n\n\n");
	}// run
}
