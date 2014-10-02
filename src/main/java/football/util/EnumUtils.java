package football.util;

public final class EnumUtils
{
	private EnumUtils() {} //NEVER USE THIS

	// type T should extend Enum, but does not because that information is not always statically
	// avaiable for callers
	public static <T> String valuesToString(Class<T> enumType) {
		String values = "";

		T[] valuesArray = enumType.getEnumConstants();
		if(valuesArray == null) {
			throw new IllegalArgumentException("Type " + enumType.getName() + " is not an Enum");
		}

		for(T value : valuesArray) {
			//TODO: make 10 below parameter
			values += String.format("%-10s ",value.toString());
		}

		return values.trim();
	}

	// almost the same as Enum.valueOf(enumType, text), except compares against Enum's
	// toString() value, instead of name()
	public static <T extends Enum<T>> T fromString(Class<T> enumType, String text) {
		if(text != null) {
			for(T value : enumType.getEnumConstants()) {
				if(text.equalsIgnoreCase(value.toString())) {
					return value;
				}
			}
		}
		throw new IllegalArgumentException("No constant with text " + text + " found");
	}
}
