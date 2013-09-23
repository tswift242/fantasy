package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.Rush;
import football.categories.Rec;
import football.categories.Misc;

public class RB extends Player
{
	private final int numStats = 3;
	private static final int yardsUnit = 10;
	private final int numStatTypes = 3; //number of stat types used by player
	private LinkedHashSet<Stat<Rush>> rushStats;
	private LinkedHashSet<Stat<Rec>> recStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: atts, yds, td
	public RB(String name, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Rec>> recStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name);
		this.rushStats = rushStats;
		this.recStats = recStats;
		this.miscStats = miscStats;
	}

	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: RB.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		return (dot(rushStats,coeffs[0]) + dot(recStats,coeffs[1]) + dot(miscStats,coeffs[2]));
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
