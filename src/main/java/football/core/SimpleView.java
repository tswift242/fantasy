package football.core;

import java.awt.BorderLayout;
import java.awt.GridBagConstraints;
import java.awt.GridLayout;
import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.WindowListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
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
	private List<ScorerPanel> scorerPanels;
	private JComboBox<Mode> modesBox;
	private JButton rescoreButton;

	public SimpleView(CustomScoringHelperModel model, String title) {
		super(title);
		logger.info("Creating view with name: {}", title);
		this.model = model;

		boolean createMultipleScorerPanels = isCompositeModel(model);
		JPanel content = createContentPanel(createMultipleScorerPanels);

		// frame stuff
		this.setContentPane(content);
		this.pack(); //resize frame based on preferred sizes of subcomponents
		this.setLocationRelativeTo(null); //center window on screen

		//TODO: pass window closing event to controller
		this.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
	}

	public SimpleView(CustomScoringHelperModel model) {
		this(model, "Fantasy Football Custom Scoring Helper");
	}

	/*
	 * Getters
	 */
	@Override
	public List<ScorerPanel> getScorerPanels() {
		return new ArrayList<ScorerPanel>(scorerPanels);
	}

	/*
	 * Setters
	 */
	@Override
	public void setMode(Mode mode) {
		for(ScorerPanel scorerPanel : scorerPanels) {
			scorerPanel.setPlayersPanel(mode);
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
		for(ScorerPanel scorerPanel : scorerPanels) {
			scorerPanel.addRulesListener(listener);
		}
		logger.info("registered rules listener");
	}

	@Override
	public void addRecalculateScoreListener(ActionListener listener) {
		rescoreButton.addActionListener(listener);
		logger.info("registered recalculate score listener");
	}

	@Override
	public void addRestoreDefaultRulesListener(ActionListener listener) {
		for(ScorerPanel scorerPanel : scorerPanels) {
			scorerPanel.addRestoreDefaultRulesListener(listener);
		}
		logger.info("registered restore default rules listener");
	}

	//TODO: move this to CSHView if we make that an abstract class
	@Override
	public void addWindowCloseListener(WindowListener listener) {
		addWindowListener(listener);
		logger.info("registered window close listener");
	}

	@Override
	public void updatePlayerScores(List<List<Player>> players) {
		int index = 0;
		for(ScorerPanel scorerPanel : scorerPanels) {
			scorerPanel.updatePlayerScores(players.get(index++));
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

		// set up content panel
		JPanel content = new JPanel();
		content.setLayout(new BorderLayout());

		// mode panel
		JPanel modePanel = createModePanel(defaultMode);
		content.add(modePanel, BorderLayout.NORTH);

		// scorer panels
		scorerPanels = new ArrayList<ScorerPanel>();
		//TODO: pass modelID into ScorerPanel constructor
		int modelID = 1;
		ScorerPanel scorerPanel;
		scorerPanel = new ScorerPanel(model.getModesToPlayersMap(modelID));
		scorerPanel.setName(String.valueOf(modelID)); // tag ScorerPanel with an ID
		scorerPanels.add(scorerPanel);

		// create 2nd ScorerPanel, and put 2 panels side-by-side before adding to content panel
		if(createMultipleScorerPanels) {
			scorerPanel = new ScorerPanel(model.getModesToPlayersMap(modelID));
			scorerPanel.setName(String.valueOf(++modelID)); // tag ScorerPanel with an ID
			scorerPanels.add(scorerPanel);

			JPanel scorerPanelsPanel = createScorerPanelsPanel(scorerPanels);

			int unitIncrement = 10;
			JScrollPane scrollPane = new JScrollPane(scorerPanelsPanel);
			scrollPane.getHorizontalScrollBar().setUnitIncrement(unitIncrement);
			scrollPane.getVerticalScrollBar().setUnitIncrement(unitIncrement);
			content.add(scrollPane, BorderLayout.CENTER);
		} else { // add just the 1 ScorerPanel to the content panel
			content.add(scorerPanels.get(0), BorderLayout.CENTER);
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

		rescoreButton = new JButton("Recalculate scores");
		c.gridx--;
		c.gridy++;
		c.gridwidth = 2;
		c.insets = new Insets(10, 2, 5, 2);
		modePanel.add(rescoreButton, c);

		return modePanel;
	}

	// line up a list of ScorerPanel's side-by-side in a grid
	private JPanel createScorerPanelsPanel(List<ScorerPanel> scorer_panels) {
		int hgap = 50;

		JPanel scorerPanelsPanel = new JPanel();
		scorerPanelsPanel.setLayout(new GridLayout(1, scorer_panels.size(), hgap, 0));
		for(ScorerPanel scorerPanel : scorer_panels) {
			scorerPanelsPanel.add(scorerPanel);
		}

		return scorerPanelsPanel;
	}
}
