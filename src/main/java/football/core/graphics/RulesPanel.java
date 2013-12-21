package football.core.graphics;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JPanel;

import football.stats.categories.*;

/*
 * Panel displaying text boxes for modifying scoring rules of all stat types
 */

public final class RulesPanel extends JPanel
{
	private static final long serialVersionUID = 4818675092111795395L;
	//TODO: create map of rule panels to stat types class

	public RulesPanel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;

		// adds one of each stat type rules panel
		StatTypeRulesPanel<Pass> passRulesPanel = new StatTypeRulesPanel<Pass>(Pass.class);
		StatTypeRulesPanel<Rush> rushRulesPanel = new StatTypeRulesPanel<Rush>(Rush.class);
		StatTypeRulesPanel<Rec> recRulesPanel = new StatTypeRulesPanel<Rec>(Rec.class);
		StatTypeRulesPanel<Misc> miscRulesPanel = new StatTypeRulesPanel<Misc>(Misc.class);
		StatTypeRulesPanel<Kick> kickRulesPanel = new StatTypeRulesPanel<Kick>(Kick.class);
		StatTypeRulesPanel<Def> defRulesPanel = new StatTypeRulesPanel<Def>(Def.class);
		this.add(passRulesPanel, c);
		c.gridy++;
		this.add(rushRulesPanel, c);
		c.gridy++;
		this.add(recRulesPanel, c);
		c.gridy++;
		this.add(miscRulesPanel, c);
		c.gridy++;
		this.add(kickRulesPanel, c);
		c.gridy++;
		this.add(defRulesPanel, c);
	}
}
