package football.util;

import java.util.Set;
import java.lang.reflect.InvocationTargetException;
import static com.google.common.base.Preconditions.checkNotNull;
import static com.google.common.base.Preconditions.checkArgument;

import football.stats.Stat;
import football.stats.StatType;

/*
 * Utility for validating method arguments, built on top of Google's Guava
 */

public final class ValidateUtils
{
	private ValidateUtils() {} //NEVER USE THIS

	public static <T> void checkArrayLength(T[] array, int length, String errorMessage) {
		checkArgument(array.length == length, errorMessage);
	}

	public static void checkArrayLength(double[] array, int length, String errorMessage) {
		checkArgument(array.length == length, errorMessage);
	}

	public static void checkArrayLength(int[] array, int length, String errorMessage) {
		checkArgument(array.length == length, errorMessage);
	}


	public static <T extends Enum<T> & StatType> void checkStatsSetSize(Set<Stat<T>> stats, Class<T> type) {
		//ugly, but no better alternative to using reflection (I think)
		int typeSize;
		try {
			typeSize = (Integer)type.getDeclaredMethod("size").invoke(null);
		} catch(NoSuchMethodException e) {
			throw new IllegalArgumentException(String.format("Class %s does not have a 'size' method",type.toString()), e);
		} catch(IllegalAccessException e) {
			throw new RuntimeException(String.format("Can't access method 'size' in class %s",type.toString()), e);
		} catch(InvocationTargetException e) {
			throw new RuntimeException(e);
		}
		int statsSize = stats.size();
		checkArgument(statsSize == typeSize, "stats' size %d does not equal %d",statsSize,typeSize);
	}


	//checks that stats is not null, and has the correct size
	public static <T extends Enum<T> & StatType> void checkStatsSetNotNullWithCorrectSize(Set<Stat<T>> stats, Class<T> type) {
		checkNotNull(stats, "stats is null");
		checkStatsSetSize(stats, type);
	}
}
