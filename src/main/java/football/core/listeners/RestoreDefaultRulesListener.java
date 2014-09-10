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

import football.config.CustomScoringHelperProperties;
import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
import football.stats.StatType;

public class RestoreDefaultRulesListener implements ActionListener
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	public RestoreDefaultRulesListener() { }

	@Override
	public void actionPerformed(ActionEvent e) {
		logger.info("restoring default rules");
		JButton scoreButton = (JButton)e.getSource();
		// get ScorerPanel containing the JButton source
		ScorerPanel scorerPanel = (ScorerPanel)SwingUtilities.getAncestorOfClass(ScorerPanel.class, scoreButton);
		checkNotNull(scorerPanel, "JButton does not have ScorerPanel ancestor");
		// get TextFields containing rule info on ScorerPanel
		List<RuleTextField<? extends StatType>> ruleTextFields = scorerPanel.getRuleTextFields();
		// set text of each rule text field back to the default
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			String defaultText = CustomScoringHelperProperties.getDefaultRules().getValueText(ruleTextField.getCategory());
			ruleTextField.setText(defaultText);
		}
	}
}
