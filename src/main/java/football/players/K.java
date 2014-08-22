package football.players;

import java.util.Set;
import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.categories.Kick;
import football.util.PlayerUtils;

import static football.util.ValidateUtils.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtils.checkArrayLength;

public final class K extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private final Set<Stat<Kick>> kickStats;

	//stats ordered: pat made/miss, fg made, fg miss
	public K(String name, double defaultScore, Set<Stat<Kick>> kickStats)
	{
		super(name, defaultScore);
		this.kickStats = new LinkedHashSet<Stat<Kick>>(kickStats);
		checkStatsSetNotNullWithCorrectSize(this.kickStats,Kick.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public K(K other) {
		this(other.name, other.score, other.kickStats);
	}

	public Set<Stat<Kick>> getKickStats() {
		return new LinkedHashSet<Stat<Kick>>(kickStats);
	}

	@Override
	public K deepCopy() {
		return new K(this);
	}

	@Override
	public double evaluate(RuleMap rules) {
		//checkNotNull(rules, "rules is null");
		score = PlayerUtils.dot(kickStats, rules);
		return score;
	}

	public static RuleMap parseScoringRules(String[] args) {
		checkNotNull(args, "args is null");
		int numKickStats = getNumStats();
		int numArgs = numKickStats+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		RuleMap rules = PlayerUtils.parseScoringRules(args, 1, numKickStats, Kick.class);
		return rules;
	}

	public static int getNumStats() {
		return Kick.size();
	}

	@Override
	public String categoriesToString() {
		return Kick.valuesToString();
	}

	@Override
	public String statsToString() {
		return PlayerUtils.statsToString(kickStats);
	}
}
