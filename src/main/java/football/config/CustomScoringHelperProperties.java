package football.config;

import java.io.IOException;
import java.io.FileNotFoundException;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.modes.Mode;
import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.categories.*;
import football.util.metrics.Metric;
import football.util.metrics.SortOrderMetric;

public final class CustomScoringHelperProperties {

	private static final Logger logger = LoggerFactory.getLogger(CustomScoringHelperProperties.class.getName());

	public static final RuleMap defaultNFLRules = createDefaultNFLRuleMap();
	public static final RuleMap defaultESPNRules = createDefaultESPNRuleMap();
	public static final RuleMap defaultYahooRules = createDefaultYahooRuleMap();
	public static final RuleMap defaultCBSRules = createDefaultCBSRuleMap();

	/*
	 * Properties
	 */
	private static boolean useCompositeModel = true;
	// Mode selected by default at program start
	private static Mode defaultMode = Mode.QB;
	// league site selected by default at program start
	private static String defaultSite = "NFL";
	// default set of Rules for the CURRENTLY SELECTED league site
	private static RuleMap defaultRules = defaultNFLRules;
	private static boolean resultsLoggingEnabled = true;
	private static String resultsDirectory = System.getProperty("user.home") +
				System.getProperty("file.separator") + "fantasyfootball-custom-scorer-results";
	private static boolean metricsEnabled = true;
	private static Metric defaultMetric = new SortOrderMetric();

	/*
	 * Property keys
	 */
	private static final String USE_COMPOSITE_MODEL = "useCompositeModel";
	private static final String DEFAULT_MODE = "defaultMode";
	private static final String DEFAULT_RULES = "defaultRules";
	private static final String ENABLE_RESULTS_LOGGING = "enableResultsLogging";
	private static final String RESULTS_DIRECTORY = "resultsDirectory";
	private static final String ENABLE_METRICS = "enableMetrics";

	private CustomScoringHelperProperties() {}

	public static void init() {
		logger.info("initializing property values");
		try {
			CustomScoringHelperPropertiesLoader loader = CustomScoringHelperPropertiesLoader.getInstance();

			// set properties by getting properties from loader
			setUseCompositeModel(Boolean.parseBoolean(loader.getProperty(USE_COMPOSITE_MODEL)));
			setDefaultMode(Mode.fromString(loader.getProperty(DEFAULT_MODE)));
			setDefaultSite(loader.getProperty(DEFAULT_RULES));
			setDefaultRules(defaultSite);
			enableResultsLogging(Boolean.parseBoolean(loader.getProperty(ENABLE_RESULTS_LOGGING)));
			setResultsDirectory(loader.getProperty(RESULTS_DIRECTORY));
			enableMetrics(Boolean.parseBoolean(loader.getProperty(ENABLE_METRICS)));

			setMetric(new SortOrderMetric());
		} catch(FileNotFoundException e) { // properties file does not exit
			logger.error("{}. Using default properties.", e.toString());
		} catch(IOException e) { // error while parsing properties file
			logger.error("{}. Using default properties.", e.toString());
		}
	}

	/*
	* Getters
	*/
	public static boolean useCompositeModel() {
		return useCompositeModel;
	}

	public static Mode getDefaultMode() {
		return defaultMode;
	}

	public static String getDefaultLeagueSite() {
		return defaultSite;
	}

	//TODO: should return defensive copy of this for safety
	public static RuleMap getDefaultRules() {
		return defaultRules;
	}

	public static boolean isResultsLoggingEnabled() {
		return resultsLoggingEnabled;
	}

	public static String getResultsDirectory() {
		if(!isResultsLoggingEnabled()) {
			throw new IllegalStateException("Attempted to get results directory while results logging was disabled");
		}
		return resultsDirectory;
	}

	public static String getResultsFilename(Mode mode) {
		return (mode.toString() + ".txt");
	}

	public static boolean metricsEnabled() {
		return metricsEnabled;
	}

	//TODO: should return defensive copy of this for safety
	public static Metric getDefaultMetric() {
		if(!metricsEnabled()) {
			throw new IllegalStateException("Attempted to get default metric while metrics was disabled");
		}
		return defaultMetric;
	}

	/*
	* Setters
	*/
	private static void setUseCompositeModel(boolean createCompositeModel) {
		useCompositeModel = createCompositeModel;
	}

	private static void setDefaultMode(Mode mode) {
		defaultMode = mode;
	}

	private static void setDefaultSite(String site) {
		defaultSite = site;
	}

	public static void setDefaultRules(String site) {
		switch(site) {
			case "NFL":
				defaultRules = defaultNFLRules;
				break;
			case "ESPN":
				defaultRules = defaultESPNRules;
				break;
			case "Yahoo":
				defaultRules = defaultYahooRules;
				break;
			case "CBS":
				defaultRules = defaultCBSRules;
				break;
			default:
				throw new IllegalArgumentException("Invalid site specified: " + site + ". Must be one of these values: NFL, ESPN, Yahaoo, CBS");
		}
	}

	private static void enableResultsLogging(boolean logResults) {
		resultsLoggingEnabled = logResults;
	}

	private static void setResultsDirectory(String directory) {
		if(!isResultsLoggingEnabled()) {
			resultsDirectory = null;
		}
		// allow user to set resultsDirectory property to nothing to indicate that they wish
		// to use the default directory
		else if(!StringUtils.isBlank(directory)) {
			resultsDirectory = directory;
		}
	}

	private static void enableMetrics(boolean enableMetrics) {
		metricsEnabled = (useCompositeModel() && enableMetrics);
	}

	private static void setMetric(Metric metric) {
		defaultMetric = (metricsEnabled() ? metric : null);
	}




