package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.Rec;
import football.categories.Misc;

public class WR extends Player
{
	private final int numStats = 3;
	private static final int yardsUnit = 10;
	private final int numStatTypes = 2; //number of stat types used by player
	private LinkedHashSet<Stat<Rec>> recStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: rec, yds, td
	public WR(String name, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name);
		this.recStats = recStats;
		this.miscStats = miscStats;
	}

	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: wr.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		return (dot(recStats,coeffs[0]) + dot(miscStats,coeffs[1]));
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
