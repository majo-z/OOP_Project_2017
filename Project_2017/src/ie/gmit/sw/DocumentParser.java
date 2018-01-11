package ie.gmit.sw;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Deque;
import java.util.LinkedList;
import java.util.concurrent.BlockingQueue;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DocumentParser implements Runnable {

	private BlockingQueue<Shingle> queue;
	private String file;
	private int shingleSize;
	private int k;
	private Deque<String> buffer = new LinkedList<>();
	private int docID;// start at 0

	public DocumentParser(BlockingQueue<Shingle> queue, String file, int shingleSize, int k, int docID) {
		this.queue = queue;
		this.file = file;
		this.shingleSize = shingleSize;
		this.k = k;
		this.docID = docID;
	}

	public void run() {

		BufferedReader br = null;
		try {
			br = new BufferedReader(new InputStreamReader(new FileInputStream(file)));
			String line = null;
			while ((line = br.readLine()) != null) {
				String uLine = line.toUpperCase();
				String[] words = uLine.split(" ");// can also take regex

				addWordsToBuffer(words);
			} // while

			while (buffer.size() >= shingleSize) {
				queue.put(getNextShingle());// add is not a blocking method
			} // while

			queue.put(getPoison());

			br.close();
		} // run
		catch (FileNotFoundException ex) {
			Logger.getLogger(DocumentParser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (IOException ex) {
			Logger.getLogger(DocumentParser.class.getName()).log(Level.SEVERE, null, ex);
		} catch (InterruptedException ex) {
			Logger.getLogger(DocumentParser.class.getName()).log(Level.SEVERE, null, ex);
		} finally {
			try {
				br.close();
			} catch (IOException ex) {
				Logger.getLogger(DocumentParser.class.getName()).log(Level.SEVERE, null, ex);
			}
		}

	}

	private void addWordsToBuffer(String[] words) {
		for (String s : words) {
			buffer.addLast(s);
		}
	}

	private Shingle getNextShingle() {
		StringBuilder sb = new StringBuilder();
		int counter = 0;
		while (counter < shingleSize && buffer.peek() != null) {
			sb.append(buffer.poll());
			counter++;
		} // while
		if (counter > 0) {
			return new Shingle(docID, sb.toString().hashCode());// convert to string, then convert to number;
		} else {
			return null;
		}
	}// getNextShingle

	private Poison getPoison() {
		StringBuilder sb = new StringBuilder();
		while (buffer.peek() != null) {
			sb.append(buffer.poll());
		} // while

		return new Poison(docID, sb.toString().hashCode());// convert to string, then convert to number;
	}// getPoison

}// DocumentParser
