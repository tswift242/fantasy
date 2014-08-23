package football.core.graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.JPanel;

/*
 * JPanel which has a GridBagLayout
 */

public class GridBagPanel extends JPanel
{
	protected GridBagConstraints c;

	// construct JPanel  with GridBagLayout, with intial cell (0,0) and
	// all cells centered
	public GridBagPanel() {
		// switch to grid bag layout
		this.setLayout(new GridBagLayout());
		// set up constraints
		c = new GridBagConstraints();
		// start at cell (0,0)
		c.gridx = 0;
		c.gridy = 0;
		// center all cells
		c.anchor = GridBagConstraints.CENTER;

	}


	// construct JPanel with GridBagLayout which additionally has the specified
	// cell padding in all directions
	public GridBagPanel(int padding) {
		this();
		// cell padding
		c.insets = new Insets(padding, padding, padding, padding);
	}

	// for components which have a GridBagPanel instance instead of extending the class
	public GridBagConstraints getConstraints() {
		return c;
	}
}
