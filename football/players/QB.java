package football.players;

import football.Stat;
import football.categories.Pass;
import football.categories.Rush;
import football.categories.Misc;

public class QB extends Player
{
	private final int numStats = 6;
	private static final int yardsUnit = 25;
	private Stat<Pass>[] passStats;
	private Stat<Rush>[] rushStats;
	private Stat<Misc>[] miscStats;

	//stats ordered: comp, inc, yds, td, inter, sck
	public QB(String name, int[] stats)
	{
		super(name,stats);
	}

	public int getNumStats() {
		return numStats;
	}

	public static int getYardsUnit() {
		return yardsUnit;
	}

	public String statsCats() {
		return ("comp\t\tinc\t\tyds\t\ttd\t\tint\t\tsck");
	}
}
