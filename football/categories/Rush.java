package football.categories;

public enum Rush
{
	ATT("ATT"),
	YDS("YDS"),
	TD("TD");

	private final String text;
	private static final int size = values().length;
	private static final String valuesString = EnumUtil.valuesToString(Rush.class);

	private Rush(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public static int size() {
		return size;
	}

	public static String valuesToString() {
		return valuesString;
	}
}
