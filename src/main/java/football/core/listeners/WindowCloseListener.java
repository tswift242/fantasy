package football.core.listeners;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.core.intface.CustomScoringHelperModel;

public class WindowCloseListener extends WindowAdapter
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private CustomScoringHelperModel model;

	public WindowCloseListener(CustomScoringHelperModel model) {
		this.model = model;
	}

	@Override
	public void windowClosing(WindowEvent e) {
		logger.info("Closing program");
		model.close();
		System.exit(0);
	}
}
