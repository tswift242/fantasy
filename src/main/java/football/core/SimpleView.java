package football.core;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.config.CustomScoringHelperProperties;
import football.core.graphics.GridBagPanel;
import football.core.graphics.ScorerPanel;
import football.core.intface.CustomScoringHelperModel;
import football.core.intface.CustomScoringHelperView;
import football.players.Player;
import football.players.modes.Mode;
import football.stats.RuleMap;

public final class SimpleView extends JFrame implements CustomScoringHelperView
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final long serialVersionUID = -3565226027504869570L;

	private CustomScoringHelperModel model;
	private ScorerPanel panel1, panel2;
	private JComboBox<Mode> modesBox;
	private boolean createMultipleScorerPanels;

	public SimpleView(CustomScoringHelperModel model, String title) {
		super(title);
		logger.info("Creating view with name: {}", title);
		this.model = model;

		createMultipleScorerPanels = isCompositeModel(model);
		JPanel content = createContentPanel(createMultipleScorerPanels);

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
		if(panel2 != null) {
			panel2.setPlayersPanel(mode);
		}
	}

	/*
	 * listener methods
	 */
	@Override
	public void addModeListener(ItemListener listener) {
		modesBox.addItemListener(listener);
		logger.info("registered mode listener");
	}

	@Override
	public void addRulesListener(DocumentListener listener) {
		panel1.addRulesListener(listener);
		if(createMultipleScorerPanels) {
			panel2.addRulesListener(listener);
		}
		logger.info("registered rules listener");
	}

	@Override
	public void addRecalculateScoreListener(ActionListener listener) {
		panel1.addRecalculateScoreListener(listener);
		if(createMultipleScorerPanels) {
			panel2.addRecalculateScoreListener(listener);
		}
		logger.info("registered recalculate score listener");
	}

	@Override
	public void updatePlayerScores(List<List<Player>> players) {
		panel1.updatePlayerScores(players.get(0));
		if(createMultipleScorerPanels) {
			panel2.updatePlayerScores(players.get(1));
		}
	}




	// determine whether or not to create multiple ScorerPanel's
	// based on whether the model passed in is composite
	private boolean isCompositeModel(CustomScoringHelperModel model) {
		boolean isComposite;

		int numModels = model.getNumberOfModels();
		if(numModels == 1) {
			isComposite= false;
		} else if(numModels == 2) {
			isComposite = true;
		} else {
			throw new IllegalArgumentException("number of models returned by model must be 1 or 2");
		}

		return isComposite;
	}

	private JPanel createContentPanel(boolean createMultipleScorerPanels) {
		// get defaults
		Mode defaultMode = CustomScoringHelperProperties.getDefaultMode();
		RuleMap defaultRules = CustomScoringHelperProperties.getDefaultRules();
		int defaultWidth = CustomScoringHelperProperties.getDefaultWidth();
		int defaultHeight = CustomScoringHelperProperties.getDefaultHeight();

		// set up content panel
		GridBagPanel content = new GridBagPanel(5);
		GridBagConstraints c = content.getConstraints();

		// mode panel
		JPanel modePanel = createModePanel(defaultMode);

		// scorer panels / add panels to content
		content.add(modePanel, c);
		//TODO: pass modelID into ScorerPanel constructor
		//TODO: do not pass defaultMode and defaultRules into SP constructor; access statically
		// instead
		int modelID = 1;
		panel1 = new ScorerPanel(model.getModesToPlayersMap(modelID));
		panel1.setName(String.valueOf(modelID)); // tag ScorerPanel with an ID
		c.gridy++;

		// create 2nd ScorerPanel, and put 2 panels side-by-side before adding to content panel
		if(createMultipleScorerPanels) {
			modelID = 2;
			panel2 = new ScorerPanel(model.getModesToPlayersMap(modelID));
			panel2.setName(String.valueOf(modelID)); // tag ScorerPanel with an ID

			JPanel scorerPanels = new JPanel();
			int hgap = 50;
			scorerPanels.setLayout(new GridLayout(1, 2, hgap, 0));
			scorerPanels.add(panel1);
			scorerPanels.add(panel2);
			JScrollPane scrollPane = new JScrollPane(scorerPanels);
			// need to setPreferredSize of scroll pane, because it looks bad visually otherwise
			scrollPane.setPreferredSize(new Dimension(defaultWidth, (int)(0.7*defaultHeight)));
			content.add(scrollPane, c);
		} else { // add just the 1 ScorerPanel to the content panel
			content.add(panel1, c);

			// set size
			content.setPreferredSize(new Dimension(defaultWidth, defaultHeight));
		}

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
