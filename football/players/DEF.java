package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Def;

public class DEF extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private LinkedHashSet<Stat<Def>> defStats;

	public DEF(String name, double defaultScore, LinkedHashSet<Stat<Def>> defStats)
	{
		super(name, defaultScore);
		this.defStats = defStats;
	}

	//TODO: switch from vararg to fixed args??
	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error in DEF.evaluate: " + numStatTypes + " arguments expected");
			System.exit(1);
		}
		score = PlayerUtil.dot(defStats,coeffs[0]);
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numDefStats = getNumStats();
		int numArgs = numDefStats+1;
		if(args.length != numArgs) {
			System.out.println("Error in DEF.parseScoringCoeffsAndEvaluate: " + numArgs + " command line arguments expected");
			System.exit(1);
		}
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
