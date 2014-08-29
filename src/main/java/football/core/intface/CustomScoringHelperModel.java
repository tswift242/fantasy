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
	public void logResults(ScoringResults results);

	public Map<Mode,List<Player>> getModesToPlayersMap();

	public void setMode(Mode mode);

	public <T extends Enum<T> & StatType> void setRule(T category, Rule<T> rule);

	public void setRules(RuleMap rules);
}
