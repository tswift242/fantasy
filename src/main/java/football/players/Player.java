package football.players;

import java.util.Objects;
import java.util.LinkedHashSet;
import java.text.DecimalFormat;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Stat;

public abstract class Player implements Comparable<Player>
{
	private final DecimalFormat scoreFmt = new DecimalFormat(".##"); //static

	protected final String name; //uniquely identifies player
	protected double score; //calculated and stored by evaluate

	public Player(String name, double defaultScore)
	{
		checkNotNull(name, "name is null");
		checkArgument(!name.equals(""), "name is empty");
		this.name = name;
		this.score = defaultScore;
	}

	public String getName() {
		return name;
	}

	public double getScore() {
		return score;
	}

	//makes a deep copy of this player
	public abstract Player deepCopy();

	//TODO: remove since covered by method below??
	//evaluate player by assigning them a score
	public abstract double evaluate(double[] ... coeffs);

	//parse scoring coefficients from cmd line arguments and then evaluate player
	public abstract double parseScoringCoeffsAndEvaluate(String[] args);

	//players consider equal if their names match
	@Override
	public boolean equals(Object o) {
		if((o == null) || (this.getClass() != o.getClass())) {
			return false;
		}
		if(this == o) {
			return true;
		}
		Player other = (Player)o;
		return this.name.equals(other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override //implements
	public int compareTo(Player other) {
		checkNotNull(other, "other is null");
		if((this.score == 0) || (other.score == 0)) {
			System.out.println("Warning: either " + name + " or " + other.getName() + " may not have been evaluated");
		}
		return Double.compare(this.score,other.score);
	}

	@Override
	public String toString() {
		return String.format("%-15s %-8s",name,scoreFmt.format(score));
	}

	public abstract String categoriesToString();
}
