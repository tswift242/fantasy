package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.Pass;
import football.categories.Rush;
import football.categories.Misc;

public class QB extends Player
{
	private final int numStats = 6;
	private static final int yardsUnit = 25;
	private final int numStatTypes = 3; //number of stat types used by player
	private LinkedHashSet<Stat<Pass>> passStats;
	private LinkedHashSet<Stat<Rush>> rushStats;
	private LinkedHashSet<Stat<Misc>> miscStats;

	//stats ordered: comp, inc, yds, td, inter, sck
	public QB(String name, LinkedHashSet<Stat<Pass>> passStats, LinkedHashSet<Stat<Rush>> rushStats, LinkedHashSet<Stat<Misc>> miscStats)
	{
		super(name);
		this.passStats = passStats;
		this.rushStats = rushStats;
		this.miscStats = miscStats;
	}

	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: QB.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		return (dot(passStats,coeffs[0]) + dot(rushStats,coeffs[1]) + dot(miscStats,coeffs[2]));
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
