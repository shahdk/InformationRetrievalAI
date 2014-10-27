package ir.ai.algorithms;

import ir.ai.util.DocObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class PassageTermMatching {

	private String[] keywords;
	private ArrayList<DocObject> docList;
	private double numOfFiles = 0;
	
	public PassageTermMatching(String[] keywords, ArrayList<DocObject> docList){
		this.keywords = keywords;
		this.docList = docList;
		this.numOfFiles = docList.size();
	}
	
	public Map<String, ArrayList<DocObject>> getResults() {
		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		return result;
	}
	
	public double idf(String term, DocObject doc) {
		int containingDoc = 0;
		for (DocObject document : this.docList) {
			try {
				String content = document.getContent();
				String[] words = content.split(" ");
				for (String word : words) {
					if (term.toLowerCase().equals(word.toLowerCase())) {
						containingDoc++;
						break;
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		double denominator = containingDoc + 1.0;
		return Math.log(this.numOfFiles / denominator);
	}
	
}
