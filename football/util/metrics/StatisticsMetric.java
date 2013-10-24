package football.util.metrics;

import java.util.List;

import football.players.Player;

/*
 * Compares statistical quantities of scores between two lists of players
 */

//TODO: if want to make this more extensible in future, store weights and stats in double[] and remove Statistics class,
//or considering using Stat<T> and creating Statistics enum
public class StatisticsMetric implements Metric
{
	private double minWeight, maxWeight, meanWeight;

	public StatisticsMetric(double minWeight, double maxWeight, double meanWeight) {
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		this.meanWeight = meanWeight;
	}

	public void setMinWeight(double minWeight) {
		this.minWeight = minWeight;
	}

	public void setMaxWeight(double maxWeight) {
		this.maxWeight = maxWeight;
	}

	public void setMeanWeight(double meanWeight) {
		this.meanWeight = meanWeight;
	}

	//set all weights
	public void setWeights(double minWeight, double maxWeight, double meanWeight) {
		this.minWeight = minWeight;
		this.maxWeight = maxWeight;
		this.meanWeight = meanWeight;
	}

	public <E extends Player> double distance(List<E> players1, List<E> players2) {
		Statistics stats1 = getStatisticalQuantities(players1);
		Statistics stats2 = getStatisticalQuantities(players2);
		return ((minWeight * Math.abs(stats1.min - stats2.min)) + 
				(maxWeight * Math.abs(stats1.max - stats2.max)) +
				(meanWeight * Math.abs(stats1.mean - stats2.mean)));
	}

	//calculate all statistical quantities in one pass for increased efficiency
	//Alternatively could use org.apache.commons.math3.stat.descriptive.SummaryStatistics
	private <E extends Player> Statistics getStatisticalQuantities(List<E> players) {
		double min = Double.MAX_VALUE;
		double max = Double.MIN_VALUE;
		double mean = 0.0;
		for(E player : players) {
			double score = player.getScore();
			if(score < min) {
				min = score;
			}
			if(score > max) {
				max = score;
			}
			mean += score;
		}
		mean /= players.size();
		return new Statistics(min,max,mean);
	}

	/*
	* Object for holding statistical quantities
	* More descriptive alternative to double[]
	*/
	private class Statistics
	{
		private double min, max, mean;

		public Statistics(double min, double max, double mean) {
			this.min = min;
			this.max = max;
			this.mean = mean;
		}
	}
}
