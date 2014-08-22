package football.core;

import java.util.List;

import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

/*
 * Object for storing results from scoring players, as well as 
 * the inputs which created those results.
 */

public class ScoringResults
{
	// inputs
	private Mode mode;
	private RuleMap rules;

	// outputs
	// players1 -> default rules; players2 -> custom rules "rules"
	//TODO: remove one of these lists, or add second RuleMap above
	private List<Player> defaultPlayers, customPlayers;
	private double distance;

	public ScoringResults(Mode mode, RuleMap rules, List<Player> defaultPlayers, List<Player> customPlayers, double distance) {
		this.mode = mode;
		this.rules = rules;
		this.defaultPlayers = defaultPlayers;
		this.customPlayers = customPlayers;
		this.distance = distance;
	}

	public Mode getMode() {
		return mode;
	}

	public RuleMap getRules() {
		return rules;
	}

	public List<Player> getDefaultPlayers() {
		return defaultPlayers;
	}

	public List<Player> getCustomPlayers() {
		return customPlayers;
	}

	public double getDistance() {
		return distance;
	}
}
