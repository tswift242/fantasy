package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Stat;
import football.stats.categories.Rec;
import football.stats.categories.Misc;
import football.util.PlayerUtil;

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
		checkNotNull(this.recStats, "recStats is null");
		checkArgument(this.recStats.size() == Rec.size(), "recStats' size %s does not equal %s",this.recStats.size(),Rec.size());
		checkNotNull(this.miscStats, "miscStats is null");
		checkArgument(this.miscStats.size() == Misc.size(), "miscStats' size %s does not equal %s",this.miscStats.size(),Misc.size());
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

	@Override
	public double evaluate(double[] ... coeffs) {
		checkNotNull(coeffs, "coeffs is null");
		checkArgument(coeffs.length == numStatTypes, "Expected %s arguments; found %s arguments",numStatTypes,coeffs.length);
		score = (PlayerUtil.dot(recStats,coeffs[0]) + PlayerUtil.dot(miscStats,coeffs[1]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		checkArgument(args.length == numArgs, "Expected %s command line arguments; found %s arguments", numArgs, args.length);
		//parse coefficients from command line arguments
		double[] recCoeffs = PlayerUtil.parseScoringCoeffs(args,1,statTypeIdxLimits[0]);
		double[] miscCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1]);
		//normalize coefficients to be per unit
		recCoeffs[Rec.YDS.ordinal()] /= Rec.getYardsUnit();
		return evaluate(recCoeffs,miscCoeffs);
	}

	public static int getNumStats() {
		return numStats;
	}

	@Override
	public String categoriesToString() {
		return (Rec.valuesToString() + "\t\t" + Misc.valuesToString());
	}
}
