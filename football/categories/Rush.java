package football.categories;

public enum Rush
{
	ATT("ATT"),
	YDS("YDS"),
	TD("TD");

	private final String text;
	private static final int size = values().length;

	private Rush(String text) {
		this.text = text;
	}

	public String toString() {
		return text;
	}

	public static int size() {
		return size;
	}
}
