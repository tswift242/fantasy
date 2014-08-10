package football.core.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.CustomScoringHelperModel;
import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
import football.stats.RuleMap;
import football.stats.StatType;

public class RecalculateScoreListener implements ActionListener
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;

	public RecalculateScoreListener(CustomScoringHelperModel model) {
		this.model = model;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("recalculating player scores");
		JButton scoreButton = (JButton)e.getSource();
		// get ScorerPanel containing the JButton source
		ScorerPanel scorerPanel = (ScorerPanel)SwingUtilities.getAncestorOfClass(ScorerPanel.class, scoreButton);
		checkNotNull(scorerPanel, "JButton does not have ScorerPanel ancestor");
		// get TextFields containing rule info on ScorerPanel
		List<RuleTextField<? extends StatType>> ruleTextFields = scorerPanel.getRuleTextFields();
		// create and set RuleMap from ruleTextFields
		RuleMap rules = new RuleMap();
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			StatType category = ruleTextField.getCategory();
			String text = ruleTextField.getText();
			//TODO
			//parseRuleText(text, category, rules);
		}
		model.setRuleMap(rules);
		//TODO: evaluate all players using current RuleMap
		//model.run();
	}

	//TODO: put in Util file (used in PlayerUtil)
	//TODO: take in RuleMap and use overloaded put() with int/Double to avoid compiler error
	private void parseRuleText(String text, StatType category, RuleMap rules) {
		// split into value and unit
		String[] ruleComponents = text.split("/");
		Double value = Double.valueOf(ruleComponents[0]);
		int unit = 1;
		// get unit if provided
		if(ruleComponents.length > 1) {
			unit = Integer.parseInt(ruleComponents[1]);
		}
		//TODO: workflow from here not complete
		//rules.put(category, value, unit);

		//TODO: compiler error because Rule constructor needs Enum/StatType, not Object
		//return new Rule(category, value, unit);
	}
}
