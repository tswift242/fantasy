package football;

//types of categories
public class Types
{
	//TODO: any way to save code with all these enums?? -- nested/hierarchical enums??
	public enum PassCats
	{
		COMP("COMP"),
		INC("INC"),
		YDS("YDS"),
		TD("TD"),
		INT("INT"),
		SCK("SCK");

		private final String text;
		private static final int size = values().length;

		private PassCats(String text) {
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public static int size() {
			return size;
		}
	}

	public enum RushCats
	{
		ATT("ATT"),
		YDS("YDS"),
		TD("TD");

		private final String text;
		private static final int size = values().length;

		private RushCats(String text) {
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public static int size() {
			return size;
		}
	}

	public enum RecCats
	{
		REC("REC"),
		YDS("YDS"),
		TD("TD");

		private final String text;
		private static final int size = values().length;

		private RecCats(String text) {
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public static int size() {
			return size;
		}
	}

	public enum KickCats
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

		private KickCats(String text) {
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public static int size() {
			return size;
		}
	}

	public enum DefCats
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

		private DefCats(String text) {
			this.text = text;
		}

		public String toString() {
			return text;
		}

		public static int size() {
			return size;
		}
	}

	public enum MiscCats
	{
		FUMB_TD("FUMB_TD"), //fumble recovered for td
		TWO_PT_CONV("2_PT_CONV"),
		FUMB("FUMB"),
		FUMB_LOST("FUMB_LOST");

		private final String text;
		private static final int size = values().length;

		private MiscCats(String text) {
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
