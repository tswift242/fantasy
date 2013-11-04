package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.Rule;
import football.stats.Stat;
import football.stats.StatType;
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
	private final LinkedHashSet<Stat<Rush>> rushStats;
	private final LinkedHashSet<Stat<Rec>> recStats;
	private final LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: atts, yds, td
	public RB(String name, double defaultScore, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.rushStats = new LinkedHashSet<Stat<Rush>>(rushStats);
		this.recStats = new LinkedHashSet<Stat<Rec>>(recStats);
		this.miscStats = new LinkedHashSet<Stat<Misc>>(miscStats);
		checkStatsSetNotNullWithCorrectSize(this.rushStats,Rush.class);
		checkStatsSetNotNullWithCorrectSize(this.recStats,Rec.class);
		checkStatsSetNotNullWithCorrectSize(this.miscStats,Misc.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public RB(RB other) {
		this(other.getName(), other.getScore(), other.getRushStats(), other.getRecStats(), other.getMiscStats());
	}

	public LinkedHashSet<Stat<Rush>> getRushStats() {
		return new LinkedHashSet<Stat<Rush>>(rushStats);
	}

	public LinkedHashSet<Stat<Rec>> getRecStats() {
		return new LinkedHashSet<Stat<Rec>>(recStats);
	}

	public LinkedHashSet<Stat<Misc>> getMiscStats() {
		return new LinkedHashSet<Stat<Misc>>(miscStats);
	}

	@Override
	public RB deepCopy() {
		return new RB(this);
	}

	@Override
	public <T extends Enum<T> & StatType> double evaluate(LinkedHashSet<Rule<T>> ... rules) {
		checkNotNull(rules, "rules is null");
		checkArrayLength(rules,numStatTypes,String.format("Expected %s arguments; found %s arguments",numStatTypes,rules.length));
		score = (PlayerUtil.dot(rushStats,rules[0]) + PlayerUtil.dot(recStats,rules[1]) + PlayerUtil.dot(miscStats,rules[2]));
		return score;
	}

	@Override
	public double parseScoringRulesAndEvaluate(String[] args) {
		checkNotNull(args, "args is null");
		int numArgs = getNumStats()+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		LinkedHashSet<Rule<Rush>> rushRules = PlayerUtil.parseScoringRules(args,1,statTypeIdxLimits[0],Rush.class);
		LinkedHashSet<Rule<Rec>> recRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1],Rec.class);
		LinkedHashSet<Rule<Misc>> miscRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2],Misc.class);
		//normalize coefficients to be per unit
		/*rushCoeffs[Rush.YDS.ordinal()] /= Rush.getYardsUnit();
		recCoeffs[Rec.YDS.ordinal()] /= Rec.getYardsUnit();*/
		return evaluate(rushRules,recRules,miscRules);
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
