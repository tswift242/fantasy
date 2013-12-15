package football.core.graphics;

import java.awt.GridLayout;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

import football.players.Player;

/*
 * Panel displaying a list of players of a given position, their relevant stats, 
 * and their scores.
 */

public final class PlayersPanel extends JPanel
{
	private static final long serialVersionUID = 7401650925506787631L;
	private static final String WHITESPACE_REGEX = "\\s+";

	public PlayersPanel(List<Player> players) {
		int numPlayers = players.size();
		// stat categories for this group of players
		String[] categories = players.get(0).categoriesToString().split(WHITESPACE_REGEX);
		int numStats = categories.length; // numStats == numCategories
		//TODO: change layout from grid to gridbag to enable player name to be larger than stats?
		this.setLayout(new GridLayout(numPlayers+1, numStats+2));
		// add top row for stat categories
		this.add(new JLabel("Players", JLabel.CENTER));
		for(String category : categories) {
			this.add(new JLabel(category, JLabel.CENTER));
		}
		this.add(new JLabel("Score", JLabel.CENTER));
		// add new row of labels for each player, listing that player's name, stats, and score
		for(Player player : players) {
			this.add(new JLabel(player.getName(), JLabel.CENTER));
			String[] stats = player.statsToString().split(WHITESPACE_REGEX);
			for(String stat : stats) {
				this.add(new JLabel(stat, JLabel.CENTER));
			}
			this.add(new JLabel(Double.toString(player.getScore()), JLabel.CENTER));
		}
	}
}
