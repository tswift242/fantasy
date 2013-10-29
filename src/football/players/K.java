package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;
import football.stats.categories.Kick;
import football.util.PlayerUtil;

public class K extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private LinkedHashSet<Stat<Kick>> kickStats;

	//stats ordered: pat made/miss, fg made, fg miss
	public K(String name, double defaultScore, LinkedHashSet<Stat<Kick>> kickStats)
	{
		super(name, defaultScore);
		this.kickStats = kickStats;
	}

	//copy constructor. Note: does not copy stat sets.
	public K(K other) {
		this(other.getName(), other.getScore(), other.getKickStats());
	}

	public LinkedHashSet<Stat<Kick>> getKickStats() {
		return kickStats;
	}

	@Override
	public K deepCopy() {
		return new K(this);
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			throw new IllegalArgumentException(numStatTypes + " arguments expected; found " + coeffs.length);
		}
		score = PlayerUtil.dot(kickStats,coeffs[0]);
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numKickStats = getNumStats();
		int numArgs = numKickStats+1;
		if(args.length != numArgs) {
			throw new IllegalArgumentException(numArgs + " command line arguments expected; found " + args.length);
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
