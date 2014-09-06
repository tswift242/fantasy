package football.core.graphics;

import java.util.ArrayList;
import java.util.List;

import football.stats.StatType;
import football.stats.categories.*;

/*
 * Panel displaying text boxes for modifying scoring rules of all stat types
 */

public final class RulesPanel extends GridBagPanel
{
	private static final long serialVersionUID = 4818675092111795395L;
	private List<RuleTextField<? extends StatType>> ruleTextFields;

	public RulesPanel() {
		super();

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
