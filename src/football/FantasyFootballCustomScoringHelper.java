package football;

import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import football.players.*;
import football.util.metrics.Metric;
import football.util.metrics.SortOrderMetric;

public class FantasyFootballCustomScoringHelper
{
	//files to write results to
	public static final String resultsDir = "results";
	public static final String sectionDenoter = "********";
	public static final String delimiter = "***********************************************************";

	public static void main(String[] args)
	{
		if(args.length < 1) {
			System.out.println("Error in main: Mode not specified");
			usage();
			System.exit(1);
		}
		String mode = args[0];

		//quickly initialize group of players based on mode
		Player[] players = null;
		if(mode.equals("qb")) {
			players = new Player[]{Players.SANCHEZ,Players.WEEDEN,Players.LEINART,Players.QUINN,
				Players.KOLB,Players.PALMER,Players.BRADY,Players.PEYTON,Players.RODGERS};
		} else if(mode.equals("rb")) {
			players = new Player[]{Players.REDMAN,Players.HILLMAN,Players.MATHEWS,Players.JONESDREW,
				Players.RBUSH,Players.RICE,Players.LYNCH,Players.FOSTER};
		} else if(mode.equals("wr")) {
			players = new Player[]{Players.BEDWARDS,Players.SHOLMES,Players.HDOUGLAS,Players.MANNINGHAM,
				Players.AMENDOLA,Players.JJONES,Players.DBRYANT,Players.CJOHNSON};
		} else if(mode.equals("def")) {
			players = new Player[]{Players.OAKLAND,Players.NEWORLEANS,Players.JACKSONVILLE,
				Players.CLEVELAND,Players.SEATTLE,Players.SANFRAN,Players.CHICAGO};
		} else if(mode.equals("k")) {
			players = new Player[]{Players.CUNDIFF,Players.FOLK,Players.CROSBY,Players.FORBATH,
				Players.SCOBEE,Players.SUISHAM,Players.GOSTKOWSKI,Players.MBRYANT,Players.TUCKER};
		} else {
			System.out.println("Error in main: Invalid mode");
			usage();
			System.exit(1);
		}

		String filename = (mode.toUpperCase() + "results.txt");
		List<Player> defaultPlayers = new ArrayList<Player>(Arrays.asList(players));
		List<Player> customPlayers = runPlayers(defaultPlayers,args,filename);
	}

	//TODO: replace List<Player> with List<T extends Player>
	//could pass in PrintStream instead of filename string to make switching to System.out. easy
	public static List<Player> runPlayers(List<Player> defaultPlayers, String[] args, String filename) {
		//sort players according to default scores
		Collections.sort(defaultPlayers);
		//make copy of players to preserve original order
		List<Player> customPlayers = new ArrayList<Player>(defaultPlayers.size());
		for(Player player : defaultPlayers) {
			customPlayers.add(player.deepCopy());
		}
		//evaluate all players with custom rules
		for(Player player : customPlayers) {
			player.parseScoringCoeffsAndEvaluate(args);
		}
		//sort players according to custom scores
		Collections.sort(customPlayers);
		//write results to file
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(resultsDir,filename),true)));
			out.println(delimiter + "\n");
			out.println(toSectionHeader("Custom scoring coefficients"));
			out.println(customPlayers.get(0).categoriesToString()); //TODO: make this call static
			printCoeffs(args,out);
			out.println(toSectionHeader("Default scoring rules"));
			printList(defaultPlayers,out);
			out.println(toSectionHeader("Custom scoring rules"));
			printList(customPlayers,out);
			//calculate (dis)similarity btw customPlayers and defaultPlayers
			Metric metric = new SortOrderMetric();
			double dist = metric.distance(defaultPlayers,customPlayers);
			out.println("Distance between default and custom is: " + dist);
			out.println("\n" + delimiter + "\n\n\n");
			out.close();
		} catch(IOException e) {
			System.out.println("Error in runPlayers: IO exception when writing to file " + filename);
		}
		return customPlayers;
	}

	private static void usage() {
		String indent = "\t\t";
		System.out.println("Usage: java ff <mode> <sc1> ... <scN>,\twhere");
		System.out.println(indent + "mode := player position to simulate. Possible values are def, k, qb, rb, and wr.");
		System.out.println(indent + "sc1 - scN := scoring coefficients representing all rules needed to score players at position given by mode. Necessary scoring coefficients per position are given below.");
		/*System.out.println(indent + "def: " + DEF.categoriesToString());
		System.out.println(indent + "k: " + K.categoriesToString());
		System.out.println(indent + "qb: " + QB.categoriesToString());
		System.out.println(indent + "rb: " + RB.categoriesToString());
		System.out.println(indent + "wr: " + WR.categoriesToString());*/
	}

	//TODO: consider just using players.toString()
	//write players list to printwriter stream
	private static <E extends Player> void printList(List<E> players, PrintWriter out) {
		for(E player : players) {
			out.println(player.toString());
		}
		out.println("\n");
		out.flush(); //for System.out casted to printwriter
	}

	//TODO: format this better for players with different stat types
	//write coeffs array to printwriter stream
	private static void printCoeffs(String[] args, PrintWriter out) {
		int numCoeffs = args.length;
		for(int i = 1; i < numCoeffs; i++) { //skip mode argument
			out.printf("%-10s ",args[i]);
		}
		out.println("\n");
	}

	//surrounds sectionTitle on both sides by sectionDenoter to create section header
	private static String toSectionHeader(String sectionTitle) {
		return (sectionDenoter + " " + sectionTitle + " " + sectionDenoter);
	}
}
