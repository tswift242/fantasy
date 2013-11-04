package football.util;

import java.util.LinkedHashSet;
import java.util.Iterator;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkPositionIndex;

import football.stats.Rule;
import football.stats.Stat;
import football.stats.StatType;

public final class PlayerUtil
{
	private PlayerUtil() {} //NEVER USE THIS

	//utility helper function for evalute()
	//takes dot product of stats set and coeffs array
	public static <T extends Enum<T> & StatType> double dot(LinkedHashSet<Stat<T>> stats, LinkedHashSet<Rule<T>> rules) {
		checkArgument(stats.size() == rules.size(), "stats' size %s  does not equal rules' size %s", stats.size(), rules.size());
		double sum = 0.0;
		for(Iterator<Stat<T>> statsIter = stats.iterator() && Iterator<Rule<T>> rulesIter = rules.iterator();
				statsIter.hasNext() && rulesIter.hasNext(); ) {
			Stat<T> stat = statsIter.next();
			Rule<T> rule = statsIter.next();
			sum += rule.evaluate(stat);
		}
		return sum;
	}

	//utility helper function for parseScoringRulesAndEvaluate()
	//Parses elements in args between startIdx and endIdx (inclusive) into doubles and returns them in an array
	public static <T extends Enum<T> & StatType> LinkedHashSet<Rule<T>> parseScoringRules(String[] args, int startIdx, int endIdx, Class<T> enumType) {
		int argsLength = args.length;
		checkPositionIndex(startIdx, argsLength, String.format("startIdx %d is out of bounds of array with length %d",startIdx,argsLength));
		checkPositionIndex(endIdx, argsLength, String.format("endIdx %d is out of bounds of array with length %d",endIdx,argsLength));
		checkArgument(endIdx >= startIdx, "endIdx %s is smaller than startIdx", endIdx, startIdx);
		LinkedHashSet<Rule<T>> rulesSet = new LinkedHashSet<Rule<T>>();
		T[] categories = enumType.getEnumConstants();
		for(int i = startIdx; i <= endIdx; i++) {
			String arg = args[i];
			T category = categories[i-startIdx];
			if(arg.contains("/")) {
				//split arg into value and unit
				String[] ruleArray = arg.split("/");
				Double value = Double.valueOf(ruleArray[0]);
				int unit = Integer.parseInt(ruleArray[1]);
				rulesSet.add(new Rule<T>(category,value,unit));
			} else {
				Double value = Double.valueOf(arg);
				rulesSet.add(new Rule<T>(category,value)); //use default unit of 1
			}
			/*Double value = Double.valueOf(arg);
			rulesSet.add(new Rule<T>(category,value));*/
		}
		return rulesSet;
	}

	//utility helper function for parseScoringRulesAndEvaluate()
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
