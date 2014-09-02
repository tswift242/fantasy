package football.core;

import java.util.Arrays;
import java.util.List;
import static com.google.common.base.Preconditions.checkArgument;

import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;
import football.util.metrics.Metric;

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
	public ComparisonScoringResults(SimpleScoringResults results1, SimpleScoringResults results2, Metric metric) {
		this(results1.getMode(), results1.getRules().get(0), results2.getRules().get(0), 
				results1.getPlayers().get(0), results2.getPlayers().get(0),
				metric.distance(results1.getPlayers().get(0), results2.getPlayers().get(0)));
		checkArgument(results1.getMode() == results2.getMode(), "ScoringResults modes, %s and %s, are not equal", results1.getMode(), results2.getMode());

		//TODO: to use this approach need to define default constructor in ScoringResults, make fields not final, import ArrayList
		/*Mode mode = results1.getMode();
		RuleMap rules1 = results1.getRules().get(0);
		RuleMap rules2 = results2.getRules().get(0);
		List<Player> players1 = results1.getPlayers().get(0);
		List<Player> players2 = results2.getPlayers().get(0);
		// compute distance through metric
		double distance = metric.distance(players1, players2);

		// set fields (can't call "this" or "super" b/c this isn't first line of constructor)
		this.mode = mode;
		this.rules = new ArrayList<RuleMap>(Arrays.asList(rules1, rules2));
		this.players = new ArrayList<List<Player>>(Arrays.asList(players1, players2));
		this.distance = distance;*/
	}
}
