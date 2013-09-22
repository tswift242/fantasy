package football.players;

import football.Stat;
import football.categories.Rec;
import football.categories.Misc;

public class WR extends Player
{
	private final int numStats = 3;
	private static final int yardsUnit = 10;
	private Stat<Rec>[] recStats;
	private Stat<Misc>[] miscStats;

	//stats ordered: rec, yds, td
	public WR(String name, int[] stats)
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
		return ("rec\t\tyds\t\ttd");
	}
}
