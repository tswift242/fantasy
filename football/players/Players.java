package football.players;

import java.util.LinkedHashSet;

import football.Stat;
import football.categories.*;

public final class Players {

	//QB
	public static final QB BRADY = new QB("brady",
			newSet(new Stat<Pass>(Pass.COMP,401),new Stat<Pass>(Pass.INC,236),new Stat<Pass>(Pass.YDS,4827),new Stat<Pass>(Pass.TD,34),new Stat<Pass>(Pass.INT,8),new Stat<Pass>(Pass.SCK,27)),
			newSet(new Stat<Rush>(Rush.ATT,23),new Stat<Rush>(Rush.YDS,32),new Stat<Rush>(Rush.TD,4)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,2)));
	public static final QB PEYTON = new QB("peyton",
			newSet(new Stat<Pass>(Pass.COMP,400),new Stat<Pass>(Pass.INC,183),new Stat<Pass>(Pass.YDS,4659),new Stat<Pass>(Pass.TD,37),new Stat<Pass>(Pass.INT,11),new Stat<Pass>(Pass.SCK,21)),
			newSet(new Stat<Rush>(Rush.ATT,23),new Stat<Rush>(Rush.YDS,6),new Stat<Rush>(Rush.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,1),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,2)));
	public static final QB RODGERS = new QB("rodgers",
			newSet(new Stat<Pass>(Pass.COMP,371),new Stat<Pass>(Pass.INC,181),new Stat<Pass>(Pass.YDS,4295),new Stat<Pass>(Pass.TD,39),new Stat<Pass>(Pass.INT,8),new Stat<Pass>(Pass.SCK,51)),
			newSet(new Stat<Rush>(Rush.ATT,54),new Stat<Rush>(Rush.YDS,269),new Stat<Rush>(Rush.TD,2)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,1),new Stat<Misc>(Misc.FUMB_LOST,4),new Stat<Misc>(Misc.FUMB,5)));
	public static final QB PALMER = new QB("palmer",
			newSet(new Stat<Pass>(Pass.COMP,345),new Stat<Pass>(Pass.INC,220),new Stat<Pass>(Pass.YDS,4018),new Stat<Pass>(Pass.TD,22),new Stat<Pass>(Pass.INT,14),new Stat<Pass>(Pass.SCK,26)),
			newSet(new Stat<Rush>(Rush.ATT,18),new Stat<Rush>(Rush.YDS,36),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,2),new Stat<Misc>(Misc.FUMB_LOST,5),new Stat<Misc>(Misc.FUMB,7)));
	public static final QB KOLB = new QB("kolb",
			newSet(new Stat<Pass>(Pass.COMP,109),new Stat<Pass>(Pass.INC,74),new Stat<Pass>(Pass.YDS,1169),new Stat<Pass>(Pass.TD,8),new Stat<Pass>(Pass.INT,3),new Stat<Pass>(Pass.SCK,27)),
			newSet(new Stat<Rush>(Rush.ATT,16),new Stat<Rush>(Rush.YDS,100),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,2)));
	public static final QB QUINN = new QB("quinn",
			newSet(new Stat<Pass>(Pass.COMP,112),new Stat<Pass>(Pass.INC,85),new Stat<Pass>(Pass.YDS,1141),new Stat<Pass>(Pass.TD,2),new Stat<Pass>(Pass.INT,8),new Stat<Pass>(Pass.SCK,21)),
			newSet(new Stat<Rush>(Rush.ATT,19),new Stat<Rush>(Rush.YDS,66),new Stat<Rush>(Rush.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,1)));
	public static final QB LEINART = new QB("leinart",
			newSet(new Stat<Pass>(Pass.COMP,16),new Stat<Pass>(Pass.INC,17),new Stat<Pass>(Pass.YDS,115),new Stat<Pass>(Pass.TD,0),new Stat<Pass>(Pass.INT,1),new Stat<Pass>(Pass.SCK,1)),
			newSet(new Stat<Rush>(Rush.ATT,0),new Stat<Rush>(Rush.YDS,0),new Stat<Rush>(Rush.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,0)));
	public static final QB SANCHEZ = new QB("sanchez",
			newSet(new Stat<Pass>(Pass.COMP,246),new Stat<Pass>(Pass.INC,207),new Stat<Pass>(Pass.YDS,2883),new Stat<Pass>(Pass.TD,13),new Stat<Pass>(Pass.INT,18),new Stat<Pass>(Pass.SCK,34)),
			newSet(new Stat<Rush>(Rush.ATT,22),new Stat<Rush>(Rush.YDS,28),new Stat<Rush>(Rush.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,8),new Stat<Misc>(Misc.FUMB,14)));
	public static final QB WEEDEN = new QB("weeden",
			newSet(new Stat<Pass>(Pass.COMP,297),new Stat<Pass>(Pass.INC,220),new Stat<Pass>(Pass.YDS,3385),new Stat<Pass>(Pass.TD,14),new Stat<Pass>(Pass.INT,17),new Stat<Pass>(Pass.SCK,28)),
			newSet(new Stat<Rush>(Rush.ATT,27),new Stat<Rush>(Rush.YDS,111),new Stat<Rush>(Rush.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,1),new Stat<Misc>(Misc.FUMB,6)));
	//RB
	public static final RB LYNCH = new RB("lynch",
			newSet(new Stat<Rush>(Rush.ATT,315),new Stat<Rush>(Rush.YDS,1590),new Stat<Rush>(Rush.TD,11)),
			newSet(new Stat<Rec>(Rec.REC,23),new Stat<Rec>(Rec.YDS,196),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,5)));
	public static final RB RICE = new RB("rice",
			newSet(new Stat<Rush>(Rush.ATT,257),new Stat<Rush>(Rush.YDS,1143),new Stat<Rush>(Rush.TD,9)),
			newSet(new Stat<Rec>(Rec.REC,61),new Stat<Rec>(Rec.YDS,478),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,1)));
	public static final RB FOSTER = new RB("foster",
			newSet(new Stat<Rush>(Rush.ATT,351),new Stat<Rush>(Rush.YDS,1424),new Stat<Rush>(Rush.TD,15)),
			newSet(new Stat<Rec>(Rec.REC,40),new Stat<Rec>(Rec.YDS,217),new Stat<Rec>(Rec.TD,2)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,3)));
	public static final RB RBUSH = new RB("r bush",
			newSet(new Stat<Rush>(Rush.ATT,227),new Stat<Rush>(Rush.YDS,986),new Stat<Rush>(Rush.TD,6)),
			newSet(new Stat<Rec>(Rec.REC,35),new Stat<Rec>(Rec.YDS,292),new Stat<Rec>(Rec.TD,2)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,4)));
	public static final RB MATHEWS = new RB("mathews",
			newSet(new Stat<Rush>(Rush.ATT,184),new Stat<Rush>(Rush.YDS,707),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Rec>(Rec.REC,39),new Stat<Rec>(Rec.YDS,252),new Stat<Rec>(Rec.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,2)));
	public static final RB JONESDREW = new RB("jones-drew",
			newSet(new Stat<Rush>(Rush.ATT,86),new Stat<Rush>(Rush.YDS,414),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Rec>(Rec.REC,14),new Stat<Rec>(Rec.YDS,86),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,2)));
	public static final RB HILLMAN = new RB("hillman",
			newSet(new Stat<Rush>(Rush.ATT,85),new Stat<Rush>(Rush.YDS,330),new Stat<Rush>(Rush.TD,1)),
			newSet(new Stat<Rec>(Rec.REC,10),new Stat<Rec>(Rec.YDS,62),new Stat<Rec>(Rec.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,1),new Stat<Misc>(Misc.FUMB,2)));
	public static final RB REDMAN = new RB("redman",
			newSet(new Stat<Rush>(Rush.ATT,110),new Stat<Rush>(Rush.YDS,410),new Stat<Rush>(Rush.TD,2)),
			newSet(new Stat<Rec>(Rec.REC,19),new Stat<Rec>(Rec.YDS,244),new Stat<Rec>(Rec.TD,0)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,3),new Stat<Misc>(Misc.FUMB,3)));
	//WR
	public static final WR CJOHNSON = new WR("c johnson",
			newSet(new Stat<Rec>(Rec.REC,122),new Stat<Rec>(Rec.YDS,1964),new Stat<Rec>(Rec.TD,5)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,3),new Stat<Misc>(Misc.FUMB,3)));
	public static final WR JJONES = new WR("j jones",
			newSet(new Stat<Rec>(Rec.REC,79),new Stat<Rec>(Rec.YDS,1198),new Stat<Rec>(Rec.TD,10)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,0)));
	public static final WR DBRYANT = new WR("d bryant",
			newSet(new Stat<Rec>(Rec.REC,92),new Stat<Rec>(Rec.YDS,1382),new Stat<Rec>(Rec.TD,12)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,1),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,5)));
	public static final WR AMENDOLA = new WR("amendola",
			newSet(new Stat<Rec>(Rec.REC,63),new Stat<Rec>(Rec.YDS,666),new Stat<Rec>(Rec.TD,3)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,1),new Stat<Misc>(Misc.FUMB_LOST,2),new Stat<Misc>(Misc.FUMB,3)));
	public static final WR BEDWARDS = new WR("b edwards",
			newSet(new Stat<Rec>(Rec.REC,18),new Stat<Rec>(Rec.YDS,199),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,0)));
	public static final WR MANNINGHAM = new WR("manningham",
			newSet(new Stat<Rec>(Rec.REC,42),new Stat<Rec>(Rec.YDS,449),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,1),new Stat<Misc>(Misc.FUMB,2)));
	public static final WR SHOLMES = new WR("s holmes",
			newSet(new Stat<Rec>(Rec.REC,20),new Stat<Rec>(Rec.YDS,272),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,1),new Stat<Misc>(Misc.FUMB,1)));
	public static final WR HDOUGLAS = new WR("h douglas",
			newSet(new Stat<Rec>(Rec.REC,38),new Stat<Rec>(Rec.YDS,395),new Stat<Rec>(Rec.TD,1)),
			newSet(new Stat<Misc>(Misc.FUMB_TD,0),new Stat<Misc>(Misc.TWO_PT_CONV,0),new Stat<Misc>(Misc.FUMB_LOST,0),new Stat<Misc>(Misc.FUMB,1)));
	//DEF
	public static final DEF CHICAGO = new DEF("chicago",
			newSet(new Stat<Def>(Def.SCK,41),new Stat<Def>(Def.INT,24),new Stat<Def>(Def.FUMB,20),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,10),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,255),new Stat<Def>(Def.YDS,5050)));
	public static final DEF SANFRAN = new DEF("san fransisco",
			newSet(new Stat<Def>(Def.SCK,38),new Stat<Def>(Def.INT,14),new Stat<Def>(Def.FUMB,11),new Stat<Def>(Def.SAF,1),new Stat<Def>(Def.TD,4),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,265),new Stat<Def>(Def.YDS,4710)));
	public static final DEF SEATTLE = new DEF("seattle",
			newSet(new Stat<Def>(Def.SCK,36),new Stat<Def>(Def.INT,18),new Stat<Def>(Def.FUMB,13),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,5),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,255),new Stat<Def>(Def.YDS,5050)));
	public static final DEF CLEVELAND = new DEF("cleveland",
			newSet(new Stat<Def>(Def.SCK,38),new Stat<Def>(Def.INT,17),new Stat<Def>(Def.FUMB,12),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,2),new Stat<Def>(Def.RET,1),new Stat<Def>(Def.PTS,356),new Stat<Def>(Def.YDS,5821)));
	public static final DEF NEWORLEANS = new DEF("new orleans",
			newSet(new Stat<Def>(Def.SCK,30),new Stat<Def>(Def.INT,15),new Stat<Def>(Def.FUMB,11),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,5),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,434),new Stat<Def>(Def.YDS,7042)));
	public static final DEF JACKSONVILLE = new DEF("jacksonville",
			newSet(new Stat<Def>(Def.SCK,20),new Stat<Def>(Def.INT,12),new Stat<Def>(Def.FUMB,11),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,1),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,414),new Stat<Def>(Def.YDS,6088)));
	public static final DEF OAKLAND = new DEF("oakland",
			newSet(new Stat<Def>(Def.SCK,25),new Stat<Def>(Def.INT,11),new Stat<Def>(Def.FUMB,8),new Stat<Def>(Def.SAF,0),new Stat<Def>(Def.TD,0),new Stat<Def>(Def.RET,0),new Stat<Def>(Def.PTS,431),new Stat<Def>(Def.YDS,5672)));
	//K
	public static final K GOSTKOWSKI = new K("gostkowski",
			newSet(new Stat<Kick>(Kick.PAT_MD,66),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,8),new Stat<Kick>(Kick.FG_MD_30,10),new Stat<Kick>(Kick.FG_MD_40,9),new Stat<Kick>(Kick.FG_MD_50,2),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,4),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K MBRYANT = new K("m bryant",
			newSet(new Stat<Kick>(Kick.PAT_MD,44),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,1),new Stat<Kick>(Kick.FG_MD_20,8),new Stat<Kick>(Kick.FG_MD_30,10),new Stat<Kick>(Kick.FG_MD_40,10),new Stat<Kick>(Kick.FG_MD_50,4),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,1),new Stat<Kick>(Kick.FG_MS_30,1),new Stat<Kick>(Kick.FG_MS_40,3),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K TUCKER = new K("tucker",
			newSet(new Stat<Kick>(Kick.PAT_MD,42),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,8),new Stat<Kick>(Kick.FG_MD_30,8),new Stat<Kick>(Kick.FG_MD_40,10),new Stat<Kick>(Kick.FG_MD_50,4),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,0),new Stat<Kick>(Kick.FG_MS_40,3),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K SUISHAM = new K("suisham",
			newSet(new Stat<Kick>(Kick.PAT_MD,34),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,7),new Stat<Kick>(Kick.FG_MD_30,8),new Stat<Kick>(Kick.FG_MD_40,12),new Stat<Kick>(Kick.FG_MD_50,1),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,1),new Stat<Kick>(Kick.FG_MS_30,0),new Stat<Kick>(Kick.FG_MS_40,0),new Stat<Kick>(Kick.FG_MS_50,2)));
	public static final K CUNDIFF = new K("cundiff",
			newSet(new Stat<Kick>(Kick.PAT_MD,17),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,1),new Stat<Kick>(Kick.FG_MD_30,3),new Stat<Kick>(Kick.FG_MD_40,3),new Stat<Kick>(Kick.FG_MD_50,0),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,1),new Stat<Kick>(Kick.FG_MS_50,2)));
	public static final K FORBATH = new K("forbath",
			newSet(new Stat<Kick>(Kick.PAT_MD,33),new Stat<Kick>(Kick.PAT_MS,1),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,3),new Stat<Kick>(Kick.FG_MD_30,2),new Stat<Kick>(Kick.FG_MD_40,11),new Stat<Kick>(Kick.FG_MD_50,1),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,1),new Stat<Kick>(Kick.FG_MS_40,0),new Stat<Kick>(Kick.FG_MS_50,0)));
	public static final K SCOBEE = new K("scobee",
			newSet(new Stat<Kick>(Kick.PAT_MD,18),new Stat<Kick>(Kick.PAT_MS,1),new Stat<Kick>(Kick.FG_MD_0,1),new Stat<Kick>(Kick.FG_MD_20,4),new Stat<Kick>(Kick.FG_MD_30,8),new Stat<Kick>(Kick.FG_MD_40,11),new Stat<Kick>(Kick.FG_MD_50,1),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,0),new Stat<Kick>(Kick.FG_MS_40,2),new Stat<Kick>(Kick.FG_MS_50,1)));
	public static final K FOLK = new K("folk",
			newSet(new Stat<Kick>(Kick.PAT_MD,30),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,7),new Stat<Kick>(Kick.FG_MD_30,6),new Stat<Kick>(Kick.FG_MD_40,5),new Stat<Kick>(Kick.FG_MD_50,3),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,1),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,2),new Stat<Kick>(Kick.FG_MS_50,1)));
	public static final K CROSBY = new K("crosby",
			newSet(new Stat<Kick>(Kick.PAT_MD,50),new Stat<Kick>(Kick.PAT_MS,0),new Stat<Kick>(Kick.FG_MD_0,0),new Stat<Kick>(Kick.FG_MD_20,5),new Stat<Kick>(Kick.FG_MD_30,5),new Stat<Kick>(Kick.FG_MD_40,9),new Stat<Kick>(Kick.FG_MD_50,2),new Stat<Kick>(Kick.FG_MS_0,0),new Stat<Kick>(Kick.FG_MS_20,0),new Stat<Kick>(Kick.FG_MS_30,2),new Stat<Kick>(Kick.FG_MS_40,3),new Stat<Kick>(Kick.FG_MS_50,7)));

	private Players() {}

	//utility for easy set construction
	//constructs LinkedHashSet to maintain insertion order
	@SafeVarargs //ingore "unchecked generic array creation for varargs" -- Java 7 ONLY
	private static <T extends Enum<T>> LinkedHashSet<Stat<T>> newSet(Stat<T> ... stats) {
		LinkedHashSet<Stat<T>> set = new LinkedHashSet<Stat<T>>();
		for(Stat<T> stat : stats) {
			set.add(stat);
		}
		return set;
	}
}
