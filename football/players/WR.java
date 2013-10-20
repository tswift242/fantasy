package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Rec;
import football.stats.categories.Misc;
import football.util.PlayerUtil;

public class WR extends Player
{
	private static final int[] statTypeSizes = {Rec.size(),Misc.size()};
	private static final int numStatTypes = statTypeSizes.length; //number of stat types used by player
	//delimiting indices separating 2 different stat types in cmd line args
	private static final int[] statTypeIdxLimits = PlayerUtil.cumsum(statTypeSizes);
	//total number of stat categories affecting this player's score
	//right hand expression below equivalent to PlayerUtil.sum(statTypeSizes)
	private static final int numStats = statTypeIdxLimits[numStatTypes-1];
	private LinkedHashSet<Stat<Rec>> recStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: rec, yds, td
	public WR(String name, double defaultScore, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name, defaultScore);
		this.recStats = recStats;
		this.miscStats = miscStats;
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error in WR.evaluate: " + numStatTypes + " arguments expected");
			System.exit(1);
		}
		score = (PlayerUtil.dot(recStats,coeffs[0]) + PlayerUtil.dot(miscStats,coeffs[1]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numArgs = getNumStats()+1;
		if(args.length != numArgs) {
			System.out.println("Error in WR.parseScoringCoeffsAndEvaluate: " + numArgs + " command line arguments expected");
			System.exit(1);
		}
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
		return (Rec.valuesToString() + "\t\t||\t\t" + Misc.valuesToString());
	}
}
