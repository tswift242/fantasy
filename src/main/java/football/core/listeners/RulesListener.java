package football.core.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.Document;

import org.apache.commons.lang3.StringUtils;
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
		//TODO: change text to "0.0" if its empty
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

	//TODO: put in Util file (used in RulesListener, PlayerUtil) and remove apache imports from this file
	// parses rule text for value and unit and creates a rule. Returns null if rule is invalid.
	private <T extends Enum<T> & StatType> Rule<T> parseRuleText(String text, T category) {
		// handle invalid rule values first
		if(StringUtils.isBlank(text)) {
			logger.warn("The rule for {} is blank. Using the value of 0.0 instead.", category.toString());
			return null;
		}

		// valid rule text at this point
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
}
