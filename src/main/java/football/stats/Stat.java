package football.stats;

import football.stats.StatType;

/*
 * Class representing a football statistic. All statistic values must be integers.
 */

public final class Stat<T extends Enum<T> & StatType> extends TypedValue<T,Integer>
{
	public Stat(T category, Integer value) {
		super(category,value);
	}
}
