package football.stats.categories;

import football.stats.StatType;
import football.util.StatCategoryUtils;

public enum Rush implements StatType
{
	ATT("ATT"),
	YDS("YDS"),
	TD("TD");

	private final String text;
	private static final int size = values().length;
	private static final int yardsUnit = 10;

	private Rush(String text) {
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
}
