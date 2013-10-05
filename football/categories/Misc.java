package football.categories;

public enum Misc
{
	FUMB_TD("FUMB_TD"), //fumble recovered for td
	TWO_PT_CONV("2_PT_CONV"),
	FUMB_LOST("FUMB_LOST"),
	FUMB("FUMB");

	private final String text;
	private static final int size = values().length;

	private Misc(String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public static int size() {
		return size;
	}
}
