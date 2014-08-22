package football.stats.categories;

import football.stats.StatType;
import football.util.EnumUtils;

public enum Misc implements StatType
{
	FUMB_TD("FUMB_TD"), //fumble recovered for td
	TWO_PT_CONV("2_PT_CONV"),
	FUMB_LOST("FUMB_LOST"),
	FUMB("FUMB");

	private final String text;
	private static final int size = values().length;
	private static final String valuesString = EnumUtils.valuesToString(Misc.class);

	private Misc(String text) {
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
