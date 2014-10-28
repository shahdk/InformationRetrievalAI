package ir.ai.main;

import java.awt.Color;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.swing.BorderFactory;
import javax.swing.UIManager;
import javax.swing.plaf.BorderUIResource;
import javax.swing.plaf.ColorUIResource;

import ir.ai.algorithms.*;
import ir.ai.gui.DisplayTable;
import ir.ai.gui.FileChooser;
import ir.ai.util.DocObject;
import ir.ai.util.DocParser;
import ir.ai.util.Util;

public class Main {

	private String[] keywords = new String[] { "Adams", "Lincoln", "president",
			"assassinated president", "great president", "first president",
			"civil war president", "youngest president", "watergate scandal",
			"first black president", "cuban missile crisis", "terrorist attack", "founding father",
			"president who died of pneumonia", "budget surplus", "no child left behind", "bush tax cuts", "president with Poliomyelitis" };

	private void printResult(Map<String, ArrayList<DocObject>> result,
			String algorithm) {

		String[][] tableValues = new String[20][this.keywords.length];
		String[][] scores = new String[20][this.keywords.length];

		for (int j = 0; j < this.keywords.length; j++) {
			int i = 0;
			String keyword = this.keywords[j].toLowerCase();
			for (DocObject document : result.get(keyword)) {
				tableValues[i][j] = document.getName();
				scores[i][j] = String.valueOf(document.getScore());
				i++;
				tableValues[i][j] = "";
				scores[i][j] = "";
				i++;
			}
		}

		DisplayTable table = new DisplayTable(tableValues, algorithm,
				this.keywords, scores);
		table.setVisible(true);
	}

	public void runAlgorithmFor(String algorithm, String corpus) {

		Map<String, ArrayList<DocObject>> result = new HashMap<>();
		DocParser parser = new DocParser();
		ArrayList<DocObject> docList = parser.getDocList(corpus);
		Map<String, Integer> docFrequencyMap = Util
				.retrieveDocFrequency(docList);

		switch (algorithm.toLowerCase()) {
		case "bm25":
			BM25 bm25 = new BM25(keywords, docList, docFrequencyMap);
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
			PassageTermMatching passageTermMatching = new PassageTermMatching(
					keywords, docList, docFrequencyMap);
			result = passageTermMatching.getResults();
			break;
		case "textual alignment":
			TextualAlignment textualAlignment = new TextualAlignment(keywords,
					docList, docFrequencyMap);
			result = textualAlignment.getResults();
			break;
		default:
			System.out.println("Unknown Algorithm");
			break;
		}
		printResult(result, algorithm);
	}

	public static void main(String[] args) {

		// try {
		// UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
		// UIManager.put("Table.background", Color.BLACK);
		// UIManager.put("Table.foreground", Color.WHITE);
		// UIManager.put("Table.alternateRowColor", Color.GRAY);
		// }
		// catch (Exception e) {
		// e.printStackTrace();
		// }

		try {
			// UIManager.setLookAndFeel("com.seaglasslookandfeel.SeaGlassLookAndFeel");
			// for (LookAndFeelInfo info : UIManager.getInstalledLookAndFeels())
			// {
			// if ("Nimbus".equals(info.getName())) {
			// UIManager.setLookAndFeel(info.getClassName());
			// break;
			// }
			// }
			UIManager.setLookAndFeel("com.birosoft.liquid.LiquidLookAndFeel");
			UIManager.put("Table.alternateRowColor", Color.GRAY);
			UIManager.put("Table.cellNoFocusBorder", new BorderUIResource(
					BorderFactory.createMatteBorder(0, 2, 0, 2, Color.GREEN)));
			UIManager.getLookAndFeelDefaults().put("Table.foreground",
					Color.WHITE);
			UIManager.getLookAndFeelDefaults().put("Table.background",
					new ColorUIResource(Color.BLACK));

		} catch (Exception e) {
			// If Nimbus is not available, you can set the GUI to another look
			// and feel.
			e.printStackTrace();
		}

		FileChooser fc = new FileChooser(new Main());
		fc.setVisible(true);
	}

}
