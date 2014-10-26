package ir.ai.algorithms;

import ir.ai.util.DocObject;
import ir.ai.util.NGramObject;
import ir.ai.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class NGram {

	private String[] keywords;
	private ArrayList<DocObject> docList;
	
	public NGram(String[] keywords, ArrayList<DocObject> docList){
		this.keywords = keywords;
		this.docList = docList;
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
	
	private void calculateScore(String words, DocObject doc) {
		double score = 0.0;
		
		String[] content = doc.getContent().split(" "); 
		for (int i=0; i<content.length-5; i++){
			String fiveGram = content[i] + " " + content[i+1] + " " + content[i+2] + " " + content[i+3] + " " + content[i+4];
			NGramObject ngram = new NGramObject(fiveGram);
			score += ngram.getScore(words);
		}
		
		doc.setScore(score);
		this.updateDoc(doc);
	}
	
	public void updateDoc(DocObject doc){
		for(int i=0; i<this.docList.size(); i++){
			DocObject d = this.docList.get(i);
			if (d.getName().equals(doc.getName()))
				this.docList.set(i, doc);
		}
	}
	
}
