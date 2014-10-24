package ir.ai.algorithms;

import ir.ai.util.DocObject;

import java.util.ArrayList;
import java.util.Map;

public class SkipBiGram {

	private String[] keywords;
	private String corpus;
	
	public SkipBiGram(String[] keywords, String corpus){
		this.keywords = keywords;
		this.corpus = corpus;
	}
	
	public Map<String, ArrayList<DocObject>> getResults() {
		return null;
	}
}
