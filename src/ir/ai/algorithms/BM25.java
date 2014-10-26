package ir.ai.algorithms;

import ir.ai.util.DocObject;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class BM25 {

	private double k1 = 2.0;
	private double b = 0.75;
	private double numOfFiles = 0;
	private double avgLength = 0.0;
	private String[] keywords;
	private File[] files;
	private ArrayList<DocObject> docList;

	public BM25(String[] keywords, String corpus) {
		this.keywords = keywords;
		this.docList = new ArrayList<>();
		setNumOfFiles(corpus);
		setAverageDocumentLength();
	}

	public void setNumOfFiles(String path) {
		this.files = new File(path).listFiles();
		this.numOfFiles = this.files.length;
	}

	public void setAverageDocumentLength() {
		double sum = 0;
		for (File file : this.files) {
			try {
				String content = new String(Files.readAllBytes(Paths.get(file
						.getAbsolutePath())));
				String[] words = content.split(" ");
				sum += words.length;
				DocObject doc = new DocObject(file.getName());
				doc.setLength(words.length);
				doc.setContent(content);
				this.docList.add(doc);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.avgLength = sum / this.numOfFiles;
	}
	
	public DocObject getDoc(String name){
		for (DocObject d: this.docList){
			if (d.getName().equals(name))
				return d;
		}
		System.out.println(name);
		return null;
	}
	
	public void updateDoc(DocObject doc){
		for(int i=0; i<this.docList.size(); i++){
			DocObject d = this.docList.get(i);
			if (d.getName().equals(doc.getName()))
				this.docList.set(i, doc);
		}
	}

	public double termFrequency(String term, String document) {
		double frequency = 0;
		try {
			String content = this.getDoc(document).getContent();
			String[] words = content.split(" ");
			for (int i = 0; i < words.length; i++) {
				term = term.toLowerCase();
				String word = words[i].toLowerCase();
				if (term.equals(word)) {
					frequency++;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return frequency;
	}

	public double idf(String term, DocObject doc) {
		int containingDoc = 0;
		for (File file : this.files) {
			try {
				String content = this.getDoc(file.getName()).getContent();
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
		double numerator = this.numOfFiles - containingDoc + 0.5;
		double denominator = containingDoc + 0.5;
		return Math.log(numerator / denominator);
	}

	public Map<String, ArrayList<DocObject>> getResults() {
		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		try {
			for (String keyword : this.keywords) {
				keyword = keyword.toLowerCase();
				String[] words = keyword.split(" ");
				
				for (File file : this.files) {
					String document = file.getName();
					calculateScore(words, document);
				}
				
				result.put(keyword, retrieveTop10Documents());
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}

	private ArrayList<DocObject> retrieveTop10Documents() {
		Collections.sort(this.docList, new Comparator<DocObject>() {
		    @Override public int compare(DocObject p1, DocObject p2) {
		        if (p1.getScore() < p2.getScore())
		        	return 1;
		        else if (p1.getScore() > p2.getScore())
		        	return -1;
		    	return 0;
		    }

		});
		
		ArrayList<DocObject> top10 = new ArrayList<>();
		int i = 0;
		while (i<=9){
			top10.add(this.docList.get(i));
			i++;
		}
		return top10;
	}

	private void calculateScore(String[] words, String document) {
		double score = 0.0;
		DocObject doc = this.getDoc(document);
		for (String term : words) {
			double inverseDocumentFrequency = this.idf(term, doc);
			double frequency = this.termFrequency(term, document);
			double numerator = frequency * (this.k1 + 1);
			double denominator = frequency
					+ (this.k1 * (1 - this.b + (this.b * doc.getLength() / this.avgLength)));
			score += inverseDocumentFrequency * numerator / denominator;
		}
		doc.setScore(score);
		this.updateDoc(doc);
	}
}
