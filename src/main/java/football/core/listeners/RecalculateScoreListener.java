package football.core.listeners;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import football.core.ScoringResults;
import football.core.intface.CustomScoringHelperModel;
import football.core.intface.CustomScoringHelperView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
		List<ScorerPanel> scorerPanels = view.getScorerPanels();
		for(ScorerPanel scorerPanel : scorerPanels) {
			updateRulesFromScorerPanel(scorerPanel);
		}

		//TODO: feature everything above off depending if RulesListener enabled
		// evaluate all players using current RuleMap
		ScoringResults results = model.run();
		// modify view based on updated model
		view.updatePlayerScores(results.getPlayers());
		// log results with ResultsLogger
		model.logResults(results);
	}

	// get the latest rules from the text fields of this scorer panel, and update the
	// model with them
	private void updateRulesFromScorerPanel(ScorerPanel scorerPanel) {
		// get TextFields containing rule info on ScorerPanel
		List<RuleTextField<? extends StatType>> ruleTextFields = scorerPanel.getRuleTextFields();
		// create RuleMap from ruleTextFields
		RuleMap rules = new RuleMap();
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			addRule(ruleTextField, rules);
		}
		// update model with new RuleMap
		int modelID = Integer.parseInt(scorerPanel.getName());
		logger.info("setting rules for model {}", modelID);
		model.setRules(rules, modelID);
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
