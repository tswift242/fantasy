package football;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import static com.google.common.base.Preconditions.checkPositionIndex;

import football.players.*;
import football.players.modes.Modes;
import football.stats.RuleMap;
import football.util.logging.ResultsLogger;
import football.util.metrics.Metric;
import football.util.metrics.SortOrderMetric;

public final class CustomScoringHelper
{
	// map of player modes to corresponding lists of players
	private static Map<Modes,List<Player>> modesToPlayersMap;
	// copy of the above map containing copy player lists
	private static Map<Modes,List<Player>> modesToPlayersMap2;

	public CustomScoringHelper() {
		//TODO: add mappings for these dynamically as the modes are used in 
		//run(), instead of statically adding all possible mappings at 
		//construction time
		modesToPlayersMap = initModesToPlayersMap();
		modesToPlayersMap2 = deepCopyModesMap(modesToPlayersMap);
	}

	// TODO: make mode and rules inputs when/if we make a GUI (or have setters)
	public void run(String[] args) {
		checkPositionIndex(0, args.length, "mode not specified\n" + getUsage());
		Modes mode = Modes.fromString(args[0]);

		RuleMap rules = mode.parseScoringRules(args);
		/*if(!modesToPlayersMap.containsKey(mode)) {
			addMapping(mode); // adds mapping for mode
		}
		List<Player> defaultPlayers = modesToPlayersMap.get(mode);*/
		List<Player> defaultPlayers = modesToPlayersMap.get(mode);
		// make copy of players to preserve original order
		List<Player> customPlayers = modesToPlayersMap2.get(mode);
		//evaluate all players with custom rules
		scorePlayers(customPlayers, rules);
		// sort players according to default rules
		Collections.sort(defaultPlayers);
		// sort players according to custom rules
		Collections.sort(customPlayers);
		// calculate (dis)similarity btw customPlayers and defaultPlayers
		Metric metric = new SortOrderMetric();
		double distance = metric.distance(defaultPlayers,customPlayers);
		// write results to file filename in directory resultsDirectory
		String resultsDirectory = System.getProperty("user.dir") + System.getProperty("file.separator") + "results";
		String filename = (mode.toString() + "results.txt");
		try {
			ResultsLogger logger = new ResultsLogger(resultsDirectory,filename);
			logger.logResults(args,defaultPlayers,customPlayers,distance);
			logger.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// creates and initializes a map a player modes to corresponding lists of players
	// to evaluate for that mode
	private Map<Modes,List<Player>> initModesToPlayersMap() {
		Map<Modes,List<Player>> map = new EnumMap<Modes,List<Player>>(Modes.class);
		// form list of players for each player position
		QB[] qbs = new QB[]{Players.SANCHEZ,Players.WEEDEN,Players.LEINART,Players.QUINN,
					Players.KOLB,Players.PALMER,Players.BRADY,Players.PEYTON,Players.RODGERS};
		List<Player> qbList = new ArrayList<Player>(Arrays.asList(qbs));
		RB[] rbs = new RB[]{Players.REDMAN,Players.HILLMAN,Players.MATHEWS,Players.JONESDREW,
			Players.RBUSH,Players.RICE,Players.LYNCH,Players.FOSTER};
		List<Player> rbList = new ArrayList<Player>(Arrays.asList(rbs));
		WR[] wrs = new WR[]{Players.BEDWARDS,Players.SHOLMES,Players.HDOUGLAS,Players.MANNINGHAM,
			Players.AMENDOLA,Players.JJONES,Players.DBRYANT,Players.CJOHNSON};
		List<Player> wrList = new ArrayList<Player>(Arrays.asList(wrs));
		K[] ks = new K[]{Players.CUNDIFF,Players.FOLK,Players.CROSBY,Players.FORBATH,
			Players.SCOBEE,Players.SUISHAM,Players.GOSTKOWSKI,Players.MBRYANT,Players.TUCKER};
		List<Player> kList = new ArrayList<Player>(Arrays.asList(ks));
		DEF[] defs = new DEF[]{Players.OAKLAND,Players.NEWORLEANS,Players.JACKSONVILLE,
			Players.CLEVELAND,Players.SEATTLE,Players.SANFRAN,Players.CHICAGO};
		List<Player> defList = new ArrayList<Player>(Arrays.asList(defs));
		// create list of all players for Modes.ALL mode
		List<Player> playersList = new ArrayList<Player>();
		playersList.addAll(qbList);
		playersList.addAll(rbList);
		playersList.addAll(wrList);
		playersList.addAll(kList);
		playersList.addAll(defList);
		// map mode to corresponding list of players
		map.put(Modes.QB,qbList);
		map.put(Modes.RB,rbList);
		map.put(Modes.WR,wrList);
		map.put(Modes.K,kList);
		map.put(Modes.DEF,defList);
		map.put(Modes.ALL,playersList);
		return map;
	}

	// creates a deep copy of the input modes map by deep copying each players list 
	// in map.values() and reassociating the list copy with the same mode enum in a
	// new map
	private Map<Modes,List<Player>> deepCopyModesMap(Map<Modes,List<Player>> map) {
		Map<Modes,List<Player>> mapCopy = new EnumMap<Modes,List<Player>>(Modes.class);
		// rebuild list of all players into list below by adding in every list we copy
		List<Player> playersList = new ArrayList<Player>();
		for(Modes mode : map.keySet()) {
			// skip over list for Modes.ALL so that we avoid deep copying every player
			// a second time, and instead incrementally build the listCopy for this case
			// from all the other cases
			if(mode != Modes.ALL) {
				List<Player> listCopy = deepCopyList(map.get(mode));
				mapCopy.put(mode,listCopy);
				playersList.addAll(listCopy);
			}
		}
		mapCopy.put(Modes.ALL,playersList);
		return mapCopy;
	}

	// Make deep copy of list of players. 
	// Would work for any List<E> where E defines a deepCopy() method
	private List<Player> deepCopyList(List<Player> list) {
		List<Player> listCopy = new ArrayList<Player>(list.size());
		for(Player element : list) {
			listCopy.add(element.deepCopy());
		}
		return listCopy;
	}

	// assign each player in players a score using the scoring rules in rules
	private void scorePlayers(List<Player> players, RuleMap rules) {
		for(Player player : players) {
			player.evaluate(rules);
		}
	}

	// get string detailing command line usage
	private String getUsage() {
		String result = "";
		String indent = "\t\t";
		result += "Usage: java FantasyFootballCustomScoringHelper <mode> <sc1> ... <scN>,\twhere\n";
		result += (indent + "mode := player position to simulate. Possible values are def, k, qb, rb, and wr.\n");
		result += (indent + "sc1 - scN := scoring coefficients representing all rules needed to score players" + 
				" at position given by mode. Necessary scoring coefficients per position are given below.\n");
		/*result += (indent + "def: " + DEF.categoriesToString() + "\n");
		result += (indent + "k: " + K.categoriesToString() + "\n");
		result += (indent + "qb: " + QB.categoriesToString() + "\n");
		result += (indent + "rb: " + RB.categoriesToString() + "\n");
		result += (indent + "wr: " + WR.categoriesToString() + "\n");*/
		return result;
	}
}
