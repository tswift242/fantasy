package football.core;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import static com.google.common.base.Preconditions.checkPositionIndex;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.*; // for creating DEFAULT_RULES
import football.players.modes.Modes;
import football.stats.Rule; // for creating DEFAULT_RULES
import football.stats.RuleMap;
import football.stats.StatType;
import football.stats.categories.*;
import football.util.logging.ResultsLogger;
import football.util.metrics.Metric;
import football.util.metrics.SortOrderMetric;

public final class CustomScoringHelperModel
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	// rule map containing default rules, according to NFL.com
	private static final RuleMap DEFAULT_RULES = initDefaultRuleMap();
	private static final Modes DEFAULT_MODE = Modes.QB;

	//TODO: make these maps map to List<E extends Player> if we drop Modes.ALL
	//TODO: make these lists static?
	// map of player modes to corresponding lists of players
	private Map<Modes,List<Player>> modesToPlayersMap;
	// copy of the above map containing copy player lists
	private Map<Modes,List<Player>> modesToPlayersMap2;

	private RuleMap currentRules;
	private Modes currentMode;

	public CustomScoringHelperModel() {
		logger.info("Constructing model with default mode {}", DEFAULT_MODE.toString());
		currentMode = DEFAULT_MODE;
		// init to default rules (don't simply assign to prevent DEFAULT_RULES from being 
		// modified whenever currentRules is modified)
		currentRules = initDefaultRuleMap();
		modesToPlayersMap = new EnumMap<Modes,List<Player>>(Modes.class);
		modesToPlayersMap2 = new EnumMap<Modes,List<Player>>(Modes.class);
		populateModesToPlayersMap();
		logger.debug("Using default rule map of:\n{}", DEFAULT_RULES.toString());
	}

	// command line version
	public void run(String[] args) {
		logger.info("Running model with args: {}", Arrays.toString(args));
		checkPositionIndex(0, args.length, "mode not specified\n" + getUsage());
		Modes mode = Modes.fromString(args[0]);
		// parse scoring rules relevant to this mode
		RuleMap rules = mode.parseScoringRules(args);

		ScoringResults results = run(mode, rules);
		// log results
		logResults(results);
	}

	// GUI version
	private ScoringResults run(Modes mode, RuleMap rules) {
		logger.info("Running model with mode and custom rules: {}\n{}", mode.toString(), rules.toString());
		// get corresponding lists of players for this mode
		List<Player> players1 = modesToPlayersMap.get(mode);
		List<Player> players2 = modesToPlayersMap2.get(mode);
		// evaluate all players in each list with the corresponding set of rules
		//TODO: pass in rules for players1 instead of always using DEFAULT_RULES
		scorePlayers(players1,DEFAULT_RULES);
		scorePlayers(players2,rules);
		// sort players according to their scores
		//TODO: delay sorting until logging in ResultsLogger, as sorting done by View anyway
		Collections.sort(players1);
		logger.info("Players sorted by default rules:\n{}", players1.toString());
		Collections.sort(players2);
		logger.info("Players sorted by custom rules:\n{}", players2.toString());
		// calculate (dis)similarity between players1 and players2
		Metric metric = new SortOrderMetric();
		double distance = metric.distance(players1,players2);
		logger.info("Distance between players using {}: {}", metric.getClass().getName(), distance);

		// return results so that view can be updated
		return new ScoringResults(mode, rules, players1, players2, distance);
	}

	// run using currentMode and currentRules
	public ScoringResults run() {
		return run(currentMode, currentRules);
	}

	// write results to file filename in directory resultsDirectory
	public void logResults(ScoringResults results, String resultsDirectory, String filename) {
		String fileSeparator = System.getProperty("file.separator");
		logger.info("Writing results to {}{}{}", resultsDirectory, fileSeparator, filename);

		try {
			ResultsLogger logger = new ResultsLogger(resultsDirectory,filename);
			logger.logResults(results);
			logger.close();
		} catch(IOException e) {
			logger.error("Unable to write results: {}", e.toString());
		}
	}

	// write results to default file in default directory
	public void logResults(ScoringResults results) {
		String fileSeparator = System.getProperty("file.separator");
		String resultsDirectory = System.getProperty("user.dir") + fileSeparator + "results";
		String filename = (results.getMode().toString() + "results.txt");

		logResults(results, resultsDirectory, filename);
	}

	/*
	 * Getters
	 */
	public RuleMap getDefaultRules() {
		return DEFAULT_RULES;
	}

	public Modes getDefaultMode() {
		return DEFAULT_MODE;
	}

	public Map<Modes,List<Player>> getModesToPlayersMap() {
		return modesToPlayersMap;
	}

	/*
	 * Setters
	 */
	public void setMode(Modes mode) {
		currentMode = mode;
	}

	public <T extends Enum<T> & StatType> void setRule(T category, Rule<T> rule) {
		currentRules.put(category, rule);
	}

	public void setRuleMap(RuleMap rules) {
		currentRules = rules;
	}


	// assign each player in players a score using the scoring rules in rules
	private void scorePlayers(List<Player> players, RuleMap rules) {
		for(Player player : players) {
			// if we're using default rules, "look up" correct score by using
			// the saved defaultScore of each player instead of computing the
			// score from scratch
			if(rules.equals(DEFAULT_RULES)) {
				player.useDefaultScore();
			} else {
				player.evaluate(rules);
			}
		}
	}

	// adds mappings into modes map, and then accumulates the players lists 
	// from these mappings in order to add a mapping for mode Modes.ALL
	// When this method returns, both instance maps will have a mapping for every
	// mode in Modes
	private void populateModesToPlayersMap() {
		logger.debug("Populating map of modes to players");
		// add a mapping into the modes map for every mode.
		// Modes.ALL is skipped as it is built up using the lists of all of
		// the other mappings
		for(Modes mode : Modes.values()) {
			if(mode != Modes.ALL) {
				addMapping(mode);
			}
		}

		// at this point, modes map contains a mapping for every mode 
		// except Modes.ALL. Therefore, we build up the mapping for Modes.ALL
		// using the players lists from all of the other mappings
		List<Player> allPlayersList = new ArrayList<Player>();
		List<Player> allPlayersListCopy = new ArrayList<Player>();
		for(Modes mode : modesToPlayersMap.keySet()) {
			allPlayersList.addAll(modesToPlayersMap.get(mode));
			allPlayersListCopy.addAll(modesToPlayersMap2.get(mode));
		}
		modesToPlayersMap.put(Modes.ALL, allPlayersList);
		modesToPlayersMap2.put(Modes.ALL, allPlayersListCopy);
	}

	// add mapping for mode to modes map, and corresponding copy mapping to copy of modes map
	// Allows for dynamically populating the modes map so that we don't spend unnecessary
	// computation creating lists of players for modes that aren't used
	private void addMapping(Modes mode) {
		List<Player> players = createPlayersList(mode);
		modesToPlayersMap.put(mode, players);
		modesToPlayersMap2.put(mode, deepCopyList(players));
	}

	// creates list of players for the given mode
	// Note, this should only be used in conjunction with addMapping() to create a
	// modes to players map in the class' constructor
	private static List<Player> createPlayersList(Modes mode) {
		// quickly initialize group of players based on mode
		Player[] players = null;
		//TODO: could add a getPlayersList() method to each Modes to avoid this switch stmt
		switch(mode) {
				case QB:
						players = new Player[]{Players.SANCHEZ,Players.WEEDEN,Players.LEINART,Players.QUINN,
								Players.KOLB,Players.PALMER,Players.BRADY,Players.PEYTON,Players.RODGERS};
						break;
				case RB:
						players = new Player[]{Players.REDMAN,Players.HILLMAN,Players.MATHEWS,Players.JONESDREW,
								Players.RBUSH,Players.RICE,Players.LYNCH,Players.FOSTER};
						break;
				case WR:
						players = new Player[]{Players.BEDWARDS,Players.SHOLMES,Players.HDOUGLAS,Players.MANNINGHAM,
								Players.AMENDOLA,Players.JJONES,Players.DBRYANT,Players.CJOHNSON};
						break;
				case K:
						players = new Player[]{Players.CUNDIFF,Players.FOLK,Players.CROSBY,Players.FORBATH,
								Players.SCOBEE,Players.SUISHAM,Players.GOSTKOWSKI,Players.MBRYANT,Players.TUCKER};
						break;
				case DEF:
						players = new Player[]{Players.OAKLAND,Players.NEWORLEANS,Players.JACKSONVILLE,
								Players.CLEVELAND,Players.SEATTLE,Players.SANFRAN,Players.CHICAGO};
						break;
				case ALL:
						// this list is built up from all other lists in addMapping()
						return null;
				default:
						throw new IllegalArgumentException("Error: Invalid mode\n" + getUsage());
		}

		// put players into list
		List<Player> playersList = new ArrayList<Player>(Arrays.asList(players));
		return playersList;
	}


	// Make deep copy of list of players. 
	// Would work for any List<E> where E defines a deepCopy() method
	private static List<Player> deepCopyList(List<Player> list) {
		List<Player> listCopy = new ArrayList<Player>(list.size());
		for(Player element : list) {
			listCopy.add(element.deepCopy());
		}
		return listCopy;
	}

	// create and initialize a RuleMap containing the default scoring rules
	// for NFL.com leagues
	private static RuleMap initDefaultRuleMap() {
		RuleMap rules = new RuleMap();
		rules.put(Pass.YDS, new Rule<Pass>(Pass.YDS, 1.0, 25));
		rules.put(Pass.TD, new Rule<Pass>(Pass.TD, 4.0));
		rules.put(Pass.INT, new Rule<Pass>(Pass.INT, -2.0));
		rules.put(Rush.YDS, new Rule<Rush>(Rush.YDS, 1.0, 10));
		rules.put(Rush.TD, new Rule<Rush>(Rush.TD, 6.0));
		rules.put(Rec.YDS, new Rule<Rec>(Rec.YDS, 1.0, 10));
		rules.put(Rec.TD, new Rule<Rec>(Rec.TD, 6.0));
		rules.put(Misc.FUMB_TD, new Rule<Misc>(Misc.FUMB_TD, 6.0));
		rules.put(Misc.FUMB_LOST, new Rule<Misc>(Misc.FUMB_LOST, -2.0));
		rules.put(Misc.TWO_PT_CONV, new Rule<Misc>(Misc.TWO_PT_CONV, 2.0));
		rules.put(Kick.PAT_MD, new Rule<Kick>(Kick.PAT_MD, 1.0));
		rules.put(Kick.FG_MD_0, new Rule<Kick>(Kick.FG_MD_0, 3.0));
		rules.put(Kick.FG_MD_20, new Rule<Kick>(Kick.FG_MD_20, 3.0));
		rules.put(Kick.FG_MD_30, new Rule<Kick>(Kick.FG_MD_30, 3.0));
		rules.put(Kick.FG_MD_40, new Rule<Kick>(Kick.FG_MD_40, 3.0));
		rules.put(Kick.FG_MD_50, new Rule<Kick>(Kick.FG_MD_50, 5.0));
		rules.put(Def.SCK, new Rule<Def>(Def.SCK, 1.0));
		rules.put(Def.INT, new Rule<Def>(Def.INT, 2.0));
		rules.put(Def.FUMB, new Rule<Def>(Def.FUMB, 2.0));
		rules.put(Def.SAF, new Rule<Def>(Def.SAF, 2.0));
		rules.put(Def.TD, new Rule<Def>(Def.TD, 6.0));
		rules.put(Def.RET, new Rule<Def>(Def.RET, 6.0));
		rules.put(Def.PTS, new Rule<Def>(Def.PTS, -3.0, 7));
		return rules;
	}

	// get string detailing command line usage
	private static String getUsage() {
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
