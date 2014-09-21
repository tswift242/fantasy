package football.core.intface;

import java.util.List;
import java.util.Map;

import football.core.ScoringResults;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.StatType;

public interface CustomScoringHelperModel
{
	// run model using rules specified at the command line
	public ScoringResults run(String[] args);

	// run model using rules specified through the UI, 
	// maintained as member variables by the model
	public ScoringResults run();

	// log the results of player evaluations to a file
	// SimpleScoringResults argument will always be the same SimpleScoringResults object returned by run()
	public void logResults(ScoringResults results);

	// update ResultsLogger with given full path to log file provided by the user through the GUI
	public void updateResultsLogger(String fullPathToFile);

	// update ResultsLogger to log to a mode-specific file based on a new Mode selection 
	// propagated by the ModeListener
	public void updateResultsLogger(Mode mode);

	// modelID specifies which model to access in the case of composite models
	public Map<Mode,List<Player>> getModesToPlayersMap(int modelID);

	public void setMode(Mode mode);

	// modelID specifies which model to access in the case of composite models
	public <T extends Enum<T> & StatType> void setRule(Rule<T> rule, int modelID);

	// modelID specifies which model to access in the case of composite models
	public void setRules(RuleMap rules, int modelID);

	// returns number of separate models (corresponding to separate RuleMap's) that are contained
	// within this model object (for composite models)
	// Note: should only return 1 or 2 for now
	public int getNumberOfModels();
}
