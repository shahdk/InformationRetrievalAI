package ir.ai.util;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

public class Util {

	public static ArrayList<DocObject> retrieveTop10Documents(ArrayList<DocObject> docList) {
		Collections.sort(docList, new Comparator<DocObject>() {
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
			DocObject cloned = docList.get(i);
			DocObject newObj = new DocObject(cloned.getName());
			newObj.setContent(cloned.getContent());
			newObj.setLength(cloned.getLength());
			newObj.setScore(cloned.getScore());
			top10.add(newObj);
			i++;
		}
		return top10;
	}
	
	public static Map<String, Integer> retrieveDocFrequency(ArrayList<DocObject> docList){
		Map<String, Integer> docFrequencyMap = new HashMap<>();
		int counter = 1;
		for (DocObject doc: docList){
			String[] content = doc.getContentArray();
			Arrays.sort(content);
			String[] words = removeDuplicates(content);
			for (String word: words){
				if (!docFrequencyMap.containsKey(word))
					docFrequencyMap.put(word, counter);
				else {
					counter = docFrequencyMap.get(word);
					docFrequencyMap.put(word, counter+1);
				}
			}
		}
		
		return docFrequencyMap;
	}
	
	public static String[] removeDuplicates(String[] original) {
		if (original.length < 2)
			return original;
	 
		int j = 0;
		int i = 1;
	 
		while (i < original.length) {
			if (original[i].equals(original[j])) {
				i++;
			} else {
				j++;
				original[j] = original[i];
				i++;
			}
		}
	 
		return Arrays.copyOf(original, j + 1);
	}
}
