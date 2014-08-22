package football.util;

import java.util.Set;
import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkPositionIndex;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.Stat;
import football.stats.StatType;

public final class PlayerUtils
{
	private static final Logger logger = LoggerFactory.getLogger(PlayerUtils.class.getName());

	private PlayerUtils() {} //NEVER USE THIS

	//utility helper function for evalute()
	//takes dot product of stats set and corresponding set of rules in RuleMap
	public static <T extends Enum<T> & StatType> double dot(Set<Stat<T>> stats, RuleMap rules) {
		double sum = 0.0;
		for(Stat<T> stat : stats) {
			T category = stat.getCategory();
			Rule<T> rule = rules.get(category);
			// rule == null means that there's no rule for the provided category, i.e.
			// the rule isn't contributing to the players' scores
			// (equivalent to a rule with value 0)
			if(rule != null) {
				sum += rule.evaluate(stat);
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
			rules.put(category, parseRuleText(arg, category));
		}
		return rules;
	}

	// parses rule text for value and unit and creates a rule. Returns null if rule is invalid.
	public static <T extends Enum<T> & StatType> Rule<T> parseRuleText(String text, T category) {
		// handle invalid rule values first
		if(StringUtils.isBlank(text)) {
			logger.warn("The rule for {} is blank. Using the value of 0.0 instead.", category.toString());
			return null;
		}

		// split into value and unit
		String[] ruleComponents = text.split("/");

		// extract value from first component
		Double value;
		try {
			value = Double.valueOf(ruleComponents[0]);
		} catch(NumberFormatException e) {
			logger.error("The rule for {} is not a valid number. Using the value of 0.0 instead.", category.toString());
			return null;
		}

		// extract unit from second component if provided
		int unit = 1;
		if(ruleComponents.length > 1) {
			try {
				unit = Integer.parseInt(ruleComponents[1]);
			} catch(NumberFormatException e) {
				logger.error("The rule for {} is not a valid number. Using the value of 0.0 instead.", category.toString());
				return null;
			}
		}
		return new Rule<T>(category, value, unit);
	}

	//TODO: combine this with ResultsLogger.listToString() and make generic toString() for
	//collections?
	public static <T extends Enum<T> & StatType> String statsToString(Set<Stat<T>> set) {
		String result = "";
		for(Stat<T> stat : set) {
			result += String.format("%-6s ",Integer.toString(stat.getIntValue()));
		}
		return result.trim();
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
}
