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

	private double k1 = 1.2;
	private double b = 0.75;
	private double numOfFiles = 0;
	private double avgLength = 0.0;

	private String[] keyowrds;
	private File[] files;

	public BM25(String[] keywords, String corpus) {
		this.keyowrds = keywords;
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
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		this.avgLength = sum / this.numOfFiles;
	}

	public double documentLength(String document) {
		double length = 0;
		try {
			String content = new String(Files.readAllBytes(Paths.get(document)));
			String[] words = content.split(" ");
			length = words.length;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return length;
	}

	public double termFrequency(String term, String document) {
		double frequency = 0;
		try {
			String content = new String(Files.readAllBytes(Paths.get(document)));
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

	public double idf(String term) {
		int containingDoc = 0;
		for (File file : this.files) {
			try {
				String content = new String(Files.readAllBytes(Paths.get(file
						.getAbsolutePath())));
				String[] words = content.split(" ");
				for (String word : words) {
					if (term.equals(word)) {
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
			for (String keyword : this.keyowrds) {
				String[] words = keyword.split(" ");
				ArrayList<DocObject> docList = new ArrayList<>();
				
				for (File file : this.files) {
					String document = file.getAbsolutePath();
					String content = new String(Files.readAllBytes(Paths
							.get(document)));
					String[] w = content.split(" ");
					double docLen = w.length;
					
					double score = 0.0;
					
					for (String term : words) {
						double inverseDocumentFrequency = this.idf(term);
						double frequency = this.termFrequency(term, document);
						double numerator = frequency * (this.k1 + 1);
						double denominator = frequency
								+ (this.k1 * (1 - this.b + (this.b * docLen / this.avgLength)));
						score += inverseDocumentFrequency * numerator / denominator;
					}
					DocObject doc = new DocObject(file.getName(), score);
					docList.add(doc);
				}
				
				Collections.sort(docList, new Comparator<DocObject>() {
			        @Override public int compare(DocObject p1, DocObject p2) {
			            if (p1.getScore() < p2.getScore())
			            	return 1;
			            else if (p1.getScore() > p2.getScore())
			            	return -1;
			        	return 0;
			        }

			    });
				int i = docList.size() - 1;
				while (i>=10){
					docList.remove(i);
					i--;
				}
				result.put(keyword, docList);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
