package football.core;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.checkArgument;

import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

/*
 * Object for storing results from scoring players, as well as 
 * the inputs which created those results.
 * Stores a list of both RuleMap's and Player lists. players.get(i) contains Player's
 * whose scores are the result of scoring each Player in the list with the Rule's in rules.get(i).
 */

public abstract class ScoringResults
{
	// inputs
	protected final Mode mode;
	protected final List<RuleMap> rules;

	// outputs
	protected final List<List<Player>> players;
	//TODO: not applicable for SimpleScoringResults
	protected final double distance;

	public ScoringResults(Mode mode, List<RuleMap> rules, List<List<Player>> players, double distance) {
		checkArgument(rules.size() == players.size(), "Number of RuleMap's %s does not equal number of Player lists %s", rules.size(), players.size());
		this.mode = mode;
		this.rules = new ArrayList<RuleMap>(rules);
		this.players = new ArrayList<List<Player>>(players);
		this.distance = distance;
	}

	public Mode getMode() {
		return mode;
	}

	public List<RuleMap> getRules() {
		return new ArrayList<RuleMap>(rules);
	}

	public List<List<Player>> getPlayers() {
		return new ArrayList<List<Player>>(players);
	}

	public double getDistance() {
		return distance;
	}
}
