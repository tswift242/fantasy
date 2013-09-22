package football.players;

import football.Stat;
import football.categories.Def;

public class DEF extends Player
{
	private final int numStats = 8;
	//TODO: check this
	private static final int yardsUnit = 200;
	private static final int ptsUnit = 7;
	private Stat<Def>[] defStats;

	public DEF(String name, int[] stats)
	{
		super(name,stats);
	}

	public int getNumStats() {
		return numStats;
	}

	public static int getYardsUnit() {
		return yardsUnit;
	}

	public static int getPointsUnit() {
		return ptsUnit;
	}

	public String statsCats() {
		return ("sck\t\tint\t\tfumb\t\tsaf\t\ttd\t\tret\t\tpts\t\tyds");
	}
}
