package football;

import football.core.SimpleController;
import football.core.SimpleModel;
import football.core.SimpleView;
import football.core.intface.CustomScoringHelperController;
import football.core.intface.CustomScoringHelperModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class FantasyFootballCustomScoringHelper
{
	private static final Logger logger = LoggerFactory.getLogger(FantasyFootballCustomScoringHelper.class.getName());

	public static void main(String[] args)
	{
		CustomScoringHelperModel model = new SimpleModel();
		// if no cmd line args, then run GUI version; else, run command line version
		boolean runGUIVersion = (args.length == 0);
		if(runGUIVersion) {
			logger.info("Running GUI version");
			SimpleView view = new SimpleView(model);
			CustomScoringHelperController controller = new SimpleController(model, view);
			view.setVisible(true);
		} else {
			logger.info("Running command line version");
			model.run(args);
		}
	}
}
