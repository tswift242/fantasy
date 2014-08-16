package football.players.modes;

import java.util.ArrayList;
import java.util.List;
import static com.google.common.base.Preconditions.checkNotNull;

import football.players.DEF;
import football.players.K;
import football.players.QB;
import football.players.RB;
import football.players.WR;
import football.stats.RuleMap;
import football.stats.StatType;
import football.stats.categories.*;
import football.util.EnumUtil;

public enum Modes
{
	QB("QB", newList(Pass.class, Rush.class, Misc.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.QB.parseScoringRules(args);
		}
	},
	RB("RB", newList(Rush.class, Rec.class, Misc.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.RB.parseScoringRules(args);
		}
	},
	WR("WR", newList(Rec.class, Misc.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.WR.parseScoringRules(args);
		}
	},
	K("K", newList(Kick.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.K.parseScoringRules(args);
		}
	},
	DEF("DEF", newList(Def.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.DEF.parseScoringRules(args);
		}
	},
	ALL("ALL", newList(Pass.class, Rush.class, Rec.class, Kick.class, Def.class)) {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			//TODO
			return new RuleMap();
		}
	};

	private final String text;
	// list of associated StatType's with each Mode
	private final List<Class<? extends StatType>> statTypes;

	private Modes(String text, List<Class<? extends StatType>> statTypes) {
		this.text = text;
		this.statTypes = statTypes;
	}

	@Override
	public String toString() {
		return text;
	}

	public static Modes fromString(String text) {
		return EnumUtil.fromString(Modes.class,text);
	}

	public abstract RuleMap parseScoringRules(String[] args);

	public List<Class<? extends StatType>> getStatTypes() {
		return statTypes;
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
