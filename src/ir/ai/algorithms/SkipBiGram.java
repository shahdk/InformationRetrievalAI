package ir.ai.algorithms;

import ir.ai.util.DocObject;
import ir.ai.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkipBiGram {

	private String[] keywords;
	private ArrayList<DocObject> docList;

	public SkipBiGram(String[] keywords, ArrayList<DocObject> docList) {
		this.keywords = keywords;
		this.docList = docList;
	}

	public Map<String, ArrayList<DocObject>> getResults() {
		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		
		for (String keyword: this.keywords){
			keyword = keyword.toLowerCase();
			ArrayList<String> qSkipBiGram = this.generateSkipBiGramFor(keyword);
			for (DocObject doc : this.docList) {
				ArrayList<String> pSkipBiGram = this.generateSkipBiGramFor(doc.getContent());
				double commonElements = getCommonElements(qSkipBiGram, pSkipBiGram);
				double scoreP = commonElements / pSkipBiGram.size();
				double scoreQ = commonElements / qSkipBiGram.size();
				double score = 0.0;
				if (scoreP > 0  || scoreQ > 0)
					score = (2 * scoreP * scoreQ) / (scoreP + scoreQ);
				doc.setScore(score);
				this.updateDoc(doc);
			}
			result.put(keyword, Util.retrieveTop10Documents(this.docList));
		}
		
		
		return result;
	}

	public ArrayList<String> generateSkipBiGramFor(String text) {
		ArrayList<String> biGramList = new ArrayList<>();
		String[] words = text.toLowerCase().split(" ");
		
		if (words.length <= 2){
			biGramList.add(text.toLowerCase());
			return biGramList;
		}
		
		for (int i=0; i<words.length-1; i++){
			String biGram = words[i] + " " + words[i+1];
			biGramList.add(biGram);
			
			if ((i+2) < words.length){
				biGram = words[i] + " " + words[i+2];
				biGramList.add(biGram);
			}
			
		}
		
		return biGramList;
	}
	
	public double getCommonElements(ArrayList<String> qSkipBiGram, ArrayList<String> pSkipBiGram){
		double commonElements = 0.0;
		for (String q: qSkipBiGram){
			for (String p: pSkipBiGram){
				if (p.contains(q)){
					commonElements++;
				}
			}
		}
		return commonElements;
	}
	
	public void updateDoc(DocObject doc){
		for(int i=0; i<this.docList.size(); i++){
			DocObject d = this.docList.get(i);
			if (d.getName().equals(doc.getName()))
				this.docList.set(i, doc);
		}
	}
}
