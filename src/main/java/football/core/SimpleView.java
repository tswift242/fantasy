package football.core;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentListener;

import football.core.intface.CustomScoringHelperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.graphics.GridBagPanel;
import football.core.graphics.RuleTextField;
import football.core.graphics.ScorerPanel;
import football.core.intface.CustomScoringHelperView;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;
import football.stats.StatType;

public final class SimpleView extends JFrame implements CustomScoringHelperView
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final long serialVersionUID = -3565226027504869570L;
	private final int DEFAULT_WIDTH = 1400;
	private final int DEFAULT_HEIGHT = 800;

	private CustomScoringHelperModel model;

	private ScorerPanel panel1, panel2;
	private JComboBox<Mode> modesBox;

	public SimpleView(CustomScoringHelperModel model, String title) {
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

	public SimpleView(CustomScoringHelperModel model) {
		this(model, "Fantasy Football Custom Scoring Helper");
	}

	/*
	 * Setters
	 */
	@Override
	public void setMode(Mode mode) {
		panel1.setPlayersPanel(mode);
		//panel2.setPlayersPanel(mode);
	}

	/*
	 * listener methods
	 */
	@Override
	public void addModeListener(ItemListener listener) {
		modesBox.addItemListener(listener);
		logger.info("registered mode listener");
	}

	//TODO: push logic for this to ScorerPanel, and call for both ScorerPanel's
	@Override
	public void addRulesListener(DocumentListener listener) {
		List<RuleTextField<? extends StatType>> ruleTextFields = panel1.getRuleTextFields();
		for(RuleTextField<? extends StatType> ruleTextField : ruleTextFields) {
			ruleTextField.getDocument().addDocumentListener(listener);
		}
		logger.info("registered rules listener");
	}

	//TODO: push logic for this to ScorerPanel, and call for both ScorerPanel's
	@Override
	public void addRecalculateScoreListener(ActionListener listener) {
		JButton scoreButton = panel1.getScoreButton();
		scoreButton.addActionListener(listener);
		logger.info("registered recalculate score listener");
	}

	@Override
	public void updatePlayerScores(List<Player> players) {
		panel1.updatePlayerScores(players);
	}




	private JPanel createContentPanel() {
		// get defaults from model
		Mode defaultMode = SimpleModel.DEFAULT_MODE;
		RuleMap defaultRules = SimpleModel.DEFAULT_RULES;

		// set up content panel
		GridBagPanel content = new GridBagPanel(5);
		GridBagConstraints c = content.getConstraints();

		// mode panel
		JPanel modePanel = createModePanel(defaultMode);

		// scorer panels / add panels to content
		content.add(modePanel, c);
		//TODO: pass modelID into ScorerPanel constructor
		int modelID = 1;
		panel1 = new ScorerPanel(model.getModesToPlayersMap(modelID), defaultMode, defaultRules);
		panel1.setName(String.valueOf(modelID)); // tag ScorerPanel with an ID
		c.gridy++;
		content.add(panel1, c);
		//TODO: incorporte later
		/*int modelID = 2;
		panel2 = new ScorerPanel(model.getModesToPlayersMap(modelID), defaultMode, defaultRules);
		panel2.setName(String.valueOf(modelID)); // tag ScorerPanel with an ID
		//c.gridx++;
		//content.add(panel2, c);*/

		//TODO: incorporte later
		/*JPanel scorerPanels = new JPanel();
		int hgap = 50;
		scorerPanels.setLayout(new GridLayout(1, 2, hgap, 0));
		scorerPanels.add(panel1);
		scorerPanels.add(panel2);
		JScrollPane scrollPane = new JScrollPane(scorerPanels);
		scrollPane.setPreferredSize(new Dimension(DEFAULT_WIDTH, (int)(0.7*DEFAULT_HEIGHT)));
		c.gridy++;
		content.add(scrollPane, c);*/

		//TODO: remove this when start using scroll pane above
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
