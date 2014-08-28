package football.core.intface;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.event.DocumentListener;

import football.players.Player;
import football.players.modes.Mode;

public interface CustomScoringHelperView
{
	public void setMode(Mode mode);

	public void addModeListener(ItemListener listener);

	public void addRulesListener(DocumentListener listener);

	public void addRecalculateScoreListener(ActionListener listener);

	public void updatePlayerScores(List<Player> players);
}
