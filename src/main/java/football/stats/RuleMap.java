package football;

import java.util.Map;
import java.util.HashMap;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Rule;
import football.stats.StatType;

/*
 * Implementation of a typesafe heterogeneous map which maps
 * StatType enums to corresponding rules of the same type.
 */

public class RuleMap
{
	private Map<Object,Rule<?>> map;

	public RuleMap() {
		map = new HashMap<Object,Rule<?>>();
	}

	public <T extends Enum<T> & StatType> void put(T category, Rule<T> rule) {
		checkNotNull(category, "category is null");
		T ruleCategory = rule.getCategory();
		checkArgument(ruleCategory == category,"rule category %s does not equal category key %s",
				ruleCategory.toString(),category.toString());
		map.put(category,rule);
	}

	@SuppressWarnings("unchecked")
	public <T extends Enum<T> & StatType> Rule<T> get(T category) {
		return (Rule<T>)map.get(category);
	}

	public String toString() {
		String result = "";
		for(Object o : map.keySet()) {
			result += (o.toString() + ": " + map.get(o).toString() + "\n");
		}
		return result.trim();
	}
}
