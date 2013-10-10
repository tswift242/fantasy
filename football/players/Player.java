package football.players;

import java.util.LinkedHashSet;
import java.text.DecimalFormat;

import football.stats.Stat;

public abstract class Player implements Comparable<Player>
{
	private final DecimalFormat scoreFmt = new DecimalFormat(".##"); //static

	protected String name;
	protected double score; //calculated and stored by evaluate

	public Player(String name, double defaultScore)
	{
		this.name = name;
		score = defaultScore;
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
}
