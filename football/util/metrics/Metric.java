package football.util.metrics;

import java.util.List;

import football.players.Player;

/*
 * Interface for measuring distance (dissimilarity) between two groups of players
 */

public interface Metric
{
	//distance returns a scalar that is meant to be MAXIMIZED, if used as an objective function
	public <E extends Player> double distance(List<E> players1, List<E> players2);
}
