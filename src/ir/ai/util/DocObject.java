package ir.ai.util;

public class DocObject implements Comparable<DocObject> {
	private String name;
	private double score;
	
	public DocObject(String name, double score){
		this.name = name;
		this.score = score;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public double getScore() {
		return score;
	}
	public void setScore(double score) {
		this.score = score;
	}

	@Override
	public int compareTo(DocObject o) {
		return (int)(o.getScore() - this.getScore());
	}
	
	
}
