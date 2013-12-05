package football.players.modes;

import football.util.EnumUtil;

public enum Modes
{
	//TODO: add parseScoringRules method which each value implements by calling 
	//corresponding method in player class
	QB("QB"),
	RB("RB"),
	WR("WR"),
	K("K"),
	DEF("DEF"),
	ALL("ALL");

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
}
