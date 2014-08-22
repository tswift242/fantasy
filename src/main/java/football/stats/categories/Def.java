package football.stats.categories;

import football.stats.StatType;
import football.util.EnumUtils;
import football.util.StatCategoryUtils;

public enum Def implements StatType
{
	SCK("SCK"),
	INT("INT"),
	FUMB("FUMB_REC"),
	SAF("SAF"),
	TD("TD"),
	RET("RET"),
	PTS("PTS_ALLOWED"),
	YDS("YDS_ALLOWED");

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
		return StatCategoryUtils.toString(this);
	}

	public String getText() {
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
