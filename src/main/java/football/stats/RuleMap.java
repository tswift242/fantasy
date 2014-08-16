package football.stats;

import java.util.List;
import java.util.Map;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.players.modes.Modes;
import football.stats.Rule;
import football.stats.StatType;

/*
 * Implementation of a typesafe heterogeneous map which maps
 * StatType enums to corresponding rules of the same type.
 * Does not permit null keys or values.
 */

public final class RuleMap
{
	private final Map<Object,Rule<?>> map;

	public RuleMap() {
		map = new HashMap<Object,Rule<?>>();
	}

	public <T extends Enum<T> & StatType> void put(T category, Rule<T> rule) {
		// don't permit null key or null value
		checkNotNull(category, "category is null");
		checkNotNull(rule, "rule is null");
		T ruleCategory = rule.getCategory();
		checkArgument(ruleCategory == category,"rule category %s does not equal category key %s",
				ruleCategory.toString(),category.toString());
		//TODO: make defensive copy of rule if Rule becomes mutable
		map.put(category,rule);
	}

	@SuppressWarnings("unchecked")
	public <T extends Enum<T> & StatType> Rule<T> get(T category) {
		return (Rule<T>)map.get(category);
	}

	public void putAll(RuleMap ruleMap) {
		map.putAll(ruleMap.map);
	}

	@Override
	public String toString() {
		String result = "";
		for(Object key : map.keySet()) {
			result += (map.get(key).toString() + "\n");
		}
		return result.trim();
	}

	// convert RuleMap into args String[] for Rules corresponding to given mode, 
	// for compatability with command line verison of program and ResultsLogger class
	public String[] toArgs(Modes mode) {
		// get stat types associated with this mode
		List<Class<? extends StatType>> statTypes = mode.getStatTypes();
		// compute number of total categories
		int size = 0;
		for(Class<? extends StatType> statType : statTypes) {
			size += statType.getEnumConstants().length;
		}
		String[] args = new String[size+1];
		// mode is first arg
		args[0] = mode.toString();
		// add in category values as args
		int index = 1;
		for(Class<? extends StatType> statType : statTypes) {
			StatType[] categories = statType.getEnumConstants();
			for(StatType category : categories) {
				Rule<?> rule = map.get(category);
				Double value = rule.getValue();
				int unit = rule.getUnit();
				// string representation of Rule as it would appear on the command line
				String ruleString = value.toString();
				if(unit != 1) {
					ruleString += ("/" + Integer.toString(unit));
				}
				args[index++] = ruleString;
			}
		}
		return args;
	}
}
