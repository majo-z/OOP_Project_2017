package ie.gmit.sw;

public class Poison extends Shingle {
	// generate constructors from superclass
	public Poison(int docID, int hashCode) {
		super(docID, hashCode);
	}

	@Override
	public boolean isPoison() {
		return true;
	}

}
