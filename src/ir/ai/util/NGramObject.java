package ir.ai.util;

public class NGramObject {

	private String nGram;
	private double score;
	
	public NGramObject(String nGram){
		this.nGram = nGram.toLowerCase();
		this.score = 0.0;
	}
	
	public double getScore(String words){
		String[] wordList = words.toLowerCase().split(" ");
		for (String word: wordList){
			if (this.nGram.contains(word))
				score++;
		}
		
		for (int i=0; i<wordList.length-1; i++){
			for (int j=i+1; j<wordList.length; j++){
				String phrase = wordList[i]+" "+wordList[j];
				if (this.nGram.contains(phrase))
					score+=2;
			}
		}
		
		return score;
	}
}
