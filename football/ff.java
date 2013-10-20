package football;

import java.util.Arrays;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;

import football.players.*;

public class ff
{
	//files to write results to
	public static final String resultsDir = "results";
	public static final String qbFilename = "QBresults.txt";
	public static final String rbFilename = "RBresults.txt";
	public static final String wrFilename = "WRresults.txt";
	public static final String defFilename = "DEFresults.txt";
	public static final String kFilename = "Kresults.txt";
	public static final String sectionDenoter = "********";
	public static final String delimiter = "***********************************************************";

	public static void main(String[] args)
	{
		if(args.length < 1) {
			System.out.println("Error in main: mode not specified");
			usage();
			System.exit(1);
		}
		String mode = args[0];

		if(mode.equals("qb")) {
			QB[] qbs = {Players.SANCHEZ,Players.WEEDEN,Players.LEINART,Players.QUINN,
				Players.KOLB,Players.PALMER,Players.BRADY,Players.PEYTON,Players.RODGERS};
			Player[] customQBs = runPlayers(qbs,args,qbFilename);
		} else if(mode.equals("rb")) {
			RB[] rbs = {Players.REDMAN,Players.HILLMAN,Players.MATHEWS,Players.JONESDREW,
				Players.RBUSH,Players.RICE,Players.LYNCH,Players.FOSTER};
			Player[] customRBs = runPlayers(rbs,args,rbFilename);
		} else if(mode.equals("wr")) {
			WR[] wrs = {Players.BEDWARDS,Players.SHOLMES,Players.HDOUGLAS,Players.MANNINGHAM,
				Players.AMENDOLA,Players.JJONES,Players.DBRYANT,Players.CJOHNSON};
			Player[] customWRs = runPlayers(wrs,args,wrFilename);
		} else if(mode.equals("def")) {
			DEF[] defs = {Players.OAKLAND,Players.NEWORLEANS,Players.JACKSONVILLE,
				Players.CLEVELAND,Players.SEATTLE,Players.SANFRAN,Players.CHICAGO};
			Player[] customDEFs = runPlayers(defs,args,defFilename);
		} else if(mode.equals("k")) {
			K[] ks = {Players.CUNDIFF,Players.FOLK,Players.CROSBY,Players.FORBATH,
				Players.SCOBEE,Players.SUISHAM,Players.GOSTKOWSKI,Players.MBRYANT,Players.TUCKER};
			Player[] customKs = runPlayers(ks,args,kFilename);
		} else {
			System.out.println("Error in main: Invalid mode");
			usage();
			System.exit(1);
		}
	}

	//could pass in PrintStream instead of filename string to make switching to System.out. easy
	public static Player[] runPlayers(Player[] defaultPlayers, String[] args, String filename) {
		//sort players according to default scores
		Arrays.sort(defaultPlayers);
		//make copy of players to preserve original order
		int numPlayers = defaultPlayers.length;
		Player[] customPlayers = new Player[numPlayers];
		for(int i = 0; i < numPlayers; i++) {
			customPlayers[i] = defaultPlayers[i].deepCopy();
		}
		//evaluate all players with custom rules
		for(int i = 0; i < numPlayers; i++) {
			customPlayers[i].parseScoringCoeffsAndEvaluate(args);
		}
		Arrays.sort(customPlayers); //sort players based on score
		//write results to file
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(resultsDir,filename),true)));
			out.println(delimiter + "\n");
			out.println(toSectionHeader("Custom scoring coefficients"));
			out.println(customPlayers[0].categoriesToString()); //TODO: make this call static
			printCoeffs(args,out);
			/*out.println(toSectionHeader("Default scoring rules"));
			printArray(defaultPlayers,out);*/
			out.println(toSectionHeader("Custom scoring rules"));
			printArray(customPlayers,out);
			//TODO: calculate (dis)similarity btw customPlayers and players
			//printArray(defaultPlayers,new PrintWriter(System.out));
			out.println(delimiter + "\n\n\n");
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

	//write players array to printwriter stream
	private static void printArray(Player[] players, PrintWriter out) {
		int numPlayers = players.length;
		for(int i = 0; i < numPlayers; i++) {
			out.println(players[i].toString());
		}
		out.println("\n");
		out.flush();
	}

	//TODO: format this better for players with different stat types
	//write coeffs array to printwriter stream
	private static void printCoeffs(String[] args, PrintWriter out) {
		int numCoeffs = args.length;
		for(int i = 1; i < numCoeffs; i++) { //skip mode argument
			out.print(args[i] + "\t\t");
		}
		out.println("\n");
	}

	//surrounds sectionTitle on both sides by sectionDenoter to create section header
	private static String toSectionHeader(String sectionTitle) {
		return (sectionDenoter + " " + sectionTitle + " " + sectionDenoter);
	}
}
