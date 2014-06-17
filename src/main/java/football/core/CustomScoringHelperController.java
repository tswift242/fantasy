package football.core;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.modes.Modes;

public final class CustomScoringHelperController
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;
	private CustomScoringHelperView view;

	public CustomScoringHelperController(CustomScoringHelperModel model,
											CustomScoringHelperView view) {
		this.model = model;
		this.view = view;

		// add listeners to the view
		view.addModeListener(new ModeListener());
	}

	private class ModeListener implements ItemListener
	{
		@Override
		public void itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				logger.info("New mode selected: " + e.getItem().toString());
				Modes newMode = (Modes)e.getItem();
				model.setMode(newMode);
				//TODO: set players panel in view
			}
		}
	}
}
