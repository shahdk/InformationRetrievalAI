package ir.ai.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import ir.ai.algorithms.*;
import ir.ai.util.DocObject;
import ir.ai.util.DocParser;

public class Main {

	private static String[] keywords = new String[] { "Adams", "Lincoln", "president",
			"assassinated president", "great president", "first president",
			"civil war president", "youngest", "watergate" };
	
	private static String corpus = "Presidents";
	
	private static void printResult(Map<String, ArrayList<DocObject>> result){
		for (String keyword: result.keySet()){
			System.out.println("\t"+keyword+" :");
			for (DocObject document: result.get(keyword)){
				System.out.println("\t\t"+document.getName());
			}
		}
	}

	public static void main(String[] args) {
		
		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		
		DocParser parser = new DocParser();
		ArrayList<DocObject> docList = parser.getDocList(corpus);
		
		//BM25
		BM25 bm25 = new BM25(keywords, docList);
		result = bm25.getResults();
		System.out.println("BM25");
		printResult(result);
		
		
//		//Skip Bi Grams
//		System.out.println("Skip Bi-grams");
//		SkipBiGram skipBiGram = new SkipBiGram(keywords, corpus);
//		result = skipBiGram.getResults();
//		printResult(result);
//		
//		//N-grams
//		System.out.println("N-grams");
//		NGram nGram = new NGram(keywords, corpus);
//		result = nGram.getResults();
//		printResult(result);
//		
//		//Passage term matching
//		System.out.println("Passage Term Matching");
//		PassageTermMatching passageTermMatching = new PassageTermMatching(keywords, corpus);
//		result = passageTermMatching.getResults();
//		printResult(result);
//		
//		//Textual alignment
//		System.out.println("Textual Alignment");
//		TextualAlignment textualAlignment = new TextualAlignment(keywords, corpus);
//		result = textualAlignment.getResults();
//		printResult(result);
	}

}
