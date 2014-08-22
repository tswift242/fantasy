package football.core.graphics;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

import football.players.modes.Mode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.Player;
import football.stats.RuleMap;
import football.stats.StatType;

/*
 * Panel displaying a RulesPanel, a PlayersPanel, and a button to rescore the players
 * based on the current rules.
 */

public final class ScorerPanel extends JPanel
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private static final long serialVersionUID = -4593451078660624536L;

	private JPanel playerPanels;
	private RulesPanel rules;
	private JButton scoreButton;

	public ScorerPanel(Map<Mode,List<Player>> playersMap, Mode defaultMode, RuleMap defaultRules) {
		rules = new RulesPanel(defaultRules);
		createPlayerPanels(playersMap);
		scoreButton = new JButton("Recalculate scores");

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		int padding = 5;
		c.insets = new Insets(padding, padding, padding, padding);

		this.add(rules, c);
		c.gridy++;
		this.add(scoreButton, c); //TODO: play with placement
		c.gridy++;
		c.gridheight = 2; //TODO: this isn't working
		this.add(playerPanels, c);

		// show players corresponding to default mode
		setPlayersPanel(defaultMode);
	}

	public List<RuleTextField<? extends StatType>> getRuleTextFields() {
		return rules.getRuleTextFields();
	}

	public JButton getScoreButton() {
		return scoreButton;
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

	// create a PlayersPanel for each Mode, and add it to a aggregate panel which displays
	// one panel at a time using the CardLayout
	private void createPlayerPanels(Map<Mode,List<Player>> playersMap) {
		playerPanels = new JPanel(new CardLayout());
		for(Mode mode : Mode.values()) {
			//TODO: avoiding Mode.ALL for now because of issues
			if(mode != Mode.ALL) {
				PlayersPanel players = new PlayersPanel(playersMap.get(mode));
				players.setName(mode.toString());
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
