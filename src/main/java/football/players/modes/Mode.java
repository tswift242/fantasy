package football.players.modes;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.RuleMap;
import football.stats.StatType;
import football.stats.categories.*;
import football.util.EnumUtils;

public enum Mode
{
	QB("QB", newList(Pass.class, Rush.class, Misc.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.QB.parseScoringRules(args);
		}

		@Override
		public int getScorePrecision() {
			return 2;
		}
	},
	RB("RB", newList(Rush.class, Rec.class, Misc.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.RB.parseScoringRules(args);
		}

		@Override
		public int getScorePrecision() {
			return 1;
		}
	},
	WR("WR", newList(Rec.class, Misc.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.WR.parseScoringRules(args);
		}

		@Override
		public int getScorePrecision() {
			return 1;
		}
	},
	K("K", newList(Kick.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.K.parseScoringRules(args);
		}

		@Override
		public int getScorePrecision() {
			return 1;
		}
	},
	DEF("DEF", newList(Def.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.DEF.parseScoringRules(args);
		}

		@Override
		public int getScorePrecision() {
			return 1;
		}
	},
	ALL("ALL", newList(Pass.class, Rush.class, Rec.class, Kick.class, Def.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			//TODO
			return new RuleMap();
		}

		@Override
		public int getScorePrecision() {
			return 2;
		}
	};


	private final String text;
	// list of associated StatType's with each Mode
	private final List<Class<? extends StatType>> statTypes;
	// string containing all relevant stat categories for this mode delimited by whitespace
	private final String categoriesString;

	private Mode(String text, List<Class<? extends StatType>> statTypes) {
		this.text = text;
		this.statTypes = statTypes;
		this.categoriesString = categoriesToString();
	}

	@Override
	public String toString() {
		return text;
	}

	public static Mode fromString(String text) {
		return EnumUtils.fromString(Mode.class, text);
	}

	public List<Class<? extends StatType>> getStatTypes() {
		return statTypes;
	}

	public String getCategoriesString() {
		return categoriesString;
	}

    public abstract RuleMap parseScoringRules(String[] args);

	// returns display precision for player scores for players corresponding to this Mode
	public abstract int getScorePrecision();



	private String categoriesToString() {
		String results = "";

		String delimiter = "\t\t";
		for(Class<? extends StatType> statType : statTypes) {
			results += (EnumUtils.valuesToString(statType) + delimiter);
		}

		return results.trim();
	}

	//TODO: throw in Util class
	@SafeVarargs //ignore "unchecked generic array creation for varargs" -- Java 7 ONLY
	private static List<Class<? extends StatType>> newList(Class<? extends StatType> ... statTypes) {
		List<Class<? extends StatType>> list = new ArrayList<Class<? extends StatType>>();
		for(Class<? extends StatType> statType: statTypes) {
			checkNotNull(statType, "statType %s is null", statType.toString());
			list.add(statType);
		}
		return list;
	}
}
