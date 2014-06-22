package football.core.graphics;

import java.awt.CardLayout;
import java.awt.Component;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.CustomScoringHelperModel;
import football.players.modes.Modes;

/*
 * Panel displaying a set of scoring rule combo boxes and a PlayersPanel
 */

public final class ScorerPanel extends JPanel
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());
	private static final long serialVersionUID = -4593451078660624536L;

	//private Map<Modes,List<Player>> modesToPlayersMap;
	private JPanel playerPanels;
	private RulesPanel rules;
	private JButton scoreButton;

	//TODO: pass in model, or map??
	public ScorerPanel(CustomScoringHelperModel model, Modes initMode) {
		//this.modesToPlayersMap = modesToPlayersMap;
		rules = new RulesPanel();
		createPlayerPanels(model);
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

	public void setPlayersPanel(Modes mode) {
		CardLayout cardLayout = (CardLayout)(playerPanels.getLayout());
		cardLayout.show(playerPanels, mode.toString());
		logger.info("Set players panel to mode {}", mode.toString());
	}

	// create a PlayersPanel for each Mode, and add it to a aggregate panel which displays
	// one panel at a time using the CardLayout
	private void createPlayerPanels(CustomScoringHelperModel model) {
		playerPanels = new JPanel(new CardLayout());
		for(Modes mode : Modes.values()) {
			if(mode != Modes.ALL) {
				PlayersPanel players = new PlayersPanel(model.getPlayersList(mode));
				playerPanels.add(players, mode.toString());
			}
		}
	}
}
