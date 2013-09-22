package football.players;

import football.Stat;
import football.categories.Rush;
import football.categories.Rec;
import football.categories.Misc;

public class RB extends Player
{
	private final int numStats = 3;
	private static final int yardsUnit = 10;
	private Stat<Rush>[] rushStats;
	private Stat<Rec>[] recStats;
	private Stat<Misc>[] miscStats;

	//stats ordered: atts, yds, td
	public RB(String name, int[] stats)
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
		return ("att\t\tyds\t\ttd");
	}
}
