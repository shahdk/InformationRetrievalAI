package ir.ai.algorithms;

import ir.ai.util.DocObject;

import java.util.ArrayList;
import java.util.Map;


public class PassageTermMatching {

	private String[] keywords;
	private ArrayList<DocObject> docList;
	
	public PassageTermMatching(String[] keywords, ArrayList<DocObject> docList){
		this.keywords = keywords;
		this.docList = docList;
	}
	
	public Map<String, ArrayList<DocObject>> getResults() {
		return null;
	}
	
}
