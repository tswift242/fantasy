package football.players;

import java.util.Objects;
import java.text.DecimalFormat;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.RuleMap;

/*
 * Class representing a fantasy football player.
 * A player has a name, as a unique identifier, and a score, representing
 * their fantasy value given a set of fantasy scoring rules.
 */

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

	//evaluate player by assigning them a score
	public abstract double evaluate(RuleMap rules);

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
		return this.name.equalsIgnoreCase(other.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}

	@Override //implements
	public int compareTo(Player other) {
		checkNotNull(other, "other is null");
		if((this.score == 0) || (other.score == 0)) {
			System.out.println("Warning: either " + name + " or " + other.name + " may not have been evaluated");
		}
		//compare scores
		int scoreCmpResult = Double.compare(this.score,other.score);
		//compare names iff scores are equal
		return (scoreCmpResult == 0 ? this.name.compareTo(other.name) : scoreCmpResult);
	}

	@Override
	public String toString() {
		return String.format("%-15s %-8s",name,scoreFmt.format(score));
	}

	public abstract String categoriesToString();
}
