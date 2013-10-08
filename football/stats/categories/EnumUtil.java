package football.stats.categories;

public final class EnumUtil
{
	private EnumUtil() {} //NEVER USE THIS

	public static <T extends Enum<T>> String valuesToString(Class<T> enumType) {
		String values = "";
		for(T category : enumType.getEnumConstants()) {
			values += (category.toString() + "\t\t");
		}
		return values.trim();
	}
}
