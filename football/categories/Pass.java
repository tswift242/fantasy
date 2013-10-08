package football.categories;

public enum Pass
{
	COMP("COMP"),
	INC("INC"),
	YDS("YDS"),
	TD("TD"),
	INT("INT"),
	SCK("SCK");

	private final String text;
	private static final int size = values().length;
	private static final String valuesString = EnumUtil.valuesToString(Pass.class);

	private Pass(String text) {
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
