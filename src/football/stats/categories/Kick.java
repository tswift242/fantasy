package football.stats.categories;

import football.stats.StatType;
import football.util.EnumUtil;

public enum Kick implements StatType
{
	PAT_MD("PAT_MD"),
	PAT_MS("PAT_MS"),
	FG_MD_0("FG_MD_0"),
	FG_MD_20("FG_MD_20"),
	FG_MD_30("FG_MD_30"),
	FG_MD_40("FG_MD_40"),
	FG_MD_50("FG_MD_50"),
	FG_MS_0("FG_MS_0"),
	FG_MS_20("FG_MS_20"),
	FG_MS_30("FG_MS_30"),
	FG_MS_40("FG_MS_40"),
	FG_MS_50("FG_MS_50");

	private final String text;
	private static final int size = values().length;
	private static final String valuesString = EnumUtil.valuesToString(Kick.class);

	private Kick(String text) {
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
