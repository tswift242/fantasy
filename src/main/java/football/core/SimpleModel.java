package football.core;

import java.io.File;
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

import football.config.CustomScoringHelperProperties;
import football.core.intface.CustomScoringHelperModel;
import football.players.Player;
import football.players.Players;
import football.players.modes.Mode;
import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.StatType;
import football.util.logging.ResultsLogger;

public final class SimpleModel implements CustomScoringHelperModel
{
	private static final Logger logger = LoggerFactory.getLogger(SimpleModel.class.getName());

	//TODO: make these maps map to List<E extends Player> if we drop Mode.ALL
	// map of player modes to corresponding lists of players
	private Map<Mode,List<Player>> modesToPlayersMap;

	private RuleMap currentRules;
	private Mode currentMode;
	private ResultsLogger resultsLogger;

	public SimpleModel() {
		currentMode = CustomScoringHelperProperties.getDefaultMode();
		logger.info("Constructing model with default mode {}", currentMode.toString());

		// init to default rules (don't simply assign to prevent DEFAULT_RULES from being 
		// modified whenever currentRules is modified)
		currentRules = CustomScoringHelperProperties.getDefaultRules();

		modesToPlayersMap = new EnumMap<Mode,List<Player>>(Mode.class);
		populateModesToPlayersMap();
		logger.debug("Using default rule map of:\n{}", CustomScoringHelperProperties.getDefaultRules().toString());

		if(CustomScoringHelperProperties.isResultsLoggingEnabled()) {
			resultsLogger = createDefaultResultsLogger();
		}
	}

	// command line version
	@Override
	public ScoringResults run(String[] args) {
		logger.info("Running model with args: {}", Arrays.toString(args));
		checkPositionIndex(0, args.length, "mode not specified\n" + getUsage());
		Mode mode = Mode.fromString(args[0]);
		// parse scoring rules relevant to this mode
		RuleMap rules = mode.parseScoringRules(args);

		return run(mode, rules);
	}

	// GUI version
	private ScoringResults run(Mode mode, RuleMap rules) {
		logger.info("Running model with mode and custom rules: {}\n{}", mode.toString(), rules.toString());
		// get corresponding list of players for this mode
		List<Player> players = modesToPlayersMap.get(mode);
		// evaluate all players with the corresponding set of rules
		scorePlayers(players, rules);
		// sort players according to their scores
		//TODO: delay sorting until logging in ResultsLogger, as sorting done by View anyway
		Collections.sort(players);
		logger.info("Players sorted by custom rules:\n{}", players.toString());

		// return results so that view can be updated
		return new SimpleScoringResults(mode, rules, players);
	}

	// run using currentMode and currentRules
	@Override
	public ScoringResults run() {
		return run(currentMode, currentRules);
	}

	@Override
	public void logResults(ScoringResults results) {
		try {
			if(CustomScoringHelperProperties.isResultsLoggingEnabled()) {
				resultsLogger.logResults(results);
			}
		} catch(IOException e) {
			logger.error("Unable to write results: {}", e.toString());
		}
	}

	private ResultsLogger createDefaultResultsLogger() {
		Mode mode = CustomScoringHelperProperties.getDefaultMode();
		String directory = CustomScoringHelperProperties.getResultsDirectory();
		String filename = CustomScoringHelperProperties.getResultsFilename(mode);

		String fileSeparator = System.getProperty("file.separator");
		logger.info("Creating default ResultsLogger set to log results to {}{}{}", directory, fileSeparator, filename);

		return createResultsLogger(directory, filename);
	}

	public void updateResultsLogger(String fullPathToFile) {
		File file = new File(fullPathToFile);
		// get directory and filename from file
		String directory = file.getParent();
		String filename = file.getName();

		// replace ResultsLogger with new one
		logger.info("Updating ResultsLogger to log results to {}", file.getAbsolutePath());
		resultsLogger = replaceResultsLogger(resultsLogger, directory, filename);
	}

	public void updateResultsLogger(Mode mode) {
		// get directory from ResultsLogger
		String directory = resultsLogger.getDirectory();
		// get filename based on Mode
		String filename = CustomScoringHelperProperties.getResultsFilename(mode);

		// replace ResultsLogger with new one
		logger.info("Updating ResultsLogger to new Mode {}", mode.toString());
		resultsLogger = replaceResultsLogger(resultsLogger, directory, filename);
	}

	/*
	 * Getters
	 */
	// ignore modelID, since this is not a composite model
	//TODO: should return defensive copy of map for satefy;
	// can probably remove parameter if we do this
	@Override
	public Map<Mode,List<Player>> getModesToPlayersMap(int modelID) {
		return modesToPlayersMap;
	}

	/*
	 * Setters
	 */
	@Override
	public void setMode(Mode mode) {
		currentMode = mode;
	}

	// ignore modelID, since this is not a composite model
	@Override
	public <T extends Enum<T> & StatType> void setRule(Rule<T> rule, int modelID) {
		currentRules.put(rule.getCategory(), rule);
	}

	// ignore modelID, since this is not a composite model
	@Override
	public void setRules(RuleMap rules, int modelID) {
		setRules(rules);
	}

	@Override
	public void setRules(RuleMap rules) {
		currentRules = rules;
	}

	@Override
	public void close() {
		logger.info("Closing model");
		closeResultsLogger(resultsLogger);
	}


	// assign each player in players a score using the scoring rules in rules
	private void scorePlayers(List<Player> players, RuleMap rules) {
		for(Player player : players) {
			// if we're using default rules, "look up" correct score by using
			// the saved defaultScore of each player instead of computing the
			// score from scratch
			//TODO: NOT CORRECT -- need to implement equals() for RuleMap
			//TODO: consider not checking for / tracking default score (cost of RuleMap
			// equality check probably comparable to savings in computing score)
			if(rules.equals(CustomScoringHelperProperties.getDefaultRules())) {
				player.useDefaultScore();
			} else {
				player.evaluate(rules);
			}
		}
	}

