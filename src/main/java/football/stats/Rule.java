package football.stats;

import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.StatType;

/*
 * Class representing a fantasy football scoring rule. Permits floating point values.
 * Rule awards value many points for every unit quantity of the statistic specified by category.
 */

public final class Rule<T extends Enum<T> & StatType> extends TypedValue<T,Double>
{
	private final int unit; //could probably get away with eliminating this field
	//Value normalized by unit. Computed once for efficiency, as this will be used in evaluate() computations.
	private final double normalizedValue;

	public Rule(T category, Double value, int unit) {
		super(category,value);
		this.unit = unit;
		this.normalizedValue = (this.value / this.unit);
	}

	//uses default unit of 1
	public Rule(T category, Double value) {
		this(category,value,1);
	}

	public int getUnit() {
		return unit;
	}

	public double getNormalizedValue() {
		return normalizedValue;
	}

	//possible setter for unit which also updates normalizedValue -- probably better than constructing new Rule if just unit changes
	/*public void setUnit(int unit) {
		this.unit = unit;
		this.normalizedValue = (this.value / this.unit);
	}*/

	//Evaluate a stat using this rule
	public double evaluate(Stat<T> stat) {
		checkNotNull(stat, "stat is null");
		T ruleCategory = this.category;
		T statCategory = stat.getCategory();
		checkArgument(ruleCategory == statCategory,"rule category %s does not equal stat category %s",
				ruleCategory.toString(),statCategory.toString());
		return (this.normalizedValue * stat.getIntValue());
	}

	@Override
	public String toString() {
		String categoryValuePair = super.toString();
		if(unit == 1) {
			return categoryValuePair;
		} else {
			return String.format("(%s, %s / %d)", category.toString(), value.toString(), unit);
		}
	}
}
