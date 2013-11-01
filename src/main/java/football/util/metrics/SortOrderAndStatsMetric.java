package football.util.metrics;

import java.util.List;

import football.players.Player;

/*
 * Combines sort order and statistics metrics
 */

public class SortOrderAndStatsMetric
{
	private SortOrderMetric orderMetric;
	private StatisticsMetric statMetric;
	private double orderWeight;

	public SortOrderAndStatsMetric(double orderWeight, double minWeight, double maxWeight, double meanWeight) {
		orderMetric = new SortOrderMetric();
		statMetric = new StatisticsMetric(minWeight,maxWeight,meanWeight);
		this.orderWeight = orderWeight;
	}

	public void setMinWeight(double minWeight) {
		statMetric.setMinWeight(minWeight);
	}

	public void setMaxWeight(double maxWeight) {
		statMetric.setMaxWeight(maxWeight);
	}

	public void setMeanWeight(double meanWeight) {
		statMetric.setMeanWeight(meanWeight);
	}

	public void setOrderWeight(double orderWeight) {
		this.orderWeight = orderWeight;
	}

	//set all weights
	public void setWeights(double orderWeight, double minWeight, double maxWeight, double meanWeight) {
		setMinWeight(minWeight);
		setMaxWeight(maxWeight);
		setMeanWeight(meanWeight);
		setOrderWeight(orderWeight);
	}


	public <E extends Player> double distance(List<E> players1, List<E> players2) {
		return ((orderWeight * orderMetric.distance(players1,players2)) + 
					statMetric.distance(players1,players2)); //components already weighted
	}
}
