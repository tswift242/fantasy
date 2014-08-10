package football.core.listeners;

import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.CustomScoringHelperModel;
import football.core.CustomScoringHelperView;
import football.players.modes.Modes;

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
			logger.info("New mode selected: " + e.getItem().toString());
			Modes newMode = (Modes)e.getItem();
			model.setMode(newMode);
			view.setMode(newMode);
		}
	}
}
