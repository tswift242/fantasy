package football.core.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.CustomScoringHelperModel;
import football.core.graphics.RuleTextField;
import football.stats.Rule;
import football.stats.StatType;

public class RulesListener implements DocumentListener
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;

	public RulesListener(CustomScoringHelperModel model) {
		this.model = model;
	}

	@Override
	public void insertUpdate(DocumentEvent e) {
		updateRule(e);
	}

	@Override
	public void removeUpdate(DocumentEvent e) {
		updateRule(e);
	}

	@Override
	public void changedUpdate(DocumentEvent e) {
		logger.info("change");
	}

	@SuppressWarnings("unchecked")
	private <T extends Enum<T> & StatType> void updateRule(DocumentEvent e) {
		//TODO: use some timing strategy to determine when to flush events to model??
		RuleTextField<T> ruleTextField = (RuleTextField<T>)e.getDocument().getProperty(RuleTextField.TEXT_FIELD_PROPERTY);
		T category = ruleTextField.getCategory();
		String text = ruleTextField.getText();
		Rule<T> rule = parseRuleText(text, category);
		logger.debug("new rule: {}", rule.toString());
		model.setRule(category, rule);
	}

	//TODO: put in Util file (used in RecalculateScoreListener, PlayerUtil)
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
}
