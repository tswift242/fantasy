package football.players;

import java.util.LinkedHashSet;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Stat;
import football.stats.categories.Kick;
import football.util.PlayerUtil;

public final class K extends Player
{
	private final int numStatTypes = 1; //number of stat types used by player
	private final LinkedHashSet<Stat<Kick>> kickStats;

	//stats ordered: pat made/miss, fg made, fg miss
	public K(String name, double defaultScore, LinkedHashSet<Stat<Kick>> kickStats)
	{
		super(name, defaultScore);
		this.kickStats = new LinkedHashSet<Stat<Kick>>(kickStats);
		checkNotNull(this.kickStats, "kickStats is null");
		checkArgument(this.kickStats.size() == Kick.size(), "kickStats' size %s does not equal %s",this.kickStats.size(),Kick.size());
	}

	//copy constructor. Note: does not copy stat sets.
	public K(K other) {
		this(other.getName(), other.getScore(), other.getKickStats());
	}

	public LinkedHashSet<Stat<Kick>> getKickStats() {
		return new LinkedHashSet<Stat<Kick>>(kickStats);
	}

	@Override
	public K deepCopy() {
		return new K(this);
	}

	@Override
	public double evaluate(double[] ... coeffs) {
		checkNotNull(coeffs, "coeffs is null");
		checkArgument(coeffs.length == numStatTypes, "Expected %s arguments; found %s arguments",numStatTypes,coeffs.length);
		score = PlayerUtil.dot(kickStats,coeffs[0]);
		return score;
	}

	@Override
	public double parseScoringCoeffsAndEvaluate(String[] args) {
		int numKickStats = getNumStats();
		int numArgs = numKickStats+1;
		checkArgument(args.length == numArgs, "Expected %s command line arguments; found %s arguments", numArgs, args.length);
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
