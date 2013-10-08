package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.Kick;

public class K extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private LinkedHashSet<Stat<Kick>> kickStats;

	//stats ordered: pat made/miss, fg made, fg miss
	public K(String name, LinkedHashSet<Stat<Kick>> kickStats)
	{
		super(name);
		this.kickStats = kickStats;
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: K.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		score = dot(kickStats,coeffs[0]);
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numKickStats = getNumStats();
		if(args.length < (numKickStats+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		double[] kickCoeffs = parseScoringCoeffs(args,1,numKickStats);
		return evaluate(kickCoeffs);
	}

	public static int getNumStats() {
		return Kick.size();
	}

	@Override
	public String categoriesToString() {
		return Kick.valuesToString();
	}
}
