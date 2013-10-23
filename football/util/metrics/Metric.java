package football.util.metrics;

import java.util.List;

import football.players.Player;

/*
 * Interface for measuring distance (dissimilarity) between two groups of players
 */

public interface Metric
{
	public <E extends Player> double distance(List<E> players1, List<E> players2);
}
