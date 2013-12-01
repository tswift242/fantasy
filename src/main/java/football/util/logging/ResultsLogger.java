package football.util.logging;

import java.io.PrintWriter;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.File;
import java.io.IOException;
import java.util.List;

import football.players.Player;
import football.util.metrics.Metric;
import football.util.metrics.SortOrderMetric;

/*
 * A class for logging player scoring and ranking results to a file.
 * Note, this is not a typical logger. No errors or warnings or logged,
 * and no logging API or framework is actually used.
 */

public class ResultsLogger
{
	private static final String delimiter = "***********************************************************";
	private static final String sectionDenoter = "********";

	private PrintWriter out;

	// create a ResultsLogger which logs results to the specified file in the specified directory.
	// append indicates whether or not results should be appended to existing file content, or 
	// if the file log should be started fresh.
	public ResultsLogger(String resultsDirectory, String filename, boolean append) throws IOException {
		out = new PrintWriter(new BufferedWriter(new FileWriter(new File(resultsDirectory,filename),append)));
	}

	// uses default append value of true
	public ResultsLogger(String resultsDirectory, String filename) throws IOException {
		this(resultsDirectory, filename, true);
	}

	public void logResults(String[] args, List<Player> defaultPlayers, List<Player> customPlayers) throws IOException {
		//Logger logger = Logger.getLogger(CustomScoringHelper.class.getName());
		//logger.setLevel(Level.INFO);
		//FileHandler fh = new FileHandler(resultsDirectory + "/" + filename, true);
		//fh.setFormatter(new SimpleFormatter());
		//logger.addHandler(fh);

		out.println(delimiter + "\n");
		out.println(toSectionHeader("Mode",sectionDenoter));
		out.println(args[0].toUpperCase() + "\n");
		out.println(toSectionHeader("Custom scoring rules",sectionDenoter));
		out.println(customPlayers.get(0).categoriesToString()); //TODO: make this call static
		out.println(argsToString(args));
		out.println(toSectionHeader("Scores using default rules",sectionDenoter));
		out.println(listToString(defaultPlayers));
		out.println(toSectionHeader("Scores using custom rules",sectionDenoter));
		out.println(listToString(customPlayers));
		//TODO: move metric calculation to run() and pass in dist
		//calculate (dis)similarity btw customPlayers and defaultPlayers
		Metric metric = new SortOrderMetric();
		double dist = metric.distance(defaultPlayers,customPlayers);
		out.println("Distance between default and custom rules is: " + dist);
		out.println("\n" + delimiter + "\n\n\n");
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
