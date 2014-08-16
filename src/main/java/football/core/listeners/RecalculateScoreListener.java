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
import football.stats.Rule;
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
			addRule(ruleTextField, rules);
		}
		model.setRuleMap(rules);
		//evaluate all players using current RuleMap
		model.run();
	}

	//TODO: put in Util file (used in RulesListener, PlayerUtil)
	private <T extends Enum<T> & StatType> Rule<T> parseRuleText(String text, T category) {
		// split into value and unit
		String[] ruleComponents = text.split("/");
		Double value = Double.valueOf(ruleComponents[0]);
		int unit = 1;
		// get unit if provided
		if(ruleComponents.length > 1) {
			unit = Integer.parseInt(ruleComponents[1]);
		}
		return new Rule<T>(category, value, unit);
	}

	// parse text from ruleTextField to create a Rule, and add this Rule to RuleMap, rules
	private <T extends Enum<T> & StatType> void addRule(RuleTextField<T> ruleTextField, RuleMap rules) {
		T category = ruleTextField.getCategory();
		String text = ruleTextField.getText();
		Rule<T> rule = parseRuleText(text, category);
		logger.debug("new rule: {}", rule.toString());
		rules.put(category, rule);
	}
}
