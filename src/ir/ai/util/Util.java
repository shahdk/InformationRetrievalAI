package ir.ai.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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
			top10.add(docList.get(i));
			i++;
		}
		return top10;
	}
}
