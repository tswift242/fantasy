package football.stats;

import football.stats.StatType;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

/*
 * Class representing a fantasy football scoring rule. Permits floating point values.
 * Rule awards value many points for every unit quantity of the statistic specified by category.
 */

public final class Rule<T extends Enum<T> & StatType> extends TypedValue<T,Double>
{
	private final int unit;

	public Rule(T category, Double value, int unit) {
		super(category,value);
		this.unit = unit;
	}

	//uses default unit of 1
	public Rule(T category, Double value) {
		this(category,value,1);
	}

	public double evaluate(Stat<T> stat) {
		checkNotNull(stat, "stat is null");
		T ruleCategory = this.category;
		T statCategory = stat.getCategory();
		checkArgument(ruleCategory == statCategory,"rule category %s does not equal stat category %s",ruleCategory.toString(),statCategory.toString());
		return (this.value * (stat.getValue() / this.unit));
	}
}
