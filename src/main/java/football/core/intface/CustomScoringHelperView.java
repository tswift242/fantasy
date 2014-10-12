package football.core.intface;

import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import java.util.List;
import javax.swing.event.DocumentListener;

import football.core.graphics.ScorerPanel;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

public interface CustomScoringHelperView
{
	public List<ScorerPanel> getScorerPanels();

	public void setMode(Mode mode);

	public void updatePlayerScores(List<List<Player>> players);

	public void setRuleTextFields(RuleMap rules);

	public void addModeListener(ItemListener listener);

	public void addLeagueSiteListener(ItemListener listener);

	public void addRulesListener(DocumentListener listener);

	public void addRecalculateScoreListener(ActionListener listener);

	public void addRestoreDefaultRulesListener(ActionListener listener);

	public void addWindowCloseListener(WindowListener listener);
}
