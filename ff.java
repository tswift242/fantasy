import java.util.Arrays;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
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
	public static final QB sanchez = new QB("sanchez",new int[]{246,207,2883,13,18,34});
	public static final QB weeden = new QB("weeden",new int[]{297,220,3385,14,17,28});
	//RB
	public static final RB lynch = new RB("lynch",new int[]{315,1590,11});
	public static final RB rice = new RB("rice",new int[]{257,1143,9});
	public static final RB foster = new RB("foster",new int[]{351,1424,15});
	public static final RB rbush = new RB("r bush",new int[]{227,986,6});
	public static final RB mathews = new RB("mathews",new int[]{184,707,1});
	public static final RB jonesdrew = new RB("jones-drew",new int[]{86,414,1});
	public static final RB hillman = new RB("hillman",new int[]{85,330,1});
	public static final RB redman = new RB("redman",new int[]{110,410,2});
	//WR
	public static final WR cjohnson = new WR("c johnson",new int[]{122,1964,5});
	public static final WR jjones = new WR("j jones",new int[]{79,1198,10});
	public static final WR dbryant = new WR("d bryant",new int[]{92,1382,12});
	public static final WR amendola = new WR("amendola",new int[]{63,666,3});
	public static final WR bedwards = new WR("b edwards",new int[]{18,199,1});
	public static final WR manningham = new WR("manningham",new int[]{42,449,1});
	public static final WR sholmes = new WR("s holmes",new int[]{20,272,1});
	public static final WR hdouglas = new WR("h douglas",new int[]{38,395,1});
	//DEF
	public static final DEF chicago = new DEF("chicago",new int[]{41,24,20,0,10,0,255,5050});
	public static final DEF sanfran = new DEF("san fransisco",new int[]{38,14,11,1,4,0,265,4710});
	public static final DEF seattle = new DEF("seattle",new int[]{36,18,13,0,5,0,255,5050});
	public static final DEF cleveland = new DEF("cleveland",new int[]{38,17,12,0,2,1,356,5821});
	public static final DEF neworleans = new DEF("new orleans",new int[]{30,15,11,0,5,0,434,7042});
	public static final DEF jacksonville = new DEF("jacksonville",new int[]{20,12,11,0,1,0,414,6088});
	public static final DEF oakland = new DEF("oakland",new int[]{25,11,8,0,0,0,431,5672});
	//K
	public static final K gostkowski = new K("gostkowski",new int[]{66,0,0,8,10,9,2,0,0,2,4,0});
	public static final K mbryant = new K("m bryant",new int[]{44,0,1,8,10,10,4,0,1,1,3,0});
	public static final K tucker = new K("tucker",new int[]{42,0,0,8,8,10,4,0,0,0,3,0});
	public static final K suisham = new K("suisham",new int[]{34,0,0,7,8,12,1,0,1,0,0,2});
	public static final K cundiff = new K("cundiff",new int[]{17,0,0,1,3,3,0,0,0,2,1,2});
	public static final K forbath = new K("forbath",new int[]{33,1,0,3,2,11,1,0,0,1,0,0});
	public static final K scobee = new K("scobee",new int[]{18,1,1,4,8,11,1,0,0,0,2,1});
	public static final K folk = new K("folk",new int[]{30,0,0,7,6,5,3,0,1,2,2,1});
	public static final K crosby = new K("crosby",new int[]{50,0,0,5,5,9,2,0,0,2,3,7});
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
			QB[] qbs = {sanchez,weeden,leinart,quinn,kolb,palmer,brady,peyton,rodgers};
			QB[] sortedQBs = runQBs(args,qbs,qbFilename);
		} else if(mode.equals("rb")) {
			RB[] rbs = {redman, hillman,mathews,jonesdrew,rbush,rice,lynch,foster};
			RB[] sortedRBs = runRBs(args,rbs,rbFilename);
		} else if(mode.equals("wr")) {
			WR[] wrs = {bedwards,sholmes,hdouglas,manningham,amendola,jjones,dbryant,cjohnson};
			WR[] sortedWRs = runWRs(args,wrs,wrFilename);
		} else if(mode.equals("def")) {
			DEF[] defs = {oakland,neworleans,jacksonville,cleveland,seattle,sanfran,chicago};
			DEF[] sortedDEFs = runDEFs(args,defs,defFilename);
		} else if(mode.equals("k")) {
			K[] ks = {cundiff,folk,crosby,forbath,scobee,suisham,gostkowski,mbryant,tucker};
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
