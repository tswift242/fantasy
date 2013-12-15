package football.core.graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JPanel;

import football.stats.categories.*;

/*
 * Panel displaying text boxes for modifying scoring rules of all stat types
 */

public final class RulesPanel extends JPanel
{
	private static final long serialVersionUID = 4818675092111795395L;
	private static final int NUM_STAT_TYPES = 6;
	//TODO: create map of rule panels to stat types class

	public RulesPanel() {
		this.setLayout(new GridLayout(NUM_STAT_TYPES, 1));
		/*Dimension subPanelDimensions = new Dimension((int)dimensions.getWidth(),
													 (int)(dimensions.getHeight()/6));*/
		// adds one of each stat type rules panel
		StatTypeRulesPanel<Pass> passRulesPanel = new StatTypeRulesPanel<Pass>(Pass.class);
		StatTypeRulesPanel<Rush> rushRulesPanel = new StatTypeRulesPanel<Rush>(Rush.class);
		StatTypeRulesPanel<Rec> recRulesPanel = new StatTypeRulesPanel<Rec>(Rec.class);
		StatTypeRulesPanel<Misc> miscRulesPanel = new StatTypeRulesPanel<Misc>(Misc.class);
		StatTypeRulesPanel<Kick> kickRulesPanel = new StatTypeRulesPanel<Kick>(Kick.class);
		StatTypeRulesPanel<Def> defRulesPanel = new StatTypeRulesPanel<Def>(Def.class);
		this.add(passRulesPanel);
		this.add(rushRulesPanel);
		this.add(recRulesPanel);
		this.add(miscRulesPanel);
		this.add(kickRulesPanel);
		this.add(defRulesPanel);
		//this.setPreferredSize(dimensions);
	}
}
