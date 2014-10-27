package ir.ai.algorithms;

import ir.ai.util.DocObject;
import ir.ai.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class TextualAlignment {

	private String[] keywords;
	private ArrayList<DocObject> docList;
	private double numOfFiles = 0;
	private Map<String, Integer> docFrequencyMap;

	public TextualAlignment(String[] keywords, ArrayList<DocObject> docList, Map<String, Integer> docFrequencyMap) {
		this.keywords = keywords;
		this.docList = docList;
		this.docFrequencyMap = docFrequencyMap;
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
		String[] q = keyword.split(" ");
		String[] p = doc.getContent().toLowerCase().split(" ");
		double[][] scores = new double[p.length][q.length];

		for (int i = 1; i < p.length; i++) {
			for (int j = 0; j < q.length; j++) {
				double score1 = 0.0;
				double score2 = 0.0;
				double score3 = 0.0;
				double score4 = 0.0;

				if (j==0 && q.length > 1){
					j = 1;
				}
				score2 = scores[i - 1][j] + sim(p[i], "", doc);
				if (q.length > 1){
					score1 = scores[i-1][j - 1] + sim(p[i], q[j], doc);
					score3 = scores[i][j - 1] + sim("", q[j], doc);
				} else {
					score1 = scores[i-1][j] + sim(p[i], q[j], doc);
					score3 = scores[i][j] + sim("", q[j], doc);
				}
				scores[i][j] = Math.max(
						Math.max(Math.max(score1, score2), score3), score4);
			}
		}

		double score = 0.0;
		for (int i = 0; i < p.length; i++) {
			for (int j = 0; j < q.length; j++) {
				score += scores[i][j];
			}
		}
		doc.setScore(score);
		this.updateDoc(doc);
	}

	public double sim(String t1, String t2, DocObject doc) {
		double result = 0.0;

		if (t1.toLowerCase().equals(t2.toLowerCase())) {
			result = this.idf(t1, doc);
		} else if (t1.equals("") || t1 == null) {
			result = -1.0 * this.idf(t2, doc);
		} else {
			result = -1.0 * this.idf(t1, doc);
		}

		return result;
	}

	public double idf(String term, DocObject doc) {
		int containingDoc = this.docFrequencyMap.get(term);
		double denominator = containingDoc + 1.0;
		return Math.log(this.numOfFiles / denominator);
	}

	public void updateDoc(DocObject doc) {
		for (int i = 0; i < this.docList.size(); i++) {
			DocObject d = this.docList.get(i);
			if (d.getName().equals(doc.getName()))
				this.docList.set(i, doc);
		}
	}

}
