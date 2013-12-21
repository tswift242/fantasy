package football.core.graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import javax.swing.JButton;
import javax.swing.JPanel;

import football.core.CustomScoringHelperModel;
import football.players.modes.Modes;

/*
 * Panel displaying a set of scoring rule combo boxes and a PlayersPanel
 */

public final class ScorerPanel extends JPanel
{
	private static final long serialVersionUID = -4593451078660624536L;
	private static final Modes DEFAULT_MODE = Modes.QB;

	//private Map<Modes,List<Player>> modesToPlayersMap;
	private CustomScoringHelperModel model; //TODO: think about this
	//TODO: should have enummap of Modes to PlayerPanel's
	private RulesPanel rules;
	private PlayersPanel players;
	private JButton scoreButton;

	//TODO: pass in model, or map??
	public ScorerPanel(CustomScoringHelperModel model) {
		//this.modesToPlayersMap = modesToPlayersMap;
		this.model = model;
		rules = new RulesPanel();
		setPlayersPanel(DEFAULT_MODE);
		scoreButton = new JButton("Recalculate scores");

		this.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;

		this.add(rules, c);
		c.gridy++;
		this.add(scoreButton, c); //TODO: play with placement
		c.gridy++;
		this.add(players, c);
		c.gridy++;
	}

	public void setPlayersPanel(Modes mode) {
		//TODO -- figure out info passing
		players = new PlayersPanel(model.getPlayersList(mode));
	}
}
