package football.players;

public class RB extends Player
{
	private final int numStats = 3;
	private static final int yardsUnit = 10;

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
