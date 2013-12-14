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

	public RulesPanel(Dimension dimensions) {
		this.setLayout(new GridLayout(NUM_STAT_TYPES, 1));
		Dimension subPanelDimensions = new Dimension((int)dimensions.getWidth(),
													 (int)(dimensions.getHeight()/6));
		// adds one of each stat type rules panel
		this.add(new StatTypeRulesPanel<Pass>(Pass.class, subPanelDimensions));
		this.add(new StatTypeRulesPanel<Rush>(Rush.class, subPanelDimensions));
		this.add(new StatTypeRulesPanel<Rec>(Rec.class, subPanelDimensions));
		this.add(new StatTypeRulesPanel<Misc>(Misc.class, subPanelDimensions));
		this.add(new StatTypeRulesPanel<Kick>(Kick.class, subPanelDimensions));
		this.add(new StatTypeRulesPanel<Def>(Def.class, subPanelDimensions));
		this.setPreferredSize(dimensions);
	}
}
