package football.core.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.SwingUtilities;
import static com.google.common.base.Preconditions.checkNotNull;

import football.core.intface.CustomScoringHelperModel;
import football.core.intface.CustomScoringHelperView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.ScoringResults;
import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
import football.stats.Rule;
import football.stats.RuleMap;
import football.stats.StatType;
import football.util.PlayerUtils;

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
		model.setRules(rules);

		//TODO: feature everything above off depending if RulesListener enabled
		// evaluate all players using current RuleMap
		ScoringResults results = model.run();
		// modify view based on updated model
		view.updatePlayerScores(results.getCustomPlayers());
		// log results with ResultsLogger
		model.logResults(results);
	}

	// parse text from ruleTextField to create a Rule, and add this Rule to RuleMap, rules
	private <T extends Enum<T> & StatType> void addRule(RuleTextField<T> ruleTextField, RuleMap rules) {
		T category = ruleTextField.getCategory();
		String text = ruleTextField.getText();

		Rule<T> rule = PlayerUtils.parseRuleText(text, category);
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
