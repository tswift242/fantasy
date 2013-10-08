package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.Def;

public class DEF extends Player
{
	//TODO: check this
	private static final int yardsUnit = 200;
	private static final int ptsUnit = 7;
	private final int numStatTypes = 1; //number of stat types used by player
	private LinkedHashSet<Stat<Def>> defStats;

	public DEF(String name, LinkedHashSet<Stat<Def>> defStats)
	{
		super(name);
		this.defStats = defStats;
	}

	//TODO: switch from vararg to fixed args??
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: DEF.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		score = dot(defStats,coeffs[0]);
		return score;
	}

	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numDefStats = getNumStats();
		if(args.length < (numDefStats+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		double[] defCoeffs = parseScoringCoeffs(args,1,numDefStats);
		//normalize coefficients to be per unit
		defCoeffs[6] /= yardsUnit;
		defCoeffs[7] /= ptsUnit;
		return evaluate(defCoeffs);
	}

	public static int getNumStats() {
		return Def.size();
	}

	public static int getYardsUnit() {
		return yardsUnit;
	}

	public static int getPointsUnit() {
		return ptsUnit;
	}

	public String categoriesToString() {
		return Def.valuesToString();
	}
}
