package football.core.graphics;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

import football.stats.StatType;
import football.stats.categories.*;

/*
 * Panel displaying text boxes for modifying scoring rules of all stat types
 */

public final class RulesPanel extends JPanel
{
	private static final long serialVersionUID = 4818675092111795395L;
	private List<RuleTextField<? extends StatType>> ruleTextFields;

	public RulesPanel() {
		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;

		// add one of each stat type rules panel
		List<StatTypeRulesPanel<? extends StatType>> rulePanels = new ArrayList<StatTypeRulesPanel<? extends StatType>>();
		rulePanels.add(new StatTypeRulesPanel<Pass>(Pass.class));
		rulePanels.add(new StatTypeRulesPanel<Rush>(Rush.class));
		rulePanels.add(new StatTypeRulesPanel<Rec>(Rec.class));
		rulePanels.add(new StatTypeRulesPanel<Misc>(Misc.class));
		rulePanels.add(new StatTypeRulesPanel<Kick>(Kick.class));
		rulePanels.add(new StatTypeRulesPanel<Def>(Def.class));

		ruleTextFields = new ArrayList<RuleTextField<? extends StatType>>();

		for(StatTypeRulesPanel<? extends StatType> rulePanel : rulePanels) {
			this.add(rulePanel, c);
			c.gridy++;
			ruleTextFields.addAll(rulePanel.getRuleTextFields());
		}
	}

	public List<RuleTextField<? extends StatType>> getRuleTextFields() {
		return ruleTextFields;
	}
}
