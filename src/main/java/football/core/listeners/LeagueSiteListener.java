package football.core.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import football.core.intface.CustomScoringHelperModel;
import football.core.intface.CustomScoringHelperView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.config.CustomScoringHelperProperties;
import football.stats.RuleMap;

public class LeagueSiteListener implements ItemListener
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;
	private CustomScoringHelperView view;

	public LeagueSiteListener(CustomScoringHelperModel model, CustomScoringHelperView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			String newSite = (String)e.getItem();
			logger.info("New league site selected: " + newSite);

			// update default rules in properties
			CustomScoringHelperProperties.setDefaultRules(newSite);

			// get updated default rules
			RuleMap defaultRules = CustomScoringHelperProperties.getDefaultRules();

			// set rules back to defaults in model (for ALL models)
			model.setRules(defaultRules);

			// set rules back to defaults in view (for ALL views/panels)
			view.setRuleTextFields(defaultRules);
		}
	}
}
