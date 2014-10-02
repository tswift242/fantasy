package football.util.logging;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import football.config.CustomScoringHelperProperties;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.ScoringResults;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

/*
 * A class for logging player scoring and ranking results to a file.
 * Note, this is not a typical logger. No errors or warnings or logged,
 * and no logging API or framework is actually used.
 */

public class ResultsLogger
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final String delimiter = StringUtils.repeat("*", 90);
	private static final String sectionDenoter = StringUtils.repeat("*", 8);

	private PrintWriter out;
	private String resultsDirectory;

	// create a ResultsLogger which logs results to the specified file in the specified directory.
	// append indicates whether or not results should be appended to existing file content, or 
	// if the file log should be started fresh.
	public ResultsLogger(String resultsDirectory, String filename, boolean append) throws IOException {
		this.resultsDirectory = resultsDirectory;

		File directory = new File(resultsDirectory);
		// create directory if it doesn't exit
		if(!directory.exists()) {
			if(!directory.mkdirs()) {
				logger.error("Error creating results directory {}", resultsDirectory);
			}
		}

		out = new PrintWriter(new BufferedWriter(new FileWriter(new File(directory ,filename), append)));
	}

	// uses default append value of true
	public ResultsLogger(String resultsDirectory, String filename) throws IOException {
		this(resultsDirectory, filename, true);
	}

	public String getDirectory() {
		return resultsDirectory;
	}

	public void logResults(ScoringResults results) throws IOException {
		Mode mode = results.getMode();
		List<RuleMap> allRules = results.getRules();
		List<List<Player>> allPlayers = results.getPlayers();
		double distance = results.getDistance();

		out.println(delimiter + "\n");
		out.println(toSectionHeader("Mode",sectionDenoter));
		out.println(mode.toString() + "\n");

		// number of different RuleMap's used to score the same players
		// == number of (simple) models being used
		int numRuleMaps = allRules.size();
		String categoriesString = mode.getCategoriesString();
		// log results for each (RuleMap, List<Player>) pair
		// Note: not efficient for lists without random access; ignoring for now b/c
		// lists will have random access, and b/c numRuleMaps <= 2
		for(int i = 0; i < numRuleMaps; i++) {
			RuleMap rules = allRules.get(i);
			List<Player> players = allPlayers.get(i);

			out.println(toSectionHeader("Custom scoring rules " + (i+1),sectionDenoter));
			out.println(categoriesString);
			out.println(argsToString(rules.toArgs(mode)));
			out.println(toSectionHeader("Scores using custom rules " + (i+1),sectionDenoter));
			out.println(listToString(players));
		}

		if(CustomScoringHelperProperties.metricsEnabled()) {
			out.println("Distance between sets of custom rules is: " + distance + "\n");
		}
		out.println(delimiter + "\n\n\n");

		// flush everything to disk
		out.flush();
	}

	public void close() {
		out.close();
	}


	// TODO: consider just using players.toString()
	// get string representation of players list
	private <E extends Player> String listToString(List<E> players) {
		String result = "";
		for(E player : players) {
			result += (player.toString() + "\n");
		}
		result += "\n";
		return result;
	}

	// write rules array to printwriter stream
	private String argsToString(String[] args) {
		String result = "";
		int numRules = args.length;
		for(int i = 1; i < numRules; i++) { //skip mode argument
			result += String.format("%-10s ",args[i]);
		}
		result += "\n";
		return result;
	}

	// surrounds sectionTitle on both sides by sectionDenoter to create section header
	private String toSectionHeader(String sectionTitle, String sectionDenoter) {
		return (sectionDenoter + " " + sectionTitle + " " + sectionDenoter);
	}
}
