package football.core;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.graphics.GridBagPanel;
import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;
import football.stats.StatType;

public final class CustomScoringHelperView extends JFrame
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final long serialVersionUID = -3565226027504869570L;
	private final int DEFAULT_WIDTH = 1400;
	private final int DEFAULT_HEIGHT = 800;

	private CustomScoringHelperModel model;

	private ScorerPanel panel1, panel2;
	private JComboBox<Mode> modesBox;

	public CustomScoringHelperView(CustomScoringHelperModel model, String title) {
		super(title);
		logger.info("Creating view with name: {}", title);
		this.model = model;

		JPanel content = createContentPanel();

		// frame stuff
		this.setContentPane(content);
		this.pack(); //resize frame based on preferred sizes of subcomponents
		this.setLocationRelativeTo(null); //center window on screen

		//TODO: pass window closing event to controller
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public CustomScoringHelperView(CustomScoringHelperModel model) {
		this(model, "Fantasy Football Custom Scoring Helper");
	}

	/*
	 * Setters
	 */
	public void setMode(Mode mode) {
		panel1.setPlayersPanel(mode);
		//panel2.setPlayersPanel(mode);
	}

	/*
	 * listener methods
	 */
	public void addModeListener(ItemListener listener) {
		modesBox.addItemListener(listener);
		logger.info("registered mode listener");
	}

	public void addRulesListener(DocumentListener listener) {
		List<RuleTextField<? extends StatType>> ruleTextFields = panel1.getRuleTextFields();
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			ruleTextField.getDocument().addDocumentListener(listener);
		}
		logger.info("registered rules listener");
	}

	public void addRecalculateScoreListener(ActionListener listener) {
		JButton scoreButton = panel1.getScoreButton();
		scoreButton.addActionListener(listener);
		logger.info("registered recalculate score listener");
	}

	public void updatePlayerScores(List<Player> players) {
		panel1.updatePlayerScores(players);
	}




	private JPanel createContentPanel() {
		// get defaults from model
		Mode defaultMode = model.getDefaultMode();
		RuleMap defaultRules = model.getDefaultRules();

		// set up content panel
		GridBagPanel content = new GridBagPanel(5);
		GridBagConstraints c = content.getConstraints();

		// mode panel
		JPanel modePanel = createModePanel(defaultMode);

		// scorer panels / add panels to content
		content.add(modePanel, c);
		//TODO: differentiate scorer panels (or drop second panel if it won't fit)
		panel1 = new ScorerPanel(model.getModesToPlayersMap(), defaultMode, defaultRules);
		c.gridy++;
		content.add(panel1, c);
		/*panel2 = new ScorerPanel(model.getModesToPlayersMap(), defaultMode, defaultRules);
		c.gridx++;
		content.add(panel2, c);*/

		// set size
		content.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

		return content;
	}

	private JPanel createModePanel(Mode initMode) {
		GridBagPanel modePanel = new GridBagPanel(2);
		GridBagConstraints c = modePanel.getConstraints();

		modePanel.add(new JLabel("Select a player mode: "), c);
		modesBox = new JComboBox<Mode>(Mode.values());
		modesBox.setSelectedItem(initMode);
		c.gridx++;
		modePanel.add(modesBox, c);

		return modePanel;
	}
}
