package ir.ai.algorithms;

import ir.ai.util.DocObject;
import ir.ai.util.NGramObject;
import ir.ai.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class SkipBiGram {

	private String[] keywords;
	private ArrayList<DocObject> docList;
	
	public SkipBiGram(String[] keywords, ArrayList<DocObject> docList){
		this.keywords = keywords;
		this.docList = docList;
	}
	
	public Map<String, ArrayList<DocObject>> getResults() {
		return null;
	}
	
}
