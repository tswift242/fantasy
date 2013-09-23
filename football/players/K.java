package football.players;

import java.util.Set;

import football.Stat;
import football.categories.Kick;

public class K extends Player
{
	private final int numStats = 12;
	private static final int yardsUnit = 10;
	private final int numStatTypes = 1; //number of stat types used by player
	private Set<Stat<Kick>> kickStats;

	//stats ordered: pat made/miss, fg made, fg miss
	public K(String name, Set<Stat<Kick>> kickStats)
	{
		super(name);
		this.kickStats = kickStats;
	}

	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: K.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		return dot(kickStats,coeffs[0]);
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
