package ir.ai.algorithms;

import ir.ai.util.DocObject;
import ir.ai.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class PassageTermMatching {

	private String[] keywords;
	private ArrayList<DocObject> docList;
	private double numOfFiles = 0;

	public PassageTermMatching(String[] keywords, ArrayList<DocObject> docList) {
		this.keywords = keywords;
		this.docList = docList;
		this.numOfFiles = docList.size();
	}

	public Map<String, ArrayList<DocObject>> getResults() {
		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		for (String keyword : this.keywords) {
			keyword = keyword.toLowerCase();
			for (DocObject doc : this.docList) {
				calculateScore(keyword, doc);
			}
			result.put(keyword, Util.retrieveTop10Documents(this.docList));
		}
		return result;
	}

	public void calculateScore(String keyword, DocObject doc) {
		String content = doc.getContent().toLowerCase();
		double wij = 0.0;
		double idftk = 0.0;
		
		String[] terms = keyword.split(" ");
		for (String term: terms){
			double idfValue = this.idf(term, doc);
			if (content.contains(term)){
				wij += idfValue;
			}
			idftk += idfValue;
		}
		
		double score = wij / idftk;
		doc.setScore(score);
		this.updateDoc(doc);
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
	
	public void updateDoc(DocObject doc){
		for(int i=0; i<this.docList.size(); i++){
			DocObject d = this.docList.get(i);
			if (d.getName().equals(doc.getName()))
				this.docList.set(i, doc);
		}
	}

}
