package football.players;

import football.Stat;
import football.categories.Kick;

public class K extends Player
{
	private final int numStats = 12;
	private static final int yardsUnit = 10;
	private Stat<Kick>[] kickStats;

	//stats ordered: pat made/miss, fg made, fg miss
	public K(String name, int[] stats)
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
		return ("pat md\t\tpat ms\t\tfg md 0\t\fg md 20\t\tfg md 30\t\tfg md 40\t\tfg md 50\t\tfg ms 0\t\tfg ms 20\t\tfg ms 30\t\tfg ms 40\t\tfg ms 50");
	}
}
