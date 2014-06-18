package football.stats;

import java.util.Map;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

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
		for(Object o : map.keySet()) {
			//result += (o.toString() + ": " + map.get(o).toString() + "\n");
			result += (map.get(o).toString() + "\n");
		}
		return result.trim();
	}
}
