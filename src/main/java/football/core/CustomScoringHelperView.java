package football.core;

import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.JPanel;

import football.core.graphics.ScorerPanel;
import football.core.graphics.RulesPanel;
import football.core.graphics.PlayersPanel;
import football.players.modes.Modes;

public final class CustomScoringHelperView extends JFrame
{
	private static final long serialVersionUID = -3565226027504869570L;
	private final int DEFAULT_WIDTH = 1400;
	private final int DEFAULT_HEIGHT = 800;

	private CustomScoringHelperModel model;

	public CustomScoringHelperView(CustomScoringHelperModel model, String title) {
		super(title);
		this.model = model;

		// set up content panel
		Dimension contentDimensions = new Dimension(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		JPanel content = new JPanel();
		//content.setLayout();
		/*Dimension rulesDimensions = new Dimension((int)((contentDimensions.getWidth()/2) * 0.9),
												  (int)(contentDimensions.getHeight() * 0.25));*/
		//content.add(new RulesPanel());
		//content.add(new PlayersPanel(model.getPlayersList(Modes.QB)));
		content.add(new ScorerPanel(model));
		content.setPreferredSize(contentDimensions);

		this.setContentPane(content);
		this.pack(); //resize frame based on preferred sizes of subcomponents
		this.setLocationRelativeTo(null); //center window on screen

		//TODO: pass window closing event to controller
		this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public CustomScoringHelperView(CustomScoringHelperModel model) {
		this(model, "Fantasy Football Custom Scoring Helper");
	}
}