	// create and initialize a RuleMap containing the default scoring rules
	// for NFL.com leagues
	private static RuleMap createDefaultNFLRuleMap() {
		RuleMap rules = createCommonRuleMap();
		// Pass
		rules.put(Pass.TD, new Rule<Pass>(Pass.TD, 4.0));
		rules.put(Pass.INT, new Rule<Pass>(Pass.INT, -2.0));
		// Kick
		rules.put(Kick.FG_MD_40, new Rule<Kick>(Kick.FG_MD_40, 3.0));
		// Def
		rules.put(Def.PTS, new Rule<Def>(Def.PTS, -2.8, 7));
		return rules;
	}

	// create and initialize a RuleMap containing the default scoring rules
	// for ESPN.com leagues
	private static RuleMap createDefaultESPNRuleMap() {
		RuleMap rules = createCommonRuleMap();
		// Pass
		rules.put(Pass.TD, new Rule<Pass>(Pass.TD, 4.0));
		rules.put(Pass.INT, new Rule<Pass>(Pass.INT, -2.0));
		// Kick
		rules.put(Kick.FG_MD_40, new Rule<Kick>(Kick.FG_MD_40, 4.0));
		rules.put(Kick.FG_MS_0, new Rule<Kick>(Kick.FG_MS_0, -1.0));
		rules.put(Kick.FG_MS_20, new Rule<Kick>(Kick.FG_MS_20, -1.0));
		rules.put(Kick.FG_MS_30, new Rule<Kick>(Kick.FG_MS_30, -1.0));
		rules.put(Kick.FG_MS_40, new Rule<Kick>(Kick.FG_MS_40, -1.0));
		rules.put(Kick.FG_MS_50, new Rule<Kick>(Kick.FG_MS_50, -1.0));
		// Def
		rules.put(Def.PTS, new Rule<Def>(Def.PTS, -1.28, 7));
		rules.put(Def.YDS, new Rule<Def>(Def.YDS, -1.91, 100)); // -1.916 = -11.5/6
		// TODO: BLK (kick/punt) = 2
		return rules;
	}

	// create and initialize a RuleMap containing the default scoring rules
	// for Yahoo.com leagues
	private static RuleMap createDefaultYahooRuleMap() {
		RuleMap rules = createCommonRuleMap();
		// Pass
		rules.put(Pass.TD, new Rule<Pass>(Pass.TD, 4.0));
		rules.put(Pass.INT, new Rule<Pass>(Pass.INT, -1.0));
		// Kick
		rules.put(Kick.FG_MD_40, new Rule<Kick>(Kick.FG_MD_40, 4.0));
		// Def
		rules.put(Def.PTS, new Rule<Def>(Def.PTS, -2.8, 7));
		// TODO: BLK (kick/punt) = 2
		return rules;
	}

	// create and initialize a RuleMap containing the default scoring rules
	// for CBS.com leagues
	private static RuleMap createDefaultCBSRuleMap() {
		RuleMap rules = createCommonRuleMap();
		// Pass
		rules.put(Pass.TD, new Rule<Pass>(Pass.TD, 6.0));
		rules.put(Pass.INT, new Rule<Pass>(Pass.INT, -2.0));
		// Kick
		rules.put(Kick.FG_MD_40, new Rule<Kick>(Kick.FG_MD_40, 3.0));
		// Def
		rules.put(Def.PTS, new Rule<Def>(Def.PTS, -2.0, 7));
		rules.put(Def.YDS, new Rule<Def>(Def.YDS, -4.0, 100));
		return rules;
	}

	// helper method which creates and initializes a RuleMap with default scoring rules
	// which are common to all fantasy sites
	private static RuleMap createCommonRuleMap() {
		RuleMap rules = new RuleMap();
		// Pass
		rules.put(Pass.YDS, new Rule<Pass>(Pass.YDS, 1.0, 25));
		// Rush
		rules.put(Rush.YDS, new Rule<Rush>(Rush.YDS, 1.0, 10));
		rules.put(Rush.TD, new Rule<Rush>(Rush.TD, 6.0));
		// Rec
		rules.put(Rec.YDS, new Rule<Rec>(Rec.YDS, 1.0, 10));
		rules.put(Rec.TD, new Rule<Rec>(Rec.TD, 6.0));
		// Misc
		rules.put(Misc.FUMB_TD, new Rule<Misc>(Misc.FUMB_TD, 6.0));
		rules.put(Misc.FUMB_LOST, new Rule<Misc>(Misc.FUMB_LOST, -2.0));
		rules.put(Misc.TWO_PT_CONV, new Rule<Misc>(Misc.TWO_PT_CONV, 2.0));
		// Kick
		rules.put(Kick.PAT_MD, new Rule<Kick>(Kick.PAT_MD, 1.0));
		rules.put(Kick.FG_MD_0, new Rule<Kick>(Kick.FG_MD_0, 3.0));
		rules.put(Kick.FG_MD_20, new Rule<Kick>(Kick.FG_MD_20, 3.0));
		rules.put(Kick.FG_MD_30, new Rule<Kick>(Kick.FG_MD_30, 3.0));
		rules.put(Kick.FG_MD_50, new Rule<Kick>(Kick.FG_MD_50, 5.0));
		// Def
		rules.put(Def.SCK, new Rule<Def>(Def.SCK, 1.0));
		rules.put(Def.INT, new Rule<Def>(Def.INT, 2.0));
		rules.put(Def.FUMB, new Rule<Def>(Def.FUMB, 2.0));
		rules.put(Def.SAF, new Rule<Def>(Def.SAF, 2.0));
		rules.put(Def.TD, new Rule<Def>(Def.TD, 6.0));
		rules.put(Def.RET, new Rule<Def>(Def.RET, 6.0));
		return rules;
	}
}
