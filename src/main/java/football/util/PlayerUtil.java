package football.util;

import java.util.Set;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkPositionIndex;

import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.StatType;

public final class PlayerUtil
{
	private PlayerUtil() {} //NEVER USE THIS

	//utility helper function for evalute()
	//takes dot product of stats set and corresponding set of rules in RuleMap
	public static <T extends Enum<T> & StatType> double dot(Set<Stat<T>> stats, RuleMap rules) {
		double sum = 0.0;
		for(Stat<T> stat : stats) {
			T category = stat.getCategory();
			Rule<T> rule = rules.get(category);
			if(rule != null) {
				sum += rule.evaluate(stat);
			} else {
				System.err.println("Warning: rule for category " + category.toString() + " is null");
			}
		}
		return sum;
	}

	//utility helper function for parseScoringRulesAndEvaluate()
	//Parses elements in args between startIdx and endIdx (inclusive) into doubles and returns them in an array
	//TODO: try making version in which final arg is List<Class<? extends Enum<?>>> and see how it goes
	public static <T extends Enum<T> & StatType> RuleMap parseScoringRules(String[] args, int startIdx, int endIdx, Class<T> enumType) {
		int argsLength = args.length;
		checkPositionIndex(startIdx, argsLength, String.format("startIdx %d is out of bounds of array with length %d",startIdx,argsLength));
		checkPositionIndex(endIdx, argsLength, String.format("endIdx %d is out of bounds of array with length %d",endIdx,argsLength));
		checkArgument(endIdx >= startIdx, "endIdx %s is smaller than startIdx", endIdx, startIdx);
		RuleMap rules = new RuleMap();
		T[] categories = enumType.getEnumConstants();
		for(int i = startIdx; i <= endIdx; i++) {
			String arg = args[i];
			T category = categories[i-startIdx];
			if(arg.contains("/")) {
				//split arg into value and unit
				String[] ruleArray = arg.split("/");
				Double value = Double.valueOf(ruleArray[0]);
				int unit = Integer.parseInt(ruleArray[1]);
				rules.put(category, new Rule<T>(category,value,unit));
			} else {
				Double value = Double.valueOf(arg);
				rules.put(category, new Rule<T>(category,value));
			}
		}
		return rules;
	}

	//utility helper function for parseScoringRulesAndEvaluate()
	//calculates cumulative sum of array a; each element in output array is the sum of elements 
	//in input array up to that index
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
