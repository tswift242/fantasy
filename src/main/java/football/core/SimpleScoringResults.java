package football.core;

import java.util.Arrays;
import java.util.List;

import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

/*
 * "Special" case where we only have 1 RuleMap and List<Player>
 */

public final class SimpleScoringResults extends ScoringResults
{
	public SimpleScoringResults(Mode mode, RuleMap rules, List<Player> players) {
		// set distance to a negative number to signify that it's not applicable
		super(mode, Arrays.asList(rules), Arrays.asList(players), -1);
	}
}
