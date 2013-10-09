package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Kick;

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
			System.out.println("Error in K.evaluate: " + numStatTypes + " arguments expected");
			System.exit(1);
		}
		score = PlayerUtil.dot(kickStats,coeffs[0]);
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numKickStats = getNumStats();
		int numArgs = numKickStats+1;
		if(args.length != numArgs) {
			System.out.println("Error in K.parseScoringCoeffsAndEvaluate: " + numArgs + " command line arguments expected");
			System.exit(1);
		}
		//parse coefficients from command line arguments
		double[] kickCoeffs = PlayerUtil.parseScoringCoeffs(args,1,numKickStats);
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
