package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.Rule;
import football.stats.Stat;
import football.stats.StatType;
import football.stats.categories.Def;
import football.util.PlayerUtil;
import static football.util.ValidateUtil.checkStatsSetNotNullWithCorrectSize;
import static football.util.ValidateUtil.checkArrayLength;

public final class DEF extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private final LinkedHashSet<Stat<Def>> defStats;

	public DEF(String name, double defaultScore, LinkedHashSet<Stat<Def>> defStats)
	{
		super(name, defaultScore);
		this.defStats = new LinkedHashSet<Stat<Def>>(defStats);
		checkStatsSetNotNullWithCorrectSize(this.defStats,Def.class);
	}

	//copy constructor. Note: does not copy stat sets.
	public DEF(DEF other) {
		this(other.getName(), other.getScore(), other.getDefStats());
	}

	public LinkedHashSet<Stat<Def>> getDefStats() {
		//TODO: return Collections.unmodifiableSet() if type changed from LinkedHashSet to Set
		return new LinkedHashSet<Stat<Def>>(defStats);
	}

	@Override
	public DEF deepCopy() {
		return new DEF(this);
	}

	//TODO: switch from vararg to fixed args??
	@Override
	public <T extends Enum<T> & StatType> double evaluate(LinkedHashSet<Rule<T>> ... rules) {
		checkNotNull(rules, "rules is null");
		checkArrayLength(rules,numStatTypes,String.format("Expected %s arguments; found %s arguments",numStatTypes,rules.length));
		score = PlayerUtil.dot(defStats,rules[0]);
		return score;
	}

	@Override
	public double parseScoringRulesAndEvaluate(String[] args) {
		checkNotNull(args, "args is null");
		int numDefStats = getNumStats();
		int numArgs = numDefStats+1;
		checkArrayLength(args,numArgs,String.format("Expected %s command line arguments; found %s arguments",numArgs,args.length));
		//parse coefficients from command line arguments
		LinkedHashSet<Rule<Def>> defRules = PlayerUtil.parseScoringRules(args,1,numDefStats,Def.class);
		//normalize coefficients to be per unit
		/*defCoeffs[Def.YDS.ordinal()] /= Def.getYardsUnit();
		defCoeffs[Def.PTS.ordinal()] /= Def.getPointsUnit();*/
		return evaluate(defRules);
	}

	public static int getNumStats() {
		return Def.size();
	}

	@Override
	public String categoriesToString() {
		return Def.valuesToString();
	}
}
