package football;

import javax.swing.JFrame;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.CustomScoringHelperController;
import football.core.CustomScoringHelperModel;
import football.core.CustomScoringHelperView;

public class FantasyFootballCustomScoringHelper
{
	private static final Logger logger = LoggerFactory.getLogger(FantasyFootballCustomScoringHelper.class.getName());

	public static void main(String[] args)
	{
		CustomScoringHelperModel model = new CustomScoringHelperModel();
		// if no cmd line args, then run GUI version; else, run command line version
		boolean runGUIVersion = (args.length == 0);
		if(runGUIVersion) {
			logger.info("Running GUI version");
			CustomScoringHelperView view = new CustomScoringHelperView(model);
			CustomScoringHelperController controller = new CustomScoringHelperController(model, view);
			view.setVisible(true);
		} else {
			logger.info("Running command line version");
			model.run(args);
		}
	}
}
