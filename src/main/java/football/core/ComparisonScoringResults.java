package football.core;

import java.util.Arrays;
import java.util.List;
import static com.google.common.base.Preconditions.checkArgument;

import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

/*
 * "Special" case where we only have 2 RuleMap's and List<Player>'s
 */

public final class ComparisonScoringResults extends ScoringResults
{
	public ComparisonScoringResults(Mode mode, RuleMap rules1, RuleMap rules2, List<Player> players1, List<Player> players2, double distance) {
		super(mode, Arrays.asList(rules1, rules2), Arrays.asList(players1, players2), distance);
	}

	// merge 2 SimpleScoringResults into a ComparisonScoringResults
	// uses a metric to compute the similarity between the 2 RuleMap's based on the Player scores
	public ComparisonScoringResults(SimpleScoringResults results1, SimpleScoringResults results2, double distance) {
		this(results1.getMode(), results1.getRules().get(0), results2.getRules().get(0), 
				results1.getPlayers().get(0), results2.getPlayers().get(0), distance);
		checkArgument(results1.getMode() == results2.getMode(), "ScoringResults modes, %s and %s, are not equal", results1.getMode(), results2.getMode());
	}
}
