package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Stat;
import football.stats.categories.Def;
import football.util.PlayerUtil;

public final class DEF extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private final LinkedHashSet<Stat<Def>> defStats;

	public DEF(String name, double defaultScore, LinkedHashSet<Stat<Def>> defStats)
	{
		super(name, defaultScore);
		this.defStats = new LinkedHashSet<Stat<Def>>(defStats);
		checkNotNull(this.defStats, "defStats is null");
		checkArgument(this.defStats.size() == Def.size(), "defStats' size %s does not equal %s",this.defStats.size(),Def.size());
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
	public double evaluate(double[] ... coeffs) {
		checkNotNull(coeffs, "coeffs is null");
		checkArgument(coeffs.length == numStatTypes, "Expected %s arguments; found %s arguments",numStatTypes,coeffs.length);
		score = PlayerUtil.dot(defStats,coeffs[0]);
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numDefStats = getNumStats();
		int numArgs = numDefStats+1;
		checkArgument(args.length == numArgs, "Expected %s command line arguments; found %s arguments", numArgs, args.length);
		//parse coefficients from command line arguments
		double[] defCoeffs = PlayerUtil.parseScoringCoeffs(args,1,numDefStats);
		//normalize coefficients to be per unit
		defCoeffs[Def.YDS.ordinal()] /= Def.getYardsUnit();
		defCoeffs[Def.PTS.ordinal()] /= Def.getPointsUnit();
		return evaluate(defCoeffs);
	}

	public static int getNumStats() {
		return Def.size();
	}

	@Override
	public String categoriesToString() {
		return Def.valuesToString();
	}
}
