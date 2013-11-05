package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.Rule;
import football.stats.Stat;
import football.stats.StatType;
import football.stats.categories.Rec;
import football.stats.categories.Misc;
import football.util.PlayerUtil;
import static football.util.ValidateUtil.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtil.checkArrayLength;

public final class WR extends Player
{
	private static final int[] statTypeSizes = {Rec.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtil.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtil.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private final LinkedHashSet<Stat<Rec>> recStats;
	private final LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: rec, yds, td
	public WR(String name, double defaultScore, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.recStats = new LinkedHashSet<Stat<Rec>>(recStats);
		this.miscStats = new LinkedHashSet<Stat<Misc>>(miscStats);
		checkStatsSetNotNullWithCorrectSize(this.recStats,Rec.class);
		checkStatsSetNotNullWithCorrectSize(this.miscStats,Misc.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public WR(WR other) {
		this(other.getName(), other.getScore(), other.getRecStats(), other.getMiscStats());
	}

	public LinkedHashSet<Stat<Rec>> getRecStats() {
		return new LinkedHashSet<Stat<Rec>>(recStats);
	}

	public LinkedHashSet<Stat<Misc>> getMiscStats() {
		return new LinkedHashSet<Stat<Misc>>(miscStats);
	}

	@Override
	public WR deepCopy() {
		return new WR(this);
	}

	//@Override
	//public <T extends Enum<T> & StatType> double evaluate(LinkedHashSet<Rule<T>> ... rules) {
	public double evaluate(LinkedHashSet<Rule<Rec>> recRules, LinkedHashSet<Rule<Misc>> miscRules) {
		//checkNotNull(rules, "rules is null");
		//checkArrayLength(rules,numStatTypes,String.format("Expected %s arguments; found %s arguments",numStatTypes,rules.length));
		score = (PlayerUtil.dot(recStats,recRules) + PlayerUtil.dot(miscStats,miscRules));
		return score;
	}

	@Override
	public double parseScoringRulesAndEvaluate(String[] args) {
		checkNotNull(args, "args is null");
		int numArgs = getNumStats()+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		LinkedHashSet<Rule<Rec>> recRules = PlayerUtil.parseScoringRules(args,1,statTypeIdxLimits[0],Rec.class);
		LinkedHashSet<Rule<Misc>> miscRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1],Misc.class);
		//normalize coefficients to be per unit
		/*recCoeffs[Rec.YDS.ordinal()] /= Rec.getYardsUnit();*/
		return evaluate(recRules,miscRules);
	}

	public static int getNumStats() {
		return numStats;
	}

	@Override
	public String categoriesToString() {
		return (Rec.valuesToString() + "\t\t" + Misc.valuesToString());
	}
}
