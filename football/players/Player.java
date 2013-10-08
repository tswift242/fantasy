package football.players;

import java.util.LinkedHashSet;
import java.text.DecimalFormat;

import football.stats.Stat;

public abstract class Player implements Comparable<Player>
{
	private final DecimalFormat scoreFmt = new DecimalFormat(".##"); //static

	protected String name;
	protected double score; //calculated and stored by evaluate

	public Player(String name)
	{
		this.name = name;
		score = 0.0; //initial value
	}

	public String getName() {
		return name;
	}

	//TODO: remove since covered by method below??
	//evaluate player by assigning them a score
	public abstract double evaluate(double[] ... coeffs);

	//parse scoring coefficients from cmd line arguments and then evaluate player
	public abstract double parseScoringCoeffsAndEvaluate(String[] args);

	@Override //implements
	public int compareTo(Player other) {
		if((this.score == 0) || (other.score == 0)) {
			System.out.println("Warning: either " + name + " or " + other.getName() + " may not have been evaluated");
		}
		return Double.compare(this.score,other.score);
	}

	@Override
	public String toString() {
		return (name + "\t\t" + scoreFmt.format(score));
	}

	//TODO: get rid of this and use enum.toString()
	public abstract String categoriesToString();

	//TODO: move this method and two that follow in Utils.java and make static
	//utility helper function for evalute()
	protected <T extends Enum<T>> double dot(LinkedHashSet<Stat<T>> stats, double[] coeffs) {
		if(stats.size() != coeffs.length) {
			System.out.println("Error: inputs don't have same length");
			System.exit(1);
		}

		double sum = 0.0;
		int i = 0;
		for(Stat<T> stat : stats) {
			sum += (stat.getValue()*coeffs[i]);
			i++;
		}
		return sum;
	}

	//utility helper function for parseScoringCoeffsAndEvaluate()
	//Parses elements in args between startIdx and endIdx (inclusive) into doubles and returns them in an array
	protected double[] parseScoringCoeffs(String[] args, int startIdx, int endIdx) {
		double[] coeffs = new double[endIdx-startIdx+1];
		for(int i = startIdx; i <= endIdx; i++) {
			coeffs[i-startIdx] = Double.parseDouble(args[i]);
		}
		return coeffs;
	}

	//utility helper function for parseScoringCoeffsAndEvaluate()
	protected int[] cumsum(int[] a) {
		int length = a.length;
		int[] cumsum = new int[length];
		cumsum[0] = a[0];
		for(int i = 1; i < length; i++) {
			cumsum[i] = cumsum[i-1]+a[i];
		}
		return cumsum;
	}
}
