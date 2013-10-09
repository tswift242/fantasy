package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Rush;
import football.stats.categories.Rec;
import football.stats.categories.Misc;

public class RB extends Player
{
	private static final int yardsUnit = 10;
	private final int numStatTypes = 3; //number of stat types used by player
	private LinkedHashSet<Stat<Rush>> rushStats;
	private LinkedHashSet<Stat<Rec>> recStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: atts, yds, td
	public RB(String name, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name);
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
		int[] limits = PlayerUtil.cumsum(new int[]{Rush.size(),Rec.size(),Misc.size()});
		double[] rushCoeffs = PlayerUtil.parseScoringCoeffs(args,1,limits[0]);
		double[] recCoeffs = PlayerUtil.parseScoringCoeffs(args,limits[0]+1,limits[1]);
		double[] miscCoeffs = PlayerUtil.parseScoringCoeffs(args,limits[1]+1,limits[2]);
		//normalize coefficients to be per unit
		rushCoeffs[1] /= yardsUnit;
		recCoeffs[1] /= WR.getYardsUnit();
		return evaluate(rushCoeffs,recCoeffs,miscCoeffs);
	}

	public static int getNumStats() {
		return (Rush.size()+Rec.size()+Misc.size());
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
