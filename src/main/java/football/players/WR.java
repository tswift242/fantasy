package football.players;

import java.util.Set;
import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.categories.Rec;
import football.stats.categories.Misc;
import football.util.PlayerUtils;

import static football.util.ValidateUtils.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtils.checkArrayLength;

public final class WR extends Player
{
	private static final int[] statTypeSizes = {Rec.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtils.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtils.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private final Set<Stat<Rec>> recStats;
	private final Set<Stat<Misc>> miscStats;

	//stats ordered: rec, yds, td
	public WR(String name, double defaultScore, Set<Stat<Rec>> recStats, Set<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.recStats = new LinkedHashSet<Stat<Rec>>(recStats);
		this.miscStats = new LinkedHashSet<Stat<Misc>>(miscStats);
		checkStatsSetNotNullWithCorrectSize(this.recStats,Rec.class);
		checkStatsSetNotNullWithCorrectSize(this.miscStats,Misc.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public WR(WR other) {
		this(other.name, other.score, other.recStats, other.miscStats);
	}

	public Set<Stat<Rec>> getRecStats() {
		return new LinkedHashSet<Stat<Rec>>(recStats);
	}

	public Set<Stat<Misc>> getMiscStats() {
		return new LinkedHashSet<Stat<Misc>>(miscStats);
	}

	@Override
	public WR deepCopy() {
		return new WR(this);
	}

	@Override
	public double evaluate(RuleMap rules) {
		//checkNotNull(rules, "rules is null");
		score = (PlayerUtils.dot(recStats, rules) + PlayerUtils.dot(miscStats, rules));
		return score;
	}

	public static RuleMap parseScoringRules(String[] args) {
		checkNotNull(args, "args is null");
		int numArgs = getNumStats()+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		RuleMap recRules = PlayerUtils.parseScoringRules(args, 1, statTypeIdxLimits[0], Rec.class);
		RuleMap miscRules = PlayerUtils.parseScoringRules(args, statTypeIdxLimits[0] + 1, statTypeIdxLimits[1], Misc.class);
		//combine rule maps
		RuleMap rules = new RuleMap();
		rules.putAll(recRules);
		rules.putAll(miscRules);
		return rules;
	}

	public static int getNumStats() {
		return numStats;
	}

	@Override
	public String categoriesToString() {
		return (Rec.valuesToString() + "\t\t" + Misc.valuesToString());
	}

	@Override
	public String statsToString() {
		return (PlayerUtils.statsToString(recStats) + "\t" + PlayerUtils.statsToString(miscStats));
	}
}
