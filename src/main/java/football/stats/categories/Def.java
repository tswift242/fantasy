package football.stats.categories;

import football.stats.StatType;
import football.util.EnumUtils;

public enum Def implements StatType
{
	SCK("SCK"),
	INT("INT"),
	FUMB("FUMB"),
	SAF("SAF"),
	TD("TD"),
	RET("RET"),
	PTS("PTS"),
	YDS("YDS");

	private final String text;
	private static final int size = values().length;
	private static final int yardsUnit = 200;
	private static final int pointsUnit = 7;
	private static final String valuesString = EnumUtils.valuesToString(Def.class);

	private Def(String text) {
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

	public static int getPointsUnit() {
		return pointsUnit;
	}

	public static String valuesToString() {
		return valuesString;
	}
}
