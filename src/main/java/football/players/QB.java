package football.players;

import java.util.Set;
import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.categories.Pass;
import football.stats.categories.Rush;
import football.stats.categories.Misc;
import football.util.PlayerUtils;

import static football.util.ValidateUtils.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtils.checkArrayLength;

public final class QB extends Player
{
	private static final int[] statTypeSizes = {Pass.size(),Rush.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtils.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtils.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private final Set<Stat<Pass>> passStats;
	private final Set<Stat<Rush>> rushStats;
	private final Set<Stat<Misc>> miscStats;

	//stats ordered: comp, inc, yds, td, inter, sck
	public QB(String name, double defaultScore, Set<Stat<Pass>> passStats, Set<Stat<Rush>> rushStats, Set<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.passStats = new LinkedHashSet<Stat<Pass>>(passStats);
		this.rushStats = new LinkedHashSet<Stat<Rush>>(rushStats);
		this.miscStats = new LinkedHashSet<Stat<Misc>>(miscStats);
		checkStatsSetNotNullWithCorrectSize(this.passStats,Pass.class);
		checkStatsSetNotNullWithCorrectSize(this.rushStats,Rush.class);
		checkStatsSetNotNullWithCorrectSize(this.miscStats,Misc.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public QB(QB other) {
		this(other.name, other.score, other.passStats, other.rushStats, other.miscStats);
	}

	public Set<Stat<Pass>> getPassStats() {
		return new LinkedHashSet<Stat<Pass>>(passStats);
	}

	public Set<Stat<Rush>> getRushStats() {
		return new LinkedHashSet<Stat<Rush>>(rushStats);
	}

	public Set<Stat<Misc>> getMiscStats() {
		return new LinkedHashSet<Stat<Misc>>(miscStats);
	}

	@Override
	public QB deepCopy() {
		return new QB(this);
	}

	@Override
	public double evaluate(RuleMap rules) {
		//checkNotNull(rules, "rules is null");
		score = (PlayerUtils.dot(passStats, rules) + PlayerUtils.dot(rushStats, rules) + PlayerUtils.dot(miscStats, rules));
		return score;
	}

	public static RuleMap parseScoringRules(String[] args) {
		checkNotNull(args, "args is null");
		int numArgs = getNumStats()+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		RuleMap passRules = PlayerUtils.parseScoringRules(args, 1, statTypeIdxLimits[0], Pass.class);
		RuleMap rushRules = PlayerUtils.parseScoringRules(args, statTypeIdxLimits[0] + 1, statTypeIdxLimits[1], Rush.class);
		RuleMap miscRules = PlayerUtils.parseScoringRules(args, statTypeIdxLimits[1] + 1, statTypeIdxLimits[2], Misc.class);
		//combine rule maps
		RuleMap rules = new RuleMap();
		rules.putAll(passRules);
		rules.putAll(rushRules);
		rules.putAll(miscRules);
		return rules;
	}

	public static int getNumStats() {
		return numStats;
	}

	@Override
	public String statsToString() {
		String delimiter = "\t";
		return (PlayerUtils.statsToString(passStats) + delimiter + PlayerUtils.statsToString(rushStats) +
				delimiter + PlayerUtils.statsToString(miscStats));
	}
}
