package football.core.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import static com.google.common.base.Preconditions.checkNotNull;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.CustomScoringHelperModel;
import football.core.CustomScoringHelperView;
import football.core.ScoringResults;
import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.StatType;

public class RecalculateScoreListener implements ActionListener
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;
	private CustomScoringHelperView view;

	public RecalculateScoreListener(CustomScoringHelperModel model, CustomScoringHelperView view) {
		this.model = model;
		this.view = view;
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
		// create RuleMap from ruleTextFields
		RuleMap rules = new RuleMap();
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			addRule(ruleTextField, rules);
		}
		// update model with new RuleMap
		model.setRuleMap(rules);

		//TODO: feature everything above off depending if RulesListener enabled
		// evaluate all players using current RuleMap
		ScoringResults results = model.run();
		// modify view based on updated model
		view.updatePlayerScores(results.getCustomPlayers());
		// log results with ResultsLogger
		model.logResults(results);
	}

	//TODO: put in Util file (used in RulesListener, PlayerUtil) and remove apache imports from this file
	// parses rule text for value and unit and creates a rule. Returns null if rule is invalid.
	private <T extends Enum<T> & StatType> Rule<T> parseRuleText(String text, T category) {
		// handle invalid rule values first
		if(StringUtils.isBlank(text)) {
			logger.warn("The rule for {} is blank. Using the value of 0.0 instead.", category.toString());
			return null;
		}

		// split into value and unit
		String[] ruleComponents = text.split("/");
		Double value;
		try {
			value = Double.valueOf(ruleComponents[0]);
		} catch(NumberFormatException e) {
			logger.error("The rule for {} is not a valid number. Using the value of 0.0 instead.", category.toString());
			return null;
		}
		int unit = 1;
		// get unit if provided
		if(ruleComponents.length > 1) {
			try {
				unit = Integer.parseInt(ruleComponents[1]);
			} catch(NumberFormatException e) {
				logger.error("The rule for {} is not a valid number. Using the value of 0.0 instead.", category.toString());
				return null;
			}
		}
		return new Rule<T>(category, value, unit);
	}

	// parse text from ruleTextField to create a Rule, and add this Rule to RuleMap, rules
	private <T extends Enum<T> & StatType> void addRule(RuleTextField<T> ruleTextField, RuleMap rules) {
		T category = ruleTextField.getCategory();
		String text = ruleTextField.getText();

		Rule<T> rule = parseRuleText(text, category);
		// if rule is invalid, remove it from the RuleMap to prevent old value from being used
		// equivalent to setting value to be 0
		if(rule == null) {
			rules.remove(category);
		} else { // valid rule -> add new rule to RuleMap
			logger.debug("new rule: {}", rule.toString());
			rules.put(category, rule);
		}
	}
}
