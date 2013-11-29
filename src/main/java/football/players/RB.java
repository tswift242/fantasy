package football.players;

import java.util.Set;
import java.util.HashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.categories.Rush;
import football.stats.categories.Rec;
import football.stats.categories.Misc;
import football.util.PlayerUtil;
import static football.util.ValidateUtil.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtil.checkArrayLength;

public final class RB extends Player
{
	private static final int[] statTypeSizes = {Rush.size(),Rec.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtil.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtil.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private final Set<Stat<Rush>> rushStats;
	private final Set<Stat<Rec>> recStats;
	private final Set<Stat<Misc>> miscStats;

	//stats ordered: atts, yds, td
	public RB(String name, double defaultScore, Set<Stat<Rush>> rushStats, Set<Stat<Rec>> recStats, Set<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.rushStats = new HashSet<Stat<Rush>>(rushStats);
		this.recStats = new HashSet<Stat<Rec>>(recStats);
		this.miscStats = new HashSet<Stat<Misc>>(miscStats);
		checkStatsSetNotNullWithCorrectSize(this.rushStats,Rush.class);
		checkStatsSetNotNullWithCorrectSize(this.recStats,Rec.class);
		checkStatsSetNotNullWithCorrectSize(this.miscStats,Misc.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public RB(RB other) {
		this(other.name, other.score, other.rushStats, other.recStats, other.miscStats);
	}

	public Set<Stat<Rush>> getRushStats() {
		return new HashSet<Stat<Rush>>(rushStats);
	}

	public Set<Stat<Rec>> getRecStats() {
		return new HashSet<Stat<Rec>>(recStats);
	}

	public Set<Stat<Misc>> getMiscStats() {
		return new HashSet<Stat<Misc>>(miscStats);
	}

	@Override
	public RB deepCopy() {
		return new RB(this);
	}

	@Override
	public double evaluate(RuleMap rules) {
		//checkNotNull(rules, "rules is null");
		score = (PlayerUtil.dot(rushStats,rules) + PlayerUtil.dot(recStats,rules) + PlayerUtil.dot(miscStats,rules));
		return score;
	}

	@Override
	public double parseScoringRulesAndEvaluate(String[] args) {
		checkNotNull(args, "args is null");
		int numArgs = getNumStats()+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		RuleMap rushRules = PlayerUtil.parseScoringRules(args,1,statTypeIdxLimits[0],Rush.class);
		RuleMap recRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1],Rec.class);
		RuleMap miscRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2],Misc.class);
		//combine rule maps
		RuleMap rules = new RuleMap();
		rules.putAll(rushRules);
		rules.putAll(recRules);
		rules.putAll(miscRules);
		return evaluate(rules);
	}

	public static int getNumStats() {
		return numStats;
	}

	@Override
	public String categoriesToString() {
		String delimiter = "\t\t";
		return (Rush.valuesToString() + delimiter + Rec.valuesToString() + delimiter + Misc.valuesToString());
	}
}