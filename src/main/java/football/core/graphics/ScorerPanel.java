package football.core.graphics;

import java.awt.CardLayout;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import java.util.Map;
import javax.swing.JButton;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.Player;
import football.players.modes.Modes;
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

	public ScorerPanel(Map<Modes,List<Player>> playersMap, Modes initMode) {
		rules = new RulesPanel();
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
	}

	public List<RuleTextField<? extends StatType>> getRuleTextFields() {
		return rules.getRuleTextFields();
	}

	public JButton getScoreButton() {
		return scoreButton;
	}

	public void setPlayersPanel(Modes mode) {
		CardLayout cardLayout = (CardLayout)(playerPanels.getLayout());
		cardLayout.show(playerPanels, mode.toString());
		logger.info("Set players panel to mode {}", mode.toString());
	}

	// create a PlayersPanel for each Mode, and add it to a aggregate panel which displays
	// one panel at a time using the CardLayout
	private void createPlayerPanels(Map<Modes,List<Player>> playersMap) {
		playerPanels = new JPanel(new CardLayout());
		for(Modes mode : Modes.values()) {
			//TODO: avoiding Modes.ALL for now because of issues
			if(mode != Modes.ALL) {
				PlayersPanel players = new PlayersPanel(playersMap.get(mode));
				playerPanels.add(players, mode.toString());
			}
		}
	}
}
