package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Pass;
import football.stats.categories.Rush;
import football.stats.categories.Misc;
import football.util.PlayerUtil;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

public class QB extends Player
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
		checkNotNull(passStats, "passStats is null");
		checkArgument(passStats.size() == Pass.size(), "passStats' size %d does not equal %d",passStats.size(),Pass.size());
		checkNotNull(rushStats, "rushStats is null");
		checkArgument(rushStats.size() == Rush.size(), "rushStats' size %d does not equal %d",rushStats.size(),Rush.size());
		checkNotNull(miscStats, "miscStats is null");
		checkArgument(miscStats.size() == Misc.size(), "miscStats' size %d does not equal %d",miscStats.size(),Misc.size());
		this.passStats = passStats;
		this.rushStats = rushStats;
		this.miscStats = miscStats;
	}

	//copy constructor. Note: does not copy stat sets.
	public QB(QB other) {
		this(other.getName(), other.getScore(), other.getPassStats(), other.getRushStats(), other.getMiscStats());
	}

	public LinkedHashSet<Stat<Pass>> getPassStats() {
		return passStats;
	}

	public LinkedHashSet<Stat<Rush>> getRushStats() {
		return rushStats;
	}

	public LinkedHashSet<Stat<Misc>> getMiscStats() {
		return miscStats;
	}

	@Override
	public QB deepCopy() {
		return new QB(this);
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		checkNotNull(coeffs, "coeffs is null");
		checkArgument(coeffs.length == numStatTypes, "Expected %d arguments; found %d arguments",numStatTypes,coeffs.length);
		score = (PlayerUtil.dot(passStats,coeffs[0]) + PlayerUtil.dot(rushStats,coeffs[1]) + PlayerUtil.dot(miscStats,coeffs[2]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		checkArgument(args.length == numArgs, "Expected %d command line arguments; found %d arguments", numArgs, args.length);
		//parse coefficients from command line arguments
		double[] passCoeffs = PlayerUtil.parseScoringCoeffs(args,1,statTypeIdxLimits[0]);
		double[] rushCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1]);
		double[] miscCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2]);
		//normalize coefficients to be per unit
		passCoeffs[Pass.YDS.ordinal()] /= Pass.getYardsUnit();
		rushCoeffs[Rush.YDS.ordinal()] /= Rush.getYardsUnit();
		return evaluate(passCoeffs,rushCoeffs,miscCoeffs);
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
