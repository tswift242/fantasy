package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Stat;
import football.stats.categories.Pass;
import football.stats.categories.Rush;
import football.stats.categories.Misc;
import football.util.PlayerUtil;

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
		checkNotNull(this.passStats, "passStats is null");
		checkArgument(this.passStats.size() == Pass.size(), "passStats' size %s does not equal %s",this.passStats.size(),Pass.size());
		checkNotNull(this.rushStats, "rushStats is null");
		checkArgument(this.rushStats.size() == Rush.size(), "rushStats' size %s does not equal %s",this.rushStats.size(),Rush.size());
		checkNotNull(this.miscStats, "miscStats is null");
		checkArgument(this.miscStats.size() == Misc.size(), "miscStats' size %s does not equal %s",this.miscStats.size(),Misc.size());
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

	@Override
	public double evaluate(double[] ... coeffs) {
		checkNotNull(coeffs, "coeffs is null");
		checkArgument(coeffs.length == numStatTypes, "Expected %s arguments; found %s arguments",numStatTypes,coeffs.length);
		score = (PlayerUtil.dot(passStats,coeffs[0]) + PlayerUtil.dot(rushStats,coeffs[1]) + PlayerUtil.dot(miscStats,coeffs[2]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		checkArgument(args.length == numArgs, "Expected %s command line arguments; found %s arguments", numArgs, args.length);
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
