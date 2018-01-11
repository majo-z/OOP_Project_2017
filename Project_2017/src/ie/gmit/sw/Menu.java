package ie.gmit.sw;

import javax.print.attribute.IntegerSyntax;
import java.io.File;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Menu {

	void show() {
		Scanner input = new Scanner(System.in);
		System.out.println("Enter path to first file:");
		String path1 = null;
		boolean file1Okay;
		do {
			path1 = input.nextLine();
			File file = new File(path1);
			file1Okay = file.exists();
			if (!file1Okay) {
				System.out.println("Could not find "+ path1 +", try again:");
			} else {
				System.out.println("File 1 okay \nEnter path to the second file:");
			}
		} while (!file1Okay);
		String path2 = null;
		boolean file2Okay;
		do {
			path2 = input.nextLine();
			File file = new File(path2);
			file2Okay = file.exists();
			if (!file2Okay) {
				System.out.println("Could not find "+ path2 +", try again:");
			} else {
				System.out.println("File 2 okay.");
			}
		} while (!file2Okay);

		System.out.print("Enter shingle size: ");
		int shingleSize = Integer.parseInt(input.nextLine());
		System.out.print("Enter number of minHashes: ");
		int k = Integer.parseInt(input.nextLine());

		try {
			Launcher.launch(path2, path2, shingleSize, k, 4);
		} catch (InterruptedException ex) {
			Logger.getLogger(Menu.class.getName()).log(Level.SEVERE, null, ex);
		}
	}
}
