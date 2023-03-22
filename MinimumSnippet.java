import java.util.ArrayList;
import java.util.List;

public class MinimumSnippet {


	private int minimumSnippetSize;
	private int termsSize;
	private Iterable<String> doc;
	private List<String> terms;
	private ArrayList<String> minimumSnippet;
	private ArrayList<String> document;
	private int startOfSnippet;
	private int endOfSnippet;

	/* Computes the minimum snippet from a given document with a set of terms. */
	public MinimumSnippet(Iterable<String> document, List<String> terms) {

		ArrayList<String> tempSnippet = new ArrayList<>();
		this.termsSize = terms.size();
		this.doc = document;
		this.terms = terms;
		this.minimumSnippetSize = this.terms.size();
		this.minimumSnippet = new ArrayList<String>();
		this.document = convertToArrayList(this.doc);

		if(terms.isEmpty()) {
			throw new IllegalArgumentException("Error: No terms were entered for the search!");
		} else {
			if(foundAllTerms()) {
				boolean shouldAdd = false;
				int position = 0;
				while(tempSnippet.size() != minimumSnippetSize) {
					for(int current = position; current < this.document.size() && 
							!tempSnippet.containsAll(this.terms); current++) {
						for(String element : this.terms) {
							if(element.equals(this.document.get(current)) && shouldAdd == false) {
								//if current element is in the document, add this current element
								shouldAdd = true;
							}
							if(shouldAdd == true && !tempSnippet.containsAll(this.terms)) {
								//if snippet is not complete and this element should be added
								tempSnippet.add(this.document.get(current));
								if(tempSnippet.size() == 1) {
									//if this is the start of the snippet
									this.startOfSnippet = current;
								}
								if(tempSnippet.size() == minimumSnippetSize && tempSnippet.containsAll(terms)){
									//if this is the end of the snippet
									this.endOfSnippet = current;
								}
								if(tempSnippet.containsAll(this.terms)) {
									//once snippet is done stop adding terms
									shouldAdd = false;
								}
								break;
							}
						}
					}
					if(minimumSnippetSize == tempSnippet.size() && tempSnippet.containsAll(terms)){
						//if the snippet is as small as it can be and has everything in it 
						minimumSnippet = tempSnippet;
						break;
					} else {
						//start the process over but from the next element in the document
						tempSnippet = new ArrayList<>();
						position++;
					}
					if(position == this.document.size()) {
						/*if we are at the end and we haven't reached the smallest snippet, start over and make the 
						 * snippet one element bigger*/
						position = 0;
						minimumSnippetSize++;
					}
				}
			}
		}
	}

	/* This method just turns the Iterable String into an ArrayList of strings. */
	private ArrayList<String> convertToArrayList(Iterable<String> doc2) {
		ArrayList<String> newDoc = new ArrayList<>();
		doc2 = this.doc;
		for(String element : doc2) {
			newDoc.add(element);
		}
		return newDoc;
	}

	/* This method checks to see if the terms are there in the document. Returns true if they are all there false 
	 * otherwise. */
	public boolean foundAllTerms() {
		return this.document.containsAll(terms);
	}

	/* This method returns the index of the first element in the snippet.  */
	public int getStartingPos() {
		return this.startOfSnippet;
	}

	/* This method returns the index of the last element in the snippet. */
	public int getEndingPos() {
		return this.endOfSnippet;
	}

	/* This method returns the size of the snippet. */
	public int getLength() {
		return this.minimumSnippetSize;
	}

	/* This method returns the position of the search term in the document. */
	public int getPos(int index) {
		int position = 0;
		for(int i = this.startOfSnippet; i <= this.endOfSnippet; i++) {
			if(terms.get(index).equals(this.document.get(i))) {
				position = i;
			}
		}
		return position;
	}

}
