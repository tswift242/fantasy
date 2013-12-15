package football.core.graphics;

import java.awt.GridLayout;
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

	//TODO: pass in model, or this map??
	public ScorerPanel(CustomScoringHelperModel model) {
		//this.modesToPlayersMap = modesToPlayersMap;
		this.model = model;
		rules = new RulesPanel();
		setPlayersPanel(DEFAULT_MODE);
		//TODO: change layout from grid to gridbag to enable players panel to be larger 
		//than rules panel
		this.setLayout(new GridLayout(2, 1));
		this.add(rules);
		this.add(players);
		//TODO: need "recalculate scores" button
	}

	public void setPlayersPanel(Modes mode) {
		//TODO -- figure out info passing
		players = new PlayersPanel(model.getPlayersList(mode));
	}
}
