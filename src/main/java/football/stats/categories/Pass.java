package football.stats.categories;

import football.stats.StatType;
import football.util.EnumUtils;
import football.util.StatCategoryUtils;

public enum Pass implements StatType
{
	COMP("COMP"),
	INC("INC"),
	YDS("YDS"),
	TD("TD"),
	INT("INT"),
	SCK("SCK");

	private final String text;
	private static final int size = values().length;
	private static final int yardsUnit = 25;
	private static final String valuesString = EnumUtils.valuesToString(Pass.class);

	private Pass(String text) {
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

	public static String valuesToString() {
		return valuesString;
	}
}
