import java.util.Arrays;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class ff
{
	//QB
	public static final QB brady = new QB("brady",new int[]{401,236,4827,34,8,27});
	public static final QB peyton = new QB("peyton",new int[]{400,183,4659,37,11,21});
	public static final QB rodgers = new QB("rodgers",new int[]{371,181,4295,39,8,51});
	public static final QB palmer = new QB("palmer",new int[]{345,220,4018,22,14,26});
	public static final QB kolb = new QB("kolb",new int[]{109,74,1169,8,3,27});
	public static final QB quinn = new QB("quinn",new int[]{112,85,1141,2,8,21});
	public static final QB leinart = new QB("leinart",new int[]{16,17,115,0,1,1});
	//RB
	public static final RB lynch = new RB("lynch",new int[]{315,1590,11});
	public static final RB rice = new RB("rice",new int[]{257,1143,9});
	public static final RB foster = new RB("foster",new int[]{351,1424,15});
	public static final RB rbush = new RB("r bush",new int[]{227,986,6});
	public static final RB vereen = new RB("vereen",new int[]{62,251,3});
	public static final RB jonesdrew = new RB("jones-drew",new int[]{86,414,1});
	public static final RB hillman = new RB("hillman",new int[]{85,330,1});
	//WR
	public static final WR cjohnson = new WR("c johnson",new int[]{122,1964,5});
	public static final WR jjones = new WR("j jones",new int[]{79,1198,10});
	public static final WR bryant = new WR("bryant",new int[]{92,1382,12});
	public static final WR amendola = new WR("amendola",new int[]{63,666,3});
	public static final WR bedwards = new WR("b edwards",new int[]{18,199,1});
	public static final WR ssmith = new WR("s smith",new int[]{14,131,0});
	public static final WR sholmes = new WR("s holmes",new int[]{20,272,1});
	//files to write results to
	public static final String qbFilename = "QBresults.txt";
	public static final String rbFilename = "RBresults.txt";
	public static final String wrFilename = "WRresults.txt";

	public static void main(String[] args)
	{
		if(args.length < 1) {
			System.out.println("Error: not mode specified");
			System.exit(1);
		}
		String mode = args[0];

		if(mode.equals("qb")) {
			QB[] qbs = {leinart,quinn,kolb,palmer,brady,peyton,rodgers};
			QB[] sortedQBs = runQBs(args,qbs,qbFilename);
		} else if(mode.equals("rb")) {
			RB[] rbs = {hillman,vereen,jonesdrew,rbush,rice,lynch,foster};
			RB[] sortedRBS = runRBs(args,rbs,rbFilename);
		} else if(mode.equals("wr")) {
			WR[] wrs = {ssmith,bedwards,sholmes,amendola,jjones,bryant,cjohnson};
			WR[] sortedWRs = runWRs(args,wrs,wrFilename);
		} else {
			System.out.println("Error: Invalid mode");
			System.exit(1);
		}
	}

	public static Player[] runPlayers(Player[] players, double[] coeffs, String filename) {
		//make copy of players to preserve original order
		Player[] sortedPlayers = Arrays.copyOf(players,players.length);
		//evaluate all players
		int numPlayers = sortedPlayers.length;
		for(int i = 0; i < numPlayers; i++) {
			sortedPlayers[i].evaluate(coeffs);
		}
		Arrays.sort(sortedPlayers); //sort players based on score
		try {
			PrintWriter out = new PrintWriter(new BufferedWriter(new FileWriter(filename,true)));
			//print results
			out.println("***** Custom scoring coefficients *****");
			out.println(players[0].statsCats());
			printCoeffs(coeffs,out);
			/*out.println(\n***** Default scoring rules *****");
			printArray(players,out);*/
			out.println("\n***** Custom scoring rules *****");
			printArray(sortedPlayers,out);
			out.println("\n\n");
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
