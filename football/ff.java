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
			System.out.println("Error: mode not specified");
			System.exit(1);
		}
		String mode = args[0];

		if(mode.equals("qb")) {
			QB[] qbs = {Players.SANCHEZ,Players.WEEDEN,Players.LEINART,Players.QUINN,
				Players.KOLB,Players.PALMER,Players.BRADY,Players.PEYTON,Players.RODGERS};
			QB[] sortedQBs = runQBs(qbs,args,qbFilename);
		} else if(mode.equals("rb")) {
			RB[] rbs = {Players.REDMAN,Players.HILLMAN,Players.MATHEWS,Players.JONESDREW,
				Players.RBUSH,Players.RICE,Players.LYNCH,Players.FOSTER};
			RB[] sortedRBs = runRBs(rbs,args,rbFilename);
		} else if(mode.equals("wr")) {
			WR[] wrs = {Players.BEDWARDS,Players.SHOLMES,Players.HDOUGLAS,Players.MANNINGHAM,
				Players.AMENDOLA,Players.JJONES,Players.DBRYANT,Players.CJOHNSON};
			WR[] sortedWRs = runWRs(wrs,args,wrFilename);
		} else if(mode.equals("def")) {
			DEF[] defs = {Players.OAKLAND,Players.NEWORLEANS,Players.JACKSONVILLE,
				Players.CLEVELAND,Players.SEATTLE,Players.SANFRAN,Players.CHICAGO};
			DEF[] sortedDEFs = runDEFs(defs,args,defFilename);
		} else if(mode.equals("k")) {
			K[] ks = {Players.CUNDIFF,Players.FOLK,Players.CROSBY,Players.FORBATH,
				Players.SCOBEE,Players.SUISHAM,Players.GOSTKOWSKI,Players.MBRYANT,Players.TUCKER};
			K[] sortedKs = runKs(ks,args,kFilename);
		} else {
			System.out.println("Error: Invalid mode");
			System.exit(1);
		}
	}

	//could pass in PrintStream instead of filename string to make switching to System.out. easy
	public static Player[] runPlayers(Player[] players, String[] args, String filename) {
		//make copy of players to preserve original order
		Player[] sortedPlayers = Arrays.copyOf(players,players.length);
		//evaluate all players
		int numPlayers = sortedPlayers.length;
		for(int i = 0; i < numPlayers; i++) {
			sortedPlayers[i].parseScoringCoeffsAndEvaluate(args);
		}
		Arrays.sort(sortedPlayers); //sort players based on score
		//write results to file
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(resultsDir,filename),true)));
			out.println(delimiter + "\n");
			out.println(toSectionHeader("Custom scoring coefficients"));
			out.println(sortedPlayers[0].categoriesToString()); //TODO: make this call static
			printCoeffs(args,out);
			/*out.println(toSectionHeader("Default scoring rules"));
			printArray(players,out);*/
			out.println(toSectionHeader("Custom scoring rules"));
			printArray(sortedPlayers,out);
			out.println(delimiter + "\n\n\n");
			out.close();
		} catch(IOException e) {
			System.out.println("IO exception when writing to file " + filename);
		}
		return sortedPlayers;
	}

	public static QB[] runQBs(QB[] qbs, String[] args, String filename)
	{
		return (QB[])runPlayers(qbs,args,filename);
	}

	public static RB[] runRBs(RB[] rbs, String[] args, String filename)
	{
		return (RB[])runPlayers(rbs,args,filename);
	}

	public static WR[] runWRs(WR[] wrs, String[] args, String filename)
	{
		return (WR[])runPlayers(wrs,args,filename);
	}

	public static DEF[] runDEFs(DEF[] defs, String[] args, String filename)
	{
		return (DEF[])runPlayers(defs,args,filename);
	}

	public static K[] runKs(K[] ks, String[] args, String filename)
	{
		return (K[])runPlayers(ks,args,filename);
	}

	//write players array to printwriter stream
	private static void printArray(Player[] players, PrintWriter out) {
		int numPlayers = players.length;
		for(int i = 0; i < numPlayers; i++) {
			out.println(players[i].toString());
		}
		out.println("\n");
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
