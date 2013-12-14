package football.core.graphics;

import java.awt.Dimension;
import java.awt.GridLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import football.stats.StatType;

/*
 * Panel displaying a number of text boxes for modifying scoring rules associated
 * with a given stat type.
 */

public final class StatTypeRulesPanel<T extends Enum<T> & StatType> extends JPanel
{
	private static final long serialVersionUID = -6296168608062180190L;
	// allows up to pattern of -dd.d/dd
	private static final int RULE_FIELD_NUM_COLS = 8;

	public StatTypeRulesPanel(Class<T> statType, Dimension dimensions) {
		// get categories for given stat type
		T[] categories = statType.getEnumConstants();
		int numCategories = categories.length;
		// switch to grid layout
		this.setLayout(new GridLayout(1, 2*numCategories));
		// create label and text field for every category and add
		// them to the panel
		for(int i = 0; i < numCategories; i++) {
			T category = categories[i];
			this.add(new JLabel(category.toString() + ": "));
			//TODO: keep reference to these in a list or map
			//TODO: set text to be default rule value
			JTextField ruleField = new JTextField("0.0", RULE_FIELD_NUM_COLS);
			//TODO: play with this, as well as preferred size of panel
			ruleField.setHorizontalAlignment(JTextField.RIGHT);
			this.add(ruleField);
		}
		this.setPreferredSize(dimensions);
	}
}
