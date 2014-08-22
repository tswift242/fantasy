package football.stats.categories;

import football.stats.StatType;
import football.util.EnumUtils;

public enum Rec implements StatType
{
	REC("REC"),
	YDS("YDS"),
	TD("TD");

	private final String text;
	private static final int size = values().length;
	private static final int yardsUnit = 10;
	private static final String valuesString = EnumUtils.valuesToString(Rec.class);

	private Rec(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public static int size() {
		return size;
	}

	public static int getYardsUnit() {
		return yardsUnit;
	}

	public static String valuesToString() {
		return valuesString;
	}
}
