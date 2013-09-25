package football;

import java.util.Arrays;
import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.LinkedHashSet;

import football.players.*;
import football.categories.*;

public class ff
{
	//QB
	public static final QB brady = new QB("brady",
			newSet(new Stat<Pass>(Pass.COMP,401),new Stat<Pass>(Pass.INC,236),new Stat<Pass>(Pass.YDS,4827),new Stat<Pass>(Pass.TD,34),new Stat<Pass>(Pass.INT,8),new Stat<Pass>(Pass.SCK,27)),
			newSet(new Stat<Rush>(Rush.ATT,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB peyton = new QB("peyton",
			newSet(new Stat<Pass>(Pass.COMP,400),new Stat<Pass>(Pass.INC,183),new Stat<Pass>(Pass.YDS,4659),new Stat<Pass>(Pass.TD,37),new Stat<Pass>(Pass.INT,11),new Stat<Pass>(Pass.SCK,21)),
			
			newSet(new Stat<Rush>(Rush.ATT,0)),newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB rodgers = new QB("rodgers",
		newSet(new Stat<Pass>(Pass.COMP,371),new Stat<Pass>(Pass.INC,181),new Stat<Pass>(Pass.YDS,4295),new Stat<Pass>(Pass.TD,39),new Stat<Pass>(Pass.INT,8),new Stat<Pass>(Pass.SCK,51)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB palmer = new QB("palmer",
		newSet(new Stat<Pass>(Pass.COMP,345),new Stat<Pass>(Pass.INC,220),new Stat<Pass>(Pass.YDS,4018),new Stat<Pass>(Pass.TD,22),new Stat<Pass>(Pass.INT,14),new Stat<Pass>(Pass.SCK,26)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB kolb = new QB("kolb",
		newSet(new Stat<Pass>(Pass.COMP,109),new Stat<Pass>(Pass.INC,74),new Stat<Pass>(Pass.YDS,1169),new Stat<Pass>(Pass.TD,8),new Stat<Pass>(Pass.INT,3),new Stat<Pass>(Pass.SCK,27)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB quinn = new QB("quinn",
		newSet(new Stat<Pass>(Pass.COMP,112),new Stat<Pass>(Pass.INC,85),new Stat<Pass>(Pass.YDS,1141),new Stat<Pass>(Pass.TD,2),new Stat<Pass>(Pass.INT,8),new Stat<Pass>(Pass.SCK,21)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB leinart = new QB("leinart",
		newSet(new Stat<Pass>(Pass.COMP,16),new Stat<Pass>(Pass.INC,17),new Stat<Pass>(Pass.YDS,115),new Stat<Pass>(Pass.TD,0),new Stat<Pass>(Pass.INT,1),new Stat<Pass>(Pass.SCK,1)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB sanchez = new QB("sanchez",
		newSet(new Stat<Pass>(Pass.COMP,246),new Stat<Pass>(Pass.INC,207),new Stat<Pass>(Pass.YDS,2883),new Stat<Pass>(Pass.TD,13),new Stat<Pass>(Pass.INT,18),new Stat<Pass>(Pass.SCK,34)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final QB weeden = new QB("weeden",
		newSet(new Stat<Pass>(Pass.COMP,297),new Stat<Pass>(Pass.INC,220),new Stat<Pass>(Pass.YDS,3385),new Stat<Pass>(Pass.TD,14),new Stat<Pass>(Pass.INT,17),new Stat<Pass>(Pass.SCK,28)),
		newSet(new Stat<Rush>(Rush.ATT,0)),
		newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	//RB
	public static final RB lynch = new RB("lynch",
			newSet(new Stat<Rush>(Rush.ATT,315),new Stat<Rush>(Rush.YDS,1590),new Stat<Rush>(Rush.TD,11)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB rice = new RB("rice",
			newSet(new Stat<Rush>(Rush.ATT,257),new Stat<Rush>(Rush.YDS,1143),new Stat<Rush>(Rush.TD,9)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB foster = new RB("foster",
			newSet(new Stat<Rush>(Rush.ATT,351),new Stat<Rush>(Rush.YDS,1424),new Stat<Rush>(Rush.TD,15)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB rbush = new RB("r bush",
			newSet(new Stat<Rush>(Rush.ATT,227),new Stat<Rush>(Rush.YDS,986),new Stat<Rush>(Rush.TD,6)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB mathews = new RB("mathews",
			newSet(new Stat<Rush>(Rush.ATT,184),new Stat<Rush>(Rush.YDS,707),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB jonesdrew = new RB("jones-drew",
			newSet(new Stat<Rush>(Rush.ATT,86),new Stat<Rush>(Rush.YDS,414),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB hillman = new RB("hillman",
			newSet(new Stat<Rush>(Rush.ATT,85),new Stat<Rush>(Rush.YDS,330),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final RB redman = new RB("redman",
			newSet(new Stat<Rush>(Rush.ATT,110),new Stat<Rush>(Rush.YDS,410),new Stat<Rush>(Rush.TD,2)),
			newSet(new Stat<Rec>(Rec.REC,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	//WR
	public static final WR cjohnson = new WR("c johnson",
			newSet(new Stat<Rec>(Rec.REC,122),new Stat<Rec>(Rec.YDS,1964),new Stat<Rec>(Rec.TD,5)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO 
	public static final WR jjones = new WR("j jones",
			newSet(new Stat<Rec>(Rec.REC,79),new Stat<Rec>(Rec.YDS,1198),new Stat<Rec>(Rec.TD,10)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final WR dbryant = new WR("d bryant",
			newSet(new Stat<Rec>(Rec.REC,92),new Stat<Rec>(Rec.YDS,1382),new Stat<Rec>(Rec.TD,12)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final WR amendola = new WR("amendola",
			newSet(new Stat<Rec>(Rec.REC,63),new Stat<Rec>(Rec.YDS,666),new Stat<Rec>(Rec.TD,3)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final WR bedwards = new WR("b edwards",
			newSet(new Stat<Rec>(Rec.REC,18),new Stat<Rec>(Rec.YDS,199),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final WR manningham = new WR("manningham",
			newSet(new Stat<Rec>(Rec.REC,42),new Stat<Rec>(Rec.YDS,449),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final WR sholmes = new WR("s holmes",
			newSet(new Stat<Rec>(Rec.REC,20),new Stat<Rec>(Rec.YDS,272),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	public static final WR hdouglas = new WR("h douglas",
			newSet(new Stat<Rec>(Rec.REC,38),new Stat<Rec>(Rec.YDS,395),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0))); //TODO
	//DEF
	public static final DEF chicago = new DEF("chicago",
			newSet(new Stat<Def>(Def.SCK,41),new Stat<Def>(Def.INT,24),new Stat<Def>(Def.FUMB,20),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,10),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,255),new Stat<Def>(Def.YDS,5050)));
	public static final DEF sanfran = new DEF("san fransisco",
			newSet(new Stat<Def>(Def.SCK,38),new Stat<Def>(Def.INT,14),new Stat<Def>(Def.FUMB,11),new Stat<Def>(Def.SAF,1),new Stat<Def>(Def.TD,4),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,265),new Stat<Def>(Def.YDS,4710)));
	public static final DEF seattle = new DEF("seattle",
			newSet(new Stat<Def>(Def.SCK,36),new Stat<Def>(Def.INT,18),new Stat<Def>(Def.FUMB,13),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,5),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,255),new Stat<Def>(Def.YDS,5050)));
	public static final DEF cleveland = new DEF("cleveland",
			newSet(new Stat<Def>(Def.SCK,38),new Stat<Def>(Def.INT,17),new Stat<Def>(Def.FUMB,12),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,2),new Stat<Def>(Def.RET,1),new Stat<Def>(Def.PTS,356),new Stat<Def>(Def.YDS,5821)));
	public static final DEF neworleans = new DEF("new orleans",
			newSet(new Stat<Def>(Def.SCK,30),new Stat<Def>(Def.INT,15),new Stat<Def>(Def.FUMB,11),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,5),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,434),new Stat<Def>(Def.YDS,7042)));
	public static final DEF jacksonville = new DEF("jacksonville",
			newSet(new Stat<Def>(Def.SCK,20),new Stat<Def>(Def.INT,12),new Stat<Def>(Def.FUMB,11),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,1),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,414),new Stat<Def>(Def.YDS,6088)));
	public static final DEF oakland = new DEF("oakland",
			newSet(new Stat<Def>(Def.SCK,25),new Stat<Def>(Def.INT,11),new Stat<Def>(Def.FUMB,8),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,0),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,431),new Stat<Def>(Def.YDS,5672)));
	//K
	public static final K gostkowski = new K("gostkowski",
			newSet(new Stat<Kick>(Kick.PAT_MD,66),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,8),new Stat<Kick>(Kick.FG_MD_30,10),new Stat<Kick>(Kick.FG_MD_40,9),new Stat<Kick>(Kick.FG_MD_50,2),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,4),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K mbryant = new K("m bryant",
			newSet(new Stat<Kick>(Kick.PAT_MD,44),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,1),new Stat<Kick>(Kick.FG_MD_20,8),new Stat<Kick>(Kick.FG_MD_30,10),new Stat<Kick>(Kick.FG_MD_40,10),new Stat<Kick>(Kick.FG_MD_50,4),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,1),new Stat<Kick>(Kick.FG_MS_30,1),new Stat<Kick>(Kick.FG_MS_40,3),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K tucker = new K("tucker",
			newSet(new Stat<Kick>(Kick.PAT_MD,42),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,8),new Stat<Kick>(Kick.FG_MD_30,8),new Stat<Kick>(Kick.FG_MD_40,10),new Stat<Kick>(Kick.FG_MD_50,4),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,0),new Stat<Kick>(Kick.FG_MS_40,3),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K suisham = new K("suisham",
			newSet(new Stat<Kick>(Kick.PAT_MD,34),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,7),new Stat<Kick>(Kick.FG_MD_30,8),new Stat<Kick>(Kick.FG_MD_40,12),new Stat<Kick>(Kick.FG_MD_50,1),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,1),new Stat<Kick>(Kick.FG_MS_30,0),new Stat<Kick>(Kick.FG_MS_40,0),new Stat<Kick>(Kick.FG_MS_50,2)));
	public static final K cundiff = new K("cundiff",
			newSet(new Stat<Kick>(Kick.PAT_MD,17),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,1),new Stat<Kick>(Kick.FG_MD_30,3),new Stat<Kick>(Kick.FG_MD_40,3),new Stat<Kick>(Kick.FG_MD_50,0),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,1),new Stat<Kick>(Kick.FG_MS_50,2)));
	public static final K forbath = new K("forbath",
			newSet(new Stat<Kick>(Kick.PAT_MD,33),new Stat<Kick>(Kick.PAT_MS,1),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,3),new Stat<Kick>(Kick.FG_MD_30,2),new Stat<Kick>(Kick.FG_MD_40,11),new Stat<Kick>(Kick.FG_MD_50,1),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,1),new Stat<Kick>(Kick.FG_MS_40,0),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K scobee = new K("scobee",
			newSet(new Stat<Kick>(Kick.PAT_MD,18),new Stat<Kick>(Kick.PAT_MS,1),new Stat<Kick>(Kick.FG_MD_0,1),new Stat<Kick>(Kick.FG_MD_20,4),new Stat<Kick>(Kick.FG_MD_30,8),new Stat<Kick>(Kick.FG_MD_40,11),new Stat<Kick>(Kick.FG_MD_50,1),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,0),new Stat<Kick>(Kick.FG_MS_40,2),new Stat<Kick>(Kick.FG_MS_50,1)));
	public static final K folk = new K("folk",
			newSet(new Stat<Kick>(Kick.PAT_MD,30),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,7),new Stat<Kick>(Kick.FG_MD_30,6),new Stat<Kick>(Kick.FG_MD_40,5),new Stat<Kick>(Kick.FG_MD_50,3),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,1),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,2),new Stat<Kick>(Kick.FG_MS_50,1)));
	public static final K crosby = new K("crosby",
			newSet(new Stat<Kick>(Kick.PAT_MD,50),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,5),new Stat<Kick>(Kick.FG_MD_30,5),new Stat<Kick>(Kick.FG_MD_40,9),new Stat<Kick>(Kick.FG_MD_50,2),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,3),new Stat<Kick>(Kick.FG_MS_50,7)));
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

	//utility for easy set construction
	//constructs LinkedHashSet to maintain insertion order
	private static <T extends Enum<T>> LinkedHashSet<Stat<T>> newSet(Stat<T> ... stats) {
		LinkedHashSet<Stat<T>> set = new LinkedHashSet<Stat<T>>();
		for(Stat<T> stat : stats) {
			set.add(stat);
		}
		return set;
	}
}
