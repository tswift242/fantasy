package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Rush;
import football.stats.categories.Rec;
import football.stats.categories.Misc;
import football.util.PlayerUtil;

public class RB extends Player
{
	private static final int[] statTypeSizes = {Rush.size(),Rec.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtil.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtil.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private LinkedHashSet<Stat<Rush>> rushStats;
	private LinkedHashSet<Stat<Rec>> recStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: atts, yds, td
	public RB(String name, double defaultScore, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.rushStats = rushStats;
		this.recStats = recStats;
		this.miscStats = miscStats;
	}

	//copy constructor. Note: does not copy stat sets.
	public RB(RB other) {
		this(other.getName(), other.getScore(), other.getRushStats(), other.getRecStats(), other.getMiscStats());
	}

	public LinkedHashSet<Stat<Rush>> getRushStats() {
		return rushStats;
	}

	public LinkedHashSet<Stat<Rec>> getRecStats() {
		return recStats;
	}

	public LinkedHashSet<Stat<Misc>> getMiscStats() {
		return miscStats;
	}

	@Override
	public RB deepCopy() {
		return new RB(this);
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			throw new IllegalArgumentException(numStatTypes + " arguments expected; found " + coeffs.length);
		}
		score = (PlayerUtil.dot(rushStats,coeffs[0]) + PlayerUtil.dot(recStats,coeffs[1]) + PlayerUtil.dot(miscStats,coeffs[2]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		if(args.length != numArgs) {
			throw new IllegalArgumentException(numArgs + " command line arguments expected; found " + args.length);
		}
		//parse coefficients from command line arguments
		double[] rushCoeffs = PlayerUtil.parseScoringCoeffs(args,1,statTypeIdxLimits[0]);
		double[] recCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1]);
		double[] miscCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2]);
		//normalize coefficients to be per unit
		rushCoeffs[Rush.YDS.ordinal()] /= Rush.getYardsUnit();
		recCoeffs[Rec.YDS.ordinal()] /= Rec.getYardsUnit();
		return evaluate(rushCoeffs,recCoeffs,miscCoeffs);
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
