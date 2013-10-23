package football.util.metrics;

import java.util.List;

import football.players.Player;

/*
 * Interface for measuring distance (dissimilarity) between two groups of players
 */

public interface Metric
{
	public <T extends Player> double distance(List<T> players1, List<T> players2);
}
