package football.core.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import football.core.intface.CustomScoringHelperModel;
import football.core.intface.CustomScoringHelperView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.modes.Mode;

public class ModeListener implements ItemListener
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;
	private CustomScoringHelperView view;

	public ModeListener(CustomScoringHelperModel model, CustomScoringHelperView view) {
		this.model = model;
		this.view = view;
	}

	@Override
	public void itemStateChanged(ItemEvent e) {
		if(e.getStateChange() == ItemEvent.SELECTED) {
			Mode newMode = (Mode)e.getItem();
			logger.info("New mode selected: " + newMode.toString());

			model.setMode(newMode);
			view.setMode(newMode);

			// change the results logger to write to new file based on the new mode
			model.updateResultsLogger(newMode);
			//TODO: update file path in GUI
		}
	}
}
