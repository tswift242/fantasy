import java.text.DecimalFormat;

public class Player implements Comparable<Player>
{
	protected String name;
	protected int[] stats;
	protected double score; //calculated and stored by evaluate

	public Player(String name, int[] stats)
	{
		if(stats.length != getNumStats()) {
			System.out.println("Error: input stats array has length " + stats.length + " instead of " + getNumStats());
			System.exit(1);
		}
		this.name = name;
		this.stats = stats;
		score = 0.0; //initial value
	}

	//override in subclasses -- used for error checking stats in constructor
	public int getNumStats() {
		return 0;
	}

	//TODO: get rid of and define in subclasses, or use variable arguments-- make class which couples stats and coeffs arrays??
	//evaluate player by assigning them a score
	public double evaluate(double[] coeffs) {
		score = dot(stats,coeffs);
		return score;
	}

	public int compareTo(Player other) {
		if((this.score == 0) || (other.score == 0)) {
			System.out.println("Warning: one of these players may not have been evaluated");
		}
		return Double.compare(this.score,other.score);
	}

	public String toString() {
		DecimalFormat dfmt = new DecimalFormat(".##");
		return (name + "\t\t" + dfmt.format(score));
	}

	//override in subclasses
	public String statsCats() {
		return "";
	}



	//helper function for evalute()
	protected double dot(int[] a, double[] b) {
		if(a.length != b.length) {
			System.out.println("Error: arrays don't have same length");
			System.exit(1);
		}

		double sum = 0.0;
		for(int i = 0; i < a.length; i++) {
			sum += (a[i]*b[i]);
		}
		return sum;
	}

	//TODO: eliminate or move to separate class
	public static class stat
	{
		protected int value;
	}
}
