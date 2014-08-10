package football.core;

import football.core.listeners.ModeListener;
import football.core.listeners.RecalculateScoreListener;
import football.core.listeners.RulesListener;

public final class CustomScoringHelperController
{
	public CustomScoringHelperController(CustomScoringHelperModel model,
											CustomScoringHelperView view) {
		// add listeners to the view
		view.addModeListener(new ModeListener(model, view));
		// TODO: isn't working; using RecalculateScoreListener for this instead
		//view.addRulesListener(new RulesListener(model));
		view.addRecalculateScoreListener(new RecalculateScoreListener(model));
	}
}
