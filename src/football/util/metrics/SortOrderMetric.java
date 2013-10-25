package football.util.metrics;

import java.util.List;
import java.util.HashMap;

import football.players.Player;

/*
 * Compares order of two sorted player lists
 */

public class SortOrderMetric implements Metric
{
	public SortOrderMetric() { }

	//O(n) runtime; O(n) space
	public <E extends Player> double distance(List<E> players1, List<E> players2) {
		//map all players in players1 to their list index for easy comparison with players2
		HashMap<E,Integer> map = new HashMap<E,Integer>();
		int i = 0; //keep track of index
		for(E player : players1) {
			map.put(player, new Integer(i));
			i++;
		}
		double sum = 0.0;
		i = 0; //reset index
		for(E player : players2) {
			Integer index = map.get(player);
			//if player not in map (players not mapped to null values)
			if(index != null) {
				int j = index.intValue(); //index of this player in players1 
				sum += Math.abs(j - i); //difference btw indices of this player in two lists
			} else {
				System.out.println("Warning in distance: player " + player.getName() + " not contained in HashMap");
			}
			i++;
		}
		return sum;
	}
}
