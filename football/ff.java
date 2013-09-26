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

	public static void main(String[] args)
	{
		if(args.length < 1) {
			System.out.println("Error: not mode specified");
			System.exit(1);
		}
		String mode = args[0];

		if(mode.equals("qb")) {
			QB[] qbs = {Players.SANCHEZ,Players.WEEDEN,Players.LEINART,Players.QUINN,
				Players.KOLB,Players.PALMER,Players.BRADY,Players.PEYTON,Players.RODGERS};
			QB[] sortedQBs = runQBs(args,qbs,qbFilename);
		} else if(mode.equals("rb")) {
			RB[] rbs = {Players.REDMAN,Players.HILLMAN,Players.MATHEWS,Players.JONESDREW,
				Players.RBUSH,Players.RICE,Players.LYNCH,Players.FOSTER};
			RB[] sortedRBs = runRBs(args,rbs,rbFilename);
		} else if(mode.equals("wr")) {
			WR[] wrs = {Players.BEDWARDS,Players.SHOLMES,Players.HDOUGLAS,Players.MANNINGHAM,
				Players.AMENDOLA,Players.JJONES,Players.DBRYANT,Players.CJOHNSON};
			WR[] sortedWRs = runWRs(args,wrs,wrFilename);
		} else if(mode.equals("def")) {
			DEF[] defs = {Players.OAKLAND,Players.NEWORLEANS,Players.JACKSONVILLE,
				Players.CLEVELAND,Players.SEATTLE,Players.SANFRAN,Players.CHICAGO};
			DEF[] sortedDEFs = runDEFs(args,defs,defFilename);
		} else if(mode.equals("k")) {
			K[] ks = {Players.CUNDIFF,Players.FOLK,Players.CROSBY,Players.FORBATH,
				Players.SCOBEE,Players.SUISHAM,Players.GOSTKOWSKI,Players.MBRYANT,Players.TUCKER};
			K[] sortedKs = runKs(args,ks,kFilename);
		} else {
			System.out.println("Error: Invalid mode");
			System.exit(1);
		}
	}

	//could pass in PrintStream instead of filename string to make switching to System.out. easy
	public static Player[] runPlayers(Player[] players, double[] coeffs, String filename) {
		//make copy of players to preserve original order
		Player[] sortedPlayers = Arrays.copyOf(players,players.length);
		//evaluate all players
		int numPlayers = sortedPlayers.length;
		for(int i = 0; i < numPlayers; i++) {
			sortedPlayers[i].evaluate(coeffs);
		}
		Arrays.sort(sortedPlayers); //sort players based on score
		//write results to file
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(new File(resultsDir,filename),true)));
			out.println("***** Custom scoring coefficients *****");
			out.println(players[0].statsCats());
			printCoeffs(coeffs,out);
			/*out.println(\n***** Default scoring rules *****");
			printArray(players,out);*/
			out.println("\n***** Custom scoring rules *****");
			printArray(sortedPlayers,out);
			out.println("\n********************************************\n");
			out.close();
		} catch(IOException e) {
			System.out.println("IO exception when writing to file " + filename);
		}
		return sortedPlayers;
	}

	public static QB[] runQBs(String[] args, QB[] qbs, String filename)
	{
		if(args.length < (qbs[0].getNumStats()+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		double compCE = Double.parseDouble(args[1]);
		double incCE = Double.parseDouble(args[2]);
		double ydsCE = Double.parseDouble(args[3])/QB.getYardsUnit();
		double tdCE = Double.parseDouble(args[4]);
		double interCE = Double.parseDouble(args[5]);
		double sckCE = Double.parseDouble(args[6]);
		double[] coeffs = {compCE,incCE,ydsCE,tdCE,interCE,sckCE};

		return (QB[])runPlayers(qbs,coeffs,filename);
	}

	public static RB[] runRBs(String[] args, RB[] rbs, String filename)
	{
		if(args.length < (rbs[0].getNumStats()+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		double attCE = Double.parseDouble(args[1]);
		double ydsCE = Double.parseDouble(args[2])/RB.getYardsUnit();
		double tdCE = Double.parseDouble(args[3]);
		double[] coeffs = {attCE,ydsCE,tdCE};

		return (RB[])runPlayers(rbs,coeffs,filename);
	}

	public static WR[] runWRs(String[] args, WR[] wrs, String filename)
	{
		if(args.length < (wrs[0].getNumStats()+1)) {
			System.out.println("Not enough arguments");
			System.exit(1);
		}
		double recCE = Double.parseDouble(args[1]);
		double ydsCE = Double.parseDouble(args[2])/WR.getYardsUnit();
		double tdCE = Double.parseDouble(args[3]);
		double[] coeffs = {recCE,ydsCE,tdCE};

		return (WR[])runPlayers(wrs,coeffs,filename);
	}

	public static DEF[] runDEFs(String[] args, DEF[] defs, String filename)
	{
		if(args.length < (defs[0].getNumStats()+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		double sckCE = Double.parseDouble(args[1]);
		double intCE = Double.parseDouble(args[2]);
		double fumbCE = Double.parseDouble(args[3]);
		double safCE = Double.parseDouble(args[4]);
		double tdCE = Double.parseDouble(args[5]);
		double retCE = Double.parseDouble(args[6]);
		double ptsCE = Double.parseDouble(args[7])/DEF.getPointsUnit();
		double ydsCE = Double.parseDouble(args[8])/DEF.getYardsUnit();
		double[] coeffs = {sckCE,intCE,fumbCE,safCE,tdCE,retCE,ptsCE,ydsCE};

		return (DEF[])runPlayers(defs,coeffs,filename);
	}

	public static K[] runKs(String[] args, K[] ks, String filename)
	{
		if(args.length < (ks[0].getNumStats()+1)) {
			System.out.println("Error: Not enough arguments");
			System.exit(1);
		}
		double patmdCE = Double.parseDouble(args[1]);
		double patmsCE = Double.parseDouble(args[2]);
		double fgmd1CE = Double.parseDouble(args[3]);
		double fgmd2CE = Double.parseDouble(args[4]);
		double fgmd3CE = Double.parseDouble(args[5]);
		double fgmd4CE = Double.parseDouble(args[6]);
		double fgmd5CE = Double.parseDouble(args[7]);
		double fgms1CE = Double.parseDouble(args[8]);
		double fgms2CE = Double.parseDouble(args[9]);
		double fgms3CE = Double.parseDouble(args[10]);
		double fgms4CE = Double.parseDouble(args[11]);
		double fgms5CE = Double.parseDouble(args[12]);
		double[] coeffs = {patmdCE,patmsCE,fgmd1CE,fgmd2CE,fgmd3CE,fgmd4CE,fgmd5CE,fgms1CE,fgms2CE,fgms3CE,fgms4CE,fgms5CE};

		return (K[])runPlayers(ks,coeffs,filename);
	}

	//write array to printwriter stream
	private static void printArray(Player[] players, PrintWriter out) {
		int numPlayers = players.length;
		for(int i = 0; i < numPlayers; i++) {
			out.println(players[i].toString());
		}
	}

	//write array to printwriter stream
	private static void printCoeffs(double[] coeffs, PrintWriter out) {
		int numCoeffs = coeffs.length;
		for(int i = 0; i < numCoeffs; i++) {
			out.print(coeffs[i] + "\t\t");
		}
		out.println();
	}
}
