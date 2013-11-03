package football.stats;

import football.stats.StatType;

/*
 * Class representing a football statistic. All statistic values must be integers.
 */

public final class Stat<T extends Enum<T> & StatType> extends TypedValue<T,Integer>
{
	//Value represented as an int. Stored to avoid redundant autoboxing.
	private final int intValue;

	public Stat(T category, Integer value) {
		super(category,value);
		this.intValue = value;
	}

	public int getIntValue() {
		return intValue;
	}
}
