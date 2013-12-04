package football;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import static com.google.common.base.Preconditions.checkPositionIndex;

import football.players.*;
import football.stats.RuleMap;
import football.util.logging.ResultsLogger;
import football.util.metrics.Metric;
import football.util.metrics.SortOrderMetric;

public class CustomScoringHelper
{
	public CustomScoringHelper() { }

	// TODO: make mode and rules inputs when/if we make a GUI (or have setters)
	public void run(String[] args) {
		checkPositionIndex(0, args.length, "mode not specified\n" + getUsage());
		String mode = args[0];

		RuleMap rules = parseScoringRules(mode, args);
		List<Player> defaultPlayers = getPlayers(mode);
		// make copy of players to preserve original order
		List<Player> customPlayers = deepCopyList(defaultPlayers);
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
		String filename = (mode.toUpperCase() + "results.txt");
		try {
			ResultsLogger logger = new ResultsLogger(resultsDirectory,filename);
			logger.logResults(args,defaultPlayers,customPlayers,distance);
			logger.close();
		} catch(IOException e) {
			e.printStackTrace();
		}
	}

	// get list of players to score based on input mode
	private List<Player> getPlayers(String mode) {
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
			throw new IllegalArgumentException("Error: Invalid mode\n" + getUsage());
		}

		// put players into list
		List<Player> playersList = new ArrayList<Player>(Arrays.asList(players));
		return playersList;
	}

	// parse scoring rules from args based on input mode
	private RuleMap parseScoringRules(String mode, String[] args) {
		RuleMap rules = null;
		if(mode.equals("qb")) {
			rules = QB.parseScoringRules(args);
		} else if(mode.equals("rb")) {
			rules = RB.parseScoringRules(args);
		} else if(mode.equals("wr")) {
			rules = WR.parseScoringRules(args);
		} else if(mode.equals("def")) {
			rules = DEF.parseScoringRules(args);
		} else if(mode.equals("k")) {
			rules = K.parseScoringRules(args);
		} else {
			throw new IllegalArgumentException("Error: Invalid mode\n" + getUsage());
		}

		return rules;
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
