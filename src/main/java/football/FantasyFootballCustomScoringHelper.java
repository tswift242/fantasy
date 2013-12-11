package football;

import javax.swing.JFrame;

import football.core.CustomScoringHelperModel;
import football.core.CustomScoringHelperView;

public class FantasyFootballCustomScoringHelper
{
	public static void main(String[] args)
	{
		CustomScoringHelperModel model = new CustomScoringHelperModel();
		model.run(args);
		CustomScoringHelperView view = new CustomScoringHelperView(model);
		view.setVisible(true);
	}
}
