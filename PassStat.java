//TODO: extends Stat??
public class PassStat
{
	private PassCat cat;
	private int value;

	public PassStat() {
	}

	public PassCat getCat() {
		return cat;
	}

	public int getValue() {
		return value;
	}

	public enum PassCat {
		COMP("COMP"),
		INC("INC"),
		YDS("YDS"),
		TD("TD"),
		INT("INT"),
		SCK("SCK");

		private final String text;
		private static final int size = values().length;

		private PassCat(String text) {
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public static int size() {
			return size;
		}
	}
}
