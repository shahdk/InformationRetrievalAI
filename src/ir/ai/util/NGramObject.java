package ir.ai.util;

public class NGramObject {

	private String fiveGram;
	private double score;
	
	public NGramObject(String fiveGram){
		this.fiveGram = fiveGram.toLowerCase();
		this.score = 0.0;
	}
	
	public double getScore(String words){
		String[] wordList = words.toLowerCase().split(" ");
		for (String word: wordList){
			if (this.fiveGram.contains(word))
				score++;
		}
		
		for (int i=0; i<wordList.length-1; i++){
			for (int j=i+1; j<wordList.length; j++){
				String phrase = wordList[i]+" "+wordList[j];
				System.out.println(phrase);
				if (this.fiveGram.contains(phrase))
					score++;
			}
		}
		
		return score;
	}
}
