package football.players;

import java.util.Set;
import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.categories.Def;
import football.util.PlayerUtils;

import static football.util.ValidateUtils.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtils.checkArrayLength;

public final class DEF extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private final Set<Stat<Def>> defStats;

	public DEF(String name, double defaultScore, Set<Stat<Def>> defStats)
	{
		super(name, defaultScore);
		this.defStats = new LinkedHashSet<Stat<Def>>(defStats);
		checkStatsSetNotNullWithCorrectSize(this.defStats,Def.class);
		//TODO: check all elements of set are non-null?
	}

	//copy constructor. Note: does not copy stat sets.
	public DEF(DEF other) {
		this(other.name, other.score, other.defStats);
	}

	public Set<Stat<Def>> getDefStats() {
		//TODO: return Collections.unmodifiableSet() if type changed from LinkedHashSet to Set
		return new LinkedHashSet<Stat<Def>>(defStats);
	}

	@Override
	public DEF deepCopy() {
		return new DEF(this);
	}

	@Override
	public double evaluate(RuleMap rules) {
		//checkNotNull(rules, "rules is null");
		score = PlayerUtils.dot(defStats, rules);
		return score;
	}

	public static RuleMap parseScoringRules(String[] args) {
		checkNotNull(args, "args is null");
		int numDefStats = getNumStats();
		int numArgs = numDefStats+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		RuleMap rules = PlayerUtils.parseScoringRules(args, 1, numDefStats, Def.class);
		return rules;
	}

	public static int getNumStats() {
		return Def.size();
	}

	@Override
	public String statsToString() {
		return PlayerUtils.statsToString(defStats);
	}
}
