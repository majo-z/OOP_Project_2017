package ie.gmit.sw;

public class Shingle {
	// compare documents as int - call hash codes
	// can use comparator or comparable
	private int docID;// start at 0
	private int hashCode;

	// constructors + getters + setters
	public Shingle(int docID, int hashCode) {
		super();
		this.docID = docID;
		this.hashCode = hashCode;
	}

	protected int getDocID() {
		return docID;
	}

	protected void setDocID(int docID) {
		this.docID = docID;
	}

	protected int getHashCode() {
		return hashCode;
	}

	protected void setHashCode(int hashCode) {
		this.hashCode = hashCode;
	}

	public boolean isPoison() {
		return false;
	}

}
