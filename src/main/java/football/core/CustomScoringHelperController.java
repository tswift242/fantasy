package football.core;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import football.players.modes.Modes;

public final class CustomScoringHelperController
{
	private CustomScoringHelperModel model;
	private CustomScoringHelperView view;

	public CustomScoringHelperController(CustomScoringHelperModel model,
											CustomScoringHelperView view) {
		this.model = model;
		this.view = view;

		// add listeners to the view
		view.addModeListener(new ModeListener());
	}

	class ModeListener implements ItemListener
	{
		@Override
		public void  itemStateChanged(ItemEvent e) {
			if(e.getStateChange() == ItemEvent.SELECTED) {
				Modes mode = view.getMode();
				//System.out.println("%%%%%%% mode: " + mode.toString());
				model.setMode(mode);
				//TODO: set players panel in view
			}
		}
	}
}
