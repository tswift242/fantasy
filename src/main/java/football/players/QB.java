package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.Rule;
import football.stats.Stat;
import football.stats.StatType;
import football.stats.categories.Pass;
import football.stats.categories.Rush;
import football.stats.categories.Misc;
import football.util.PlayerUtil;
import static football.util.ValidateUtil.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtil.checkArrayLength;

public final class QB extends Player
{
	private static final int[] statTypeSizes = {Pass.size(),Rush.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtil.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtil.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private final LinkedHashSet<Stat<Pass>> passStats;
	private final LinkedHashSet<Stat<Rush>> rushStats;
	private final LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: comp, inc, yds, td, inter, sck
	public QB(String name, double defaultScore, LinkedHashSet<Stat<Pass>> passStats, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Misc>> miscStats)
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
		this(other.getName(), other.getScore(), other.getPassStats(), other.getRushStats(), other.getMiscStats());
	}

	public LinkedHashSet<Stat<Pass>> getPassStats() {
		return new LinkedHashSet<Stat<Pass>>(passStats);
	}

	public LinkedHashSet<Stat<Rush>> getRushStats() {
		return new LinkedHashSet<Stat<Rush>>(rushStats);
	}

	public LinkedHashSet<Stat<Misc>> getMiscStats() {
		return new LinkedHashSet<Stat<Misc>>(miscStats);
	}

	@Override
	public QB deepCopy() {
		return new QB(this);
	}

	//@Override
	//public <T extends Enum<T> & StatType> double evaluate(LinkedHashSet<Rule<T>> ... rules) {
	public double evaluate(LinkedHashSet<Rule<Pass>> passRules, LinkedHashSet<Rule<Rush>> rushRules, LinkedHashSet<Rule<Misc>> miscRules) {
		//checkNotNull(rules, "rules is null");
		//checkArrayLength(rules,numStatTypes,String.format("Expected %s arguments; found %s arguments",numStatTypes,rules.length));
		score = (PlayerUtil.dot(passStats,passRules) + PlayerUtil.dot(rushStats,rushRules) + PlayerUtil.dot(miscStats,miscRules));
		return score;
	}

	@Override
	public double parseScoringRulesAndEvaluate(String[] args) {
		checkNotNull(args, "args is null");
		int numArgs = getNumStats()+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		LinkedHashSet<Rule<Pass>> passRules = PlayerUtil.parseScoringRules(args,1,statTypeIdxLimits[0],Pass.class);
		LinkedHashSet<Rule<Rush>> rushRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1],Rush.class);
		LinkedHashSet<Rule<Misc>> miscRules = PlayerUtil.parseScoringRules(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2],Misc.class);
		//normalize coefficients to be per unit
		/*passCoeffs[Pass.YDS.ordinal()] /= Pass.getYardsUnit();
		rushCoeffs[Rush.YDS.ordinal()] /= Rush.getYardsUnit();*/
		return evaluate(passRules,rushRules,miscRules);
	}

	public static int getNumStats() {
		return numStats;
	}

	@Override
	public String categoriesToString() {
		String delimiter = "\t\t";
		return (Pass.valuesToString() + delimiter + Rush.valuesToString() + delimiter + Misc.valuesToString());
	}
}
