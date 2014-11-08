package football.core.listeners;

import javax.swing.SwingUtilities;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import static com.google.common.base.Preconditions.checkNotNull;

import football.core.intface.CustomScoringHelperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
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

		// update model with new Rule
		int modelID = getModelID(ruleTextField);
		logger.info("setting rule {} for model {}", category.toString(), modelID);
		model.setRule(rule, modelID);
	}

	private <T extends Enum<T> & StatType> int getModelID(RuleTextField<T> ruleTextField) {
		ScorerPanel scorerPanel = (ScorerPanel)SwingUtilities.getAncestorOfClass(ScorerPanel.class, ruleTextField);
		checkNotNull(scorerPanel, "RuleTextField does not have ScorerPanel ancestor");
		return Integer.parseInt(scorerPanel.getName());
	}
}
