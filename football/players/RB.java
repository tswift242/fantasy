package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Rush;
import football.stats.categories.Rec;
import football.stats.categories.Misc;

public class RB extends Player
{
	private static final int yardsUnit = 10;
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

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error in RB.evaluate: " + numStatTypes + " arguments expected");
			System.exit(1);
		}
		score = (PlayerUtil.dot(rushStats,coeffs[0]) + PlayerUtil.dot(recStats,coeffs[1]) + PlayerUtil.dot(miscStats,coeffs[2]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		if(args.length != numArgs) {
			System.out.println("Error in RB.parseScoringCoeffsAndEvaluate: " + numArgs + " command line arguments expected");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		double[] rushCoeffs = PlayerUtil.parseScoringCoeffs(args,1,statTypeIdxLimits[0]);
		double[] recCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1]);
		double[] miscCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2]);
		//normalize coefficients to be per unit
		rushCoeffs[1] /= yardsUnit;
		recCoeffs[1] /= WR.getYardsUnit();
		return evaluate(rushCoeffs,recCoeffs,miscCoeffs);
	}

	public static int getNumStats() {
		return numStats;
	}

	public static int getYardsUnit() {
		return yardsUnit;
	}

	@Override
	public String categoriesToString() {
		String delimiter = "\t\t||\t\t";
		return (Rush.valuesToString() + delimiter + Rec.valuesToString() + delimiter + Misc.valuesToString());
	}
}
