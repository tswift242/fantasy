package football.core.listeners;

import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import football.core.intface.CustomScoringHelperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.graphics.RuleTextField;
import football.stats.Rule;
import football.stats.StatType;
import football.util.PlayerUtils;

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
		Rule<T> rule = PlayerUtils.parseRuleText(text, category);
		logger.debug("new rule: {}", rule.toString());
		model.setRule(category, rule);
	}
}
