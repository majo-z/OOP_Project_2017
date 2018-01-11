package ie.gmit.sw;

import java.util.ArrayList;
import java.util.List;

public class ComputeJackCard {

	// Option 1

	/*
	 * int k; List<Integer> intersection = new ArrayList(a);
	 * intersection.reatainAll(b); float jackcard = ((float) intersection.size()) /
	 * ((k*2) + (float) intersection.size());
	 */

	// Option 2
	/*
	 * int k; public float getJackJardDist(List<Integer> a, List<Integer> b) { float
	 * kF = (float) k; List<Integer> temp = new ArrayList<> (a); float intersection
	 * = temp.retainAll(b).size(); return intersection / ((k+k)-intersection);
	 * 
	 * }
	 */

}
