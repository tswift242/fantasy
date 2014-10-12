package football.core.graphics;

import java.awt.Insets;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.players.modes.Mode;

public final class GlobalOptionsPanel extends GridBagPanel
{
	private final Logger logger = LoggerFactory.getLogger(this.getClass().getName());

	private JComboBox<Mode> modesBox;
	private JComboBox<String> sitesBox;
	private JButton rescoreButton;

	public GlobalOptionsPanel(Mode initMode, String initSite) {
		super(5);

		// Mode combo box
		this.add(new JLabel("Player mode: "), c);
		modesBox = new JComboBox<Mode>(Mode.values());
		modesBox.setSelectedItem(initMode);
		c.gridx++;
		this.add(modesBox, c);

		// League site combo box
		c.gridx--;
		c.gridy++;
		this.add(new JLabel("Leage site: "), c);
		sitesBox = new JComboBox<String>(new String[]{"NFL", "ESPN", "Yahoo", "CBS"});
		sitesBox.setSelectedItem(initSite);
		c.gridx++;
		this.add(sitesBox, c);

		// Rescore button
		rescoreButton = new JButton("Recalculate scores");
		c.gridx--;
		c.gridy++;
		c.gridwidth = 2;
		c.insets = new Insets(10, 2, 5, 2);
		this.add(rescoreButton, c);
	}

	/*
	 * listener methods
	 */
	public void addModeListener(ItemListener listener) {
		modesBox.addItemListener(listener);
		logger.info("registered mode listener");
	}

	public void addLeagueSiteListener(ItemListener listener) {
		sitesBox.addItemListener(listener);
		logger.info("registered league site listener");
	}

	public void addRecalculateScoreListener(ActionListener listener) {
		rescoreButton.addActionListener(listener);
		logger.info("registered recalculate score listener");
	}
}