	// adds mappings into modes map, and then accumulates the players lists 
	// from these mappings in order to add a mapping for mode Mode.ALL
	// When this method returns, both instance maps will have a mapping for every
	// mode in Mode
	private void populateModesToPlayersMap() {
		logger.debug("Populating map of modes to players");
		// add a mapping into the modes map for every mode.
		// Mode.ALL is skipped as it is built up using the lists of all of
		// the other mappings
		for(Mode mode : Mode.values()) {
			if(mode != Mode.ALL) {
				addMapping(mode);
			}
		}

		// at this point, modes map contains a mapping for every mode 
		// except Mode.ALL. Therefore, we build up the mapping for Mode.ALL
		// using the players lists from all of the other mappings
		List<Player> allPlayersList = new ArrayList<Player>();
		for(Mode mode : modesToPlayersMap.keySet()) {
			allPlayersList.addAll(modesToPlayersMap.get(mode));
		}
		modesToPlayersMap.put(Mode.ALL, allPlayersList);
	}

	// add mapping for mode to modes map, and corresponding copy mapping to copy of modes map
	// Allows for dynamically populating the modes map so that we don't spend unnecessary
	// computation creating lists of players for modes that aren't used
	private void addMapping(Mode mode) {
		modesToPlayersMap.put(mode, createPlayersList(mode));
	}

	// creates list of players for the given mode
	// Note, this should only be used in conjunction with addMapping() to create a
	// modes to players map in the class' constructor
	private static List<Player> createPlayersList(Mode mode) {
		// quickly initialize group of players based on mode
		Player[] players = null;
		//TODO: could add a getPlayersList() method to each Mode to avoid this switch stmt
		switch(mode) {
				case QB:
						players = new Player[]{Players.SANCHEZ.deepCopy(),Players.WEEDEN.deepCopy(),
								Players.LEINART.deepCopy(),Players.QUINN.deepCopy(),Players.KOLB.deepCopy(),
								Players.PALMER.deepCopy(),Players.BRADY.deepCopy(),Players.PEYTON.deepCopy(),
								Players.RODGERS.deepCopy()};
						break;
				case RB:
						players = new Player[]{Players.REDMAN.deepCopy(),Players.HILLMAN.deepCopy(),
							Players.MATHEWS.deepCopy(),Players.JONESDREW.deepCopy(),Players.RBUSH.deepCopy(),
							Players.RICE.deepCopy(),Players.LYNCH.deepCopy(),Players.FOSTER.deepCopy()};
						break;
				case WR:
						players = new Player[]{Players.BEDWARDS.deepCopy(),Players.SHOLMES.deepCopy(),
							Players.HDOUGLAS.deepCopy(),Players.MANNINGHAM.deepCopy(),Players.AMENDOLA.deepCopy(),
							Players.JJONES.deepCopy(),Players.DBRYANT.deepCopy(),Players.CJOHNSON.deepCopy()};
						break;
				case K:
						players = new Player[]{Players.CUNDIFF.deepCopy(),Players.FOLK.deepCopy(),
							Players.CROSBY.deepCopy(),Players.FORBATH.deepCopy(),Players.SCOBEE.deepCopy(),
							Players.SUISHAM.deepCopy(),Players.GOSTKOWSKI.deepCopy(),Players.MBRYANT.deepCopy(),
							Players.TUCKER.deepCopy()};
						break;
				case DEF:
						players = new Player[]{Players.OAKLAND.deepCopy(),Players.NEWORLEANS.deepCopy(),
							Players.JACKSONVILLE.deepCopy(),Players.CLEVELAND.deepCopy(),Players.SEATTLE.deepCopy(),
							Players.SANFRAN.deepCopy(),Players.CHICAGO.deepCopy()};
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

	

	// get string detailing command line usage
	private static String getUsage() {
		String result = "";
		String indent = "\t\t";
		result += "Usage: java FantasyFootballCustomScoringHelper <mode> <sc1> ... <scN>,\twhere\n";
		result += (indent + "mode := player position to simulate. Possible values are def, k, qb, rb, and wr.\n");
		result += (indent + "sc1 - scN := scoring coefficients representing all rules needed to score players" + 
				" at position given by mode. Necessary scoring coefficients per position are given below.\n");
		result += (indent + "def: " + Mode.DEF.getCategoriesString() + "\n");
		result += (indent + "k: " + Mode.K.getCategoriesString() + "\n");
		result += (indent + "qb: " + Mode.QB.getCategoriesString() + "\n");
		result += (indent + "rb: " + Mode.RB.getCategoriesString() + "\n");
		result += (indent + "wr: " + Mode.WR.getCategoriesString() + "\n");
		return result;
	}

	// wrap logic for catching IOException for ResultsLogger constructor into a method
	private static ResultsLogger createResultsLogger(String directory, String filename) {
		ResultsLogger resultsLogger = null;

		try {
			resultsLogger = new ResultsLogger(directory, filename);
		} catch(IOException e) {
			logger.error("Error creating ResultsLogger: {}", e.toString());
		}

		return resultsLogger;
	}

	// replace old ResultsLogger oldLogger with a new ResultsLogger pointing at
	// directory/filename
	private static ResultsLogger replaceResultsLogger(ResultsLogger oldLogger, String directory, String filename) {
		// close old ResultsLogger
		closeResultsLogger(oldLogger);

		return createResultsLogger(directory, filename);
	}

	private static void closeResultsLogger(ResultsLogger resultsLogger) {
		if(resultsLogger != null) {
			resultsLogger.close();
		}
	}
}
