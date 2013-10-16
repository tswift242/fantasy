package football.players;

import java.util.LinkedHashSet;

import football.stats.Stat;

public final class PlayerUtil
{
	private PlayerUtil() {} //NEVER USE THIS

	//utility helper function for evalute()
	//takes dot product of stats set and coeffs array
	public static <T extends Enum<T>> double dot(LinkedHashSet<Stat<T>> stats, double[] coeffs) {
		if(stats.size() != coeffs.length) {
			System.out.println("Error in PlayerUtil.dot: inputs don't have the same length");
			System.exit(1);
		}

		double sum = 0.0;
		int i = 0;
		for(Stat<T> stat : stats) {
			sum += (stat.getValue()*coeffs[i]);
			i++;
		}
		return sum;
	}

	//utility helper function for parseScoringCoeffsAndEvaluate()
	//Parses elements in args between startIdx and endIdx (inclusive) into doubles and returns them in an array
	public static double[] parseScoringCoeffs(String[] args, int startIdx, int endIdx) {
		if(endIdx < startIdx) {
			System.out.println("Error in PlayerUtil.parseScoringCoeffs: input endIdx is smaller than input startIdx");
			System.exit(1);
		}
		if(endIdx >= args.length) {
			System.out.println("Error in PlayerUtil.parseScoringCoeffs: input endIdx is too large for args array");
			System.exit(1);
		}
		double[] coeffs = new double[endIdx-startIdx+1];
		for(int i = startIdx; i <= endIdx; i++) {
			coeffs[i-startIdx] = Double.parseDouble(args[i]);
		}
		return coeffs;
	}

	//utility helper function for parseScoringCoeffsAndEvaluate()
	//calculates cumulative sum of array a; each element in output array is sum of elements in input array up to that index
	public static int[] cumsum(int[] a) {
		int length = a.length;
		int[] cumsum = new int[length];
		cumsum[0] = a[0];
		for(int i = 1; i < length; i++) {
			cumsum[i] = cumsum[i-1]+a[i];
		}
		return cumsum;
	}

	public static int sum(int[] a) {
		int length = a.length;
		int sum = 0;
		for(int i = 0; i < length; i++) {
			sum += a[i];
		}
		return sum;
	}
}
