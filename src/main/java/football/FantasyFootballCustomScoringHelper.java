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
	public static void main(String[] args) throws IOException
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

		//put players into list
		List<Player> defaultPlayers = new ArrayList<Player>(Arrays.asList(players));
		//make copy of players to preserve original order
		List<Player> customPlayers = deepCopyList(defaultPlayers);
		//evaluate all players with custom rules
		for(Player player : customPlayers) {
			player.parseScoringRulesAndEvaluate(args);
		}
		//sort players according to default rules
		Collections.sort(defaultPlayers);
		//sort players according to custom rules
		Collections.sort(customPlayers);
		//write results to file filename in directory resultsDir
		String resultsDirectory = "results";
		String filename = (mode.toUpperCase() + "results.txt");
		logResults(resultsDirectory,filename,defaultPlayers,customPlayers,args);
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

	//Make deep copy of list of players. Works for any List<E> where E defines a deepCopy() method
	private static List<Player> deepCopyList(List<Player> list) {
		List<Player> listCopy = new ArrayList<Player>(list.size());
		for(Player element : list) {
			listCopy.add(element.deepCopy());
		}
		return listCopy;
	}

	//TODO: consider just using players.toString()
	//write players list to printwriter stream
	private static <E extends Player> void printList(List<E> players, PrintWriter out) {
		for(E player : players) {
			out.println(player.toString());
		}
		out.println("\n");
		//out.flush(); //for System.out casted to printwriter
	}

	//write rules array to printwriter stream
	private static void printRules(String[] args, PrintWriter out) {
		int numRules = args.length;
		for(int i = 1; i < numRules; i++) { //skip mode argument
			out.printf("%-10s ",args[i]);
		}
		out.println("\n");
	}

	//surrounds sectionTitle on both sides by sectionDenoter to create section header
	private static String toSectionHeader(String sectionTitle, String sectionDenoter) {
		return (sectionDenoter + " " + sectionTitle + " " + sectionDenoter);
	}

	private static void logResults(String resultsDirectory, String filename, List<Player> defaultPlayers, 
			List<Player> customPlayers, String[] args) throws IOException {
		PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(resultsDirectory,filename),true)));
		String delimiter = "***********************************************************";
		String sectionDenoter = "********";
		out.println(delimiter + "\n");
		out.println(toSectionHeader("Custom scoring rules",sectionDenoter));
		out.println(customPlayers.get(0).categoriesToString()); //TODO: make this call static
		printRules(args,out);
		out.println(toSectionHeader("Scores using default rules",sectionDenoter));
		printList(defaultPlayers,out);
		out.println(toSectionHeader("Scores using custom rules",sectionDenoter));
		printList(customPlayers,out);
		//calculate (dis)similarity btw customPlayers and defaultPlayers
		Metric metric = new SortOrderMetric();
		double dist = metric.distance(defaultPlayers,customPlayers);
		out.println("Distance between default and custom rules is: " + dist);
		out.println("\n" + delimiter + "\n\n\n");
		out.close();
	}
}
