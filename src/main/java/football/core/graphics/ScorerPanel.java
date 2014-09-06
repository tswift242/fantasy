package football.core.graphics;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.event.ActionListener;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;

import football.players.modes.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.config.CustomScoringHelperProperties;
import football.players.Player;
import football.stats.StatType;

/*
 * Panel displaying a RulesPanel, a PlayersPanel, and a button to rescore the players
 * based on the current rules.
 */

public final class ScorerPanel extends GridBagPanel
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private static final long serialVersionUID = -4593451078660624536L;

	private JPanel playerPanels;
	private RulesPanel rules;
	private JButton scoreButton;

	public ScorerPanel(Map<Mode,List<Player>> playersMap) {
		super(5);

		rules = new RulesPanel();
		createPlayerPanels(playersMap);
		scoreButton = new JButton("Recalculate scores");

		this.add(rules, c);
		c.gridy++;
		this.add(scoreButton, c); //TODO: play with placement
		c.gridy++;
		c.gridheight = 2; //TODO: this isn't working
		this.add(playerPanels, c);

		// show players corresponding to default mode
		setPlayersPanel(CustomScoringHelperProperties.getDefaultMode());
	}

	public List<RuleTextField<? extends StatType>> getRuleTextFields() {
		return rules.getRuleTextFields();
	}

	public void setPlayersPanel(Mode mode) {
		CardLayout cardLayout = (CardLayout)(playerPanels.getLayout());
		cardLayout.show(playerPanels, mode.toString());
		logger.info("Set players panel to mode {}", mode.toString());
	}

	// update player scores within current player panel
	public void updatePlayerScores(List<Player> players) {
		PlayersPanel panel = getCurrentPanel();
		logger.info("updating scores for player panel: {}", panel.getName());
		panel.updatePlayerScores(players);
	}

	public void addRulesListener(DocumentListener listener) {
		List<RuleTextField<? extends StatType>> ruleTextFields = getRuleTextFields();
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			ruleTextField.getDocument().addDocumentListener(listener);
		}
	}

	public void addRecalculateScoreListener(ActionListener listener) {
		scoreButton.addActionListener(listener);
	}



	// create a PlayersPanel for each Mode, and add it to a aggregate panel which displays
	// one panel at a time using the CardLayout
	private void createPlayerPanels(Map<Mode,List<Player>> playersMap) {
		playerPanels = new JPanel(new CardLayout());
		for(Mode mode : Mode.values()) {
			//TODO: avoiding Mode.ALL for now because of issues
			if(mode != Mode.ALL) {
				PlayersPanel players = new PlayersPanel(playersMap.get(mode));
				players.setName(mode.toString());
				// TODO: get consistent PlayersPanel sizing by setting 
				// preferredScrollableViewportSize for each panel to be preferred size
				// of largest PlayersPanel (QB)
				playerPanels.add(players, mode.toString());
			}
		}
	}

	private PlayersPanel getCurrentPanel() {
		PlayersPanel panel = null;

		for(Component component : playerPanels.getComponents()) {
			if(component.isVisible()) {
				panel = (PlayersPanel)component;
				break;
			}
		}

		return panel;
	}
}
