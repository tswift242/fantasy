package football.players.modes;

import football.players.DEF;
import football.players.K;
import football.players.QB;
import football.players.RB;
import football.players.WR;
import football.stats.RuleMap;
import football.util.EnumUtil;

public enum Modes
{
	QB("QB") {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.QB.parseScoringRules(args);
		}
	},
	RB("RB") {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.RB.parseScoringRules(args);
		}
	},
	WR("WR") {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.WR.parseScoringRules(args);
		}
	},
	K("K") {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.K.parseScoringRules(args);
		}
	},
	DEF("DEF") {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			return football.players.DEF.parseScoringRules(args);
		}
	},
	ALL("ALL") {
		@Override
		public RuleMap parseScoringRules(String[] args) {
			//TODO
			return new RuleMap();
		}
	};

	private final String text;

	private Modes(String text) {
		this.text = text;
	}

	@Override
	public String toString() {
		return text;
	}

	public static Modes fromString(String text) {
		return EnumUtil.fromString(Modes.class,text);
	}

	public abstract RuleMap parseScoringRules(String[] args);
}
