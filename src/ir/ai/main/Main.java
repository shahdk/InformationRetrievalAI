package ir.ai.main;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.UIManager;
import ir.ai.algorithms.*;
import ir.ai.gui.DisplayTable;
import ir.ai.gui.FileChooser;
import ir.ai.util.DocObject;
import ir.ai.util.DocParser;

public class Main {

	private String[] keywords = new String[] { "Adams", "Lincoln", "president",
			"assassinated president", "great president", "first president",
			"civil war president", "youngest", "watergate" };
	
	private void printResult(Map<String, ArrayList<DocObject>> result, String algorithm){
		
		String[][] tableValues = new String[20][this.keywords.length];
		String[][] scores = new String[20][this.keywords.length];

		for (int j=0; j<this.keywords.length; j++){
			int i=0;
			String keyword = this.keywords[j].toLowerCase();
			for (DocObject document: result.get(keyword)){
				tableValues[i][j] = document.getName();
				scores[i][j] = String.valueOf(document.getScore());
				i++;
				tableValues[i][j] = "";
				scores[i][j] = "";
				i++;
			}
		}
		
		DisplayTable table = new DisplayTable(tableValues, algorithm, this.keywords, scores);
		table.setVisible(true);
	}
	
	public void runAlgorithmFor(String algorithm, String corpus){
		
		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		DocParser parser = new DocParser();
		ArrayList<DocObject> docList = parser.getDocList(corpus);
		
		switch(algorithm.toLowerCase()){
		case "bm25":
			BM25 bm25 = new BM25(keywords, docList);
			result = bm25.getResults();
			break;
		case "skip bi-grams":
			SkipBiGram skipBiGram = new SkipBiGram(keywords, docList);
			result = skipBiGram.getResults();
			break;
		case "n-grams":
			NGram nGram = new NGram(keywords, docList);
			result = nGram.getResults();
			break;
		case "passage term matching":
			PassageTermMatching passageTermMatching = new PassageTermMatching(keywords, docList);
			result = passageTermMatching.getResults();
			break;
		case "textual alignment":
			TextualAlignment textualAlignment = new TextualAlignment(keywords, docList);
			result = textualAlignment.getResults();
			break;
		default:
			System.out.println("Unknown Algorithm");
			break;
		}
		printResult(result, algorithm);
	}

	public static void main(String[] args) {
		
		try {
			UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
	    } 
	    catch (Exception e) {
	    	e.printStackTrace();
	    }
	    
		
		FileChooser fc = new FileChooser(new Main());
		fc.setVisible(true);
	}

}
