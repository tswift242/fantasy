package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Pass;
import football.stats.categories.Rush;
import football.stats.categories.Misc;

public class QB extends Player
{
	private static final int yardsUnit = 25;
	private final int numStatTypes = 3; //number of stat types used by player
	private LinkedHashSet<Stat<Pass>> passStats;
	private LinkedHashSet<Stat<Rush>> rushStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: comp, inc, yds, td, inter, sck
	public QB(String name, LinkedHashSet<Stat<Pass>> passStats, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name);
		this.passStats = passStats;
		this.rushStats = rushStats;
		this.miscStats = miscStats;
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: QB.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		score = (dot(passStats,coeffs[0]) + dot(rushStats,coeffs[1]) + dot(miscStats,coeffs[2]));
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		if(args.length < (getNumStats()+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		//TODO: put Pass,Rush,Misc(.size()?) in a list to enforce consistency btw this array and getNumStats()
		int[] limits = cumsum(new int[]{Pass.size(),Rush.size(),Misc.size()});
		double[] passCoeffs = parseScoringCoeffs(args,1,limits[0]);
		double[] rushCoeffs = parseScoringCoeffs(args,limits[0]+1,limits[1]);
		double[] miscCoeffs = parseScoringCoeffs(args,limits[1]+1,limits[2]);
		//normalize coefficients to be per unit
		passCoeffs[2] /= yardsUnit;
		rushCoeffs[1] /= RB.getYardsUnit();
		return evaluate(passCoeffs,rushCoeffs,miscCoeffs);
	}

	public static int getNumStats() {
		return (Pass.size()+Rush.size()+Misc.size());
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
