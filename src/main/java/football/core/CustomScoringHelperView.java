package football.core;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemListener;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.graphics.ScorerPanel;
import football.core.graphics.RulesPanel;
import football.core.graphics.PlayersPanel;
import football.players.modes.Modes;
import football.core.graphics.StatTypeRulesPanel;
//import football.stats.categories.*;

public final class CustomScoringHelperView extends JFrame
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private static final long serialVersionUID = -3565226027504869570L;
	private final int DEFAULT_WIDTH = 1400;
	private final int DEFAULT_HEIGHT = 800;

	private CustomScoringHelperModel model;

	private ScorerPanel panel1, panel2;
	private JComboBox<Modes> modesBox;

	//TODO: break this up into helper methods
	public CustomScoringHelperView(CustomScoringHelperModel model, String title) {
		super(title);
		logger.info("Creating view with name: {}", title);
		this.model = model;
		Modes initMode = model.getDefaultMode();

		// set up content panel
		JPanel content = new JPanel();
		content.setLayout(new GridBagLayout());

		// mode panel
		JPanel modePanel = new JPanel();
		modePanel.setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		int padding = 2;
		c.insets = new Insets(padding, padding, padding, padding);

		modePanel.add(new JLabel("Select a player mode: "), c);
		modesBox = new JComboBox<Modes>(Modes.values());
		modesBox.setSelectedItem(initMode);
		c.gridx++;
		modePanel.add(modesBox, c);

		// change/reset constraints for use on content panel
		c.gridx = 0;
		c.gridy = 0;
		padding = 5;
		c.insets = new Insets(padding, padding, padding, padding);

		//content.add(new StatTypeRulesPanel<Pass>(Pass.class));
		//content.add(new RulesPanel());
		//content.add(new PlayersPanel(model.getPlayersList(Modes.QB)));

		// scorer panels / add panels to content
		content.add(modePanel, c);
		//TODO: differentiate scorer panels (or drop second panel if it won't fit)
		ScorerPanel panel1 = new ScorerPanel(model, initMode);
		c.gridy++;
		content.add(panel1, c);
		/*panel2 = new ScorerPanel(model, initMode);
		c.gridx++;
		content.add(panel2, c);*/
		content.setPreferredSize(new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT));

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
	public void setMode(Modes mode) {
		panel1.setPlayersPanel(mode);
		//panel2.setPlayersPanel(mode);
	}

	/*
	 * listener methods
	 */
	public void addModeListener(ItemListener al) {
		modesBox.addItemListener(al);
	}
}
