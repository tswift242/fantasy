package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.Rec;
import football.categories.Misc;

public class WR extends Player
{
	private static final int yardsUnit = 10;
	private final int numStatTypes = 2; //number of stat types used by player
	private LinkedHashSet<Stat<Rec>> recStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: rec, yds, td
	public WR(String name, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name);
		this.recStats = recStats;
		this.miscStats = miscStats;
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: wr.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		score = (dot(recStats,coeffs[0]) + dot(miscStats,coeffs[1]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		if(args.length < (getNumStats()+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		int[] limits = cumsum(new int[]{Rec.size(),Misc.size()});
		double[] recCoeffs = parseScoringCoeffs(args,1,limits[0]);
		double[] miscCoeffs = parseScoringCoeffs(args,limits[0]+1,limits[1]);
		//normalize coefficients to be per unit
		recCoeffs[1] /= yardsUnit;
		return evaluate(recCoeffs,miscCoeffs);
	}

	public static int getNumStats() {
		return (Rec.size()+Misc.size());
	}

	public static int getYardsUnit() {
		return yardsUnit;
	}

	@Override
	public String categoriesToString() {
		return (Rec.valuesToString() + "\t\t||\t\t" + Misc.valuesToString());
	}
}
