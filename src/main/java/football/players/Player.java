package football.players;

import java.util.Objects;
import java.text.DecimalFormat;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.stats.RuleMap;

/*
 * Class representing a fantasy football player.
 * A player has a name, as a unique identifier, and a score, representing
 * their fantasy value given a set of fantasy scoring rules.
 */

public abstract class Player implements Comparable<Player>
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private static final DecimalFormat SCORE_FORMAT = new DecimalFormat(".00");

	protected final String name; //uniquely identifies player
	protected double score; //calculated and stored by evaluate

	public Player(String name)
	{
		checkNotNull(name, "name is null");
		checkArgument(!name.equals(""), "name is empty");
		this.name = name;
		this.score = 0;
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

	//players considered equal if their names match
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
			logger.warn("either {} or {} may not have been evaluated", name, other.name);
		}
		//compare scores
		int scoreCmpResult = Double.compare(this.score,other.score);
		//compare names iff scores are equal
		return (scoreCmpResult == 0 ? this.name.compareTo(other.name) : scoreCmpResult);
	}

	@Override
	public String toString() {
		return String.format("%-15s %-8s",name,SCORE_FORMAT.format(score));
	}

	// returns all player's stat values
	public abstract String statsToString();
}
