package football.util;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import football.stats.StatType;

public final class StatCategoryUtils
{
	// stat category names which are not unique to across all StatType's
	public static final Set<String> repeatedCategoryNames = new HashSet<String>(Arrays.asList("YDS","TD","SCK","INT"));

	private StatCategoryUtils() {}

	public static <T extends Enum<T> & StatType> String toString(T category) {
		String result = category.getText();

		// pre-pend name of stat type (enum) to category name if category name if not unique
		if(repeatedCategoryNames.contains(result)) {
			String statTypeName = category.getClass().getSimpleName().toUpperCase();
			result = statTypeName + "_" + result;
		}

		return result;
	}
}
