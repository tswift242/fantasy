package football.players;

import football.Stat;
import football.categories.Def;

public class DEF extends Player
{
	private final int numStats = 8;
	//TODO: check this
	private static final int yardsUnit = 200;
	private static final int ptsUnit = 7;
	private final int numStatTypes = 1; //number of stat types used by player
	private Stat<Def>[] defStats;

	public DEF(String name, Stat<Def>[] defStats)
	{
		super(name);
		this.defStats = defStats;
	}

	public double evaluate(double[] ... coeffs) {
		if(coeffs.length != numStatTypes) {
			System.out.println("Error: DEF.evalutae() expects " + numStatTypes + " arguments");
			System.exit(1);
		}
		return dot(defStats,coeffs[0]);
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
