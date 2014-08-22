package football.util;

public final class EnumUtils
{
	private EnumUtils() {} //NEVER USE THIS

	public static <T extends Enum<T>> String valuesToString(Class<T> enumType) {
		String values = "";
		for(T value : enumType.getEnumConstants()) {
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
