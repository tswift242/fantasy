package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Pass;
import football.stats.categories.Rush;
import football.stats.categories.Misc;

public class QB extends Player
{
	private static final int yardsUnit = 25;
	private static final int[] statTypeSizes = {Pass.size(),Rush.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtil.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtil.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private LinkedHashSet<Stat<Pass>> passStats;
	private LinkedHashSet<Stat<Rush>> rushStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: comp, inc, yds, td, inter, sck
	public QB(String name, double defaultScore, LinkedHashSet<Stat<Pass>> passStats, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.passStats = passStats;
		this.rushStats = rushStats;
		this.miscStats = miscStats;
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error in QB.evaluate: " + numStatTypes + " arguments expected");
			System.exit(1);
		}
		score = (PlayerUtil.dot(passStats,coeffs[0]) + PlayerUtil.dot(rushStats,coeffs[1]) + PlayerUtil.dot(miscStats,coeffs[2]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		if(args.length != numArgs) {
			System.out.println("Error in QB.parseScoringCoeffsAndEvaluate: " + numArgs + " command line arguments expected");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		double[] passCoeffs = PlayerUtil.parseScoringCoeffs(args,1,statTypeIdxLimits[0]);
		double[] rushCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[0]+1,statTypeIdxLimits[1]);
		double[] miscCoeffs = PlayerUtil.parseScoringCoeffs(args,statTypeIdxLimits[1]+1,statTypeIdxLimits[2]);
		//normalize coefficients to be per unit
		passCoeffs[2] /= yardsUnit;
		rushCoeffs[1] /= RB.getYardsUnit();
		return evaluate(passCoeffs,rushCoeffs,miscCoeffs);
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
		return (Pass.valuesToString() + delimiter + Rush.valuesToString() + delimiter + Misc.valuesToString());
	}
}
