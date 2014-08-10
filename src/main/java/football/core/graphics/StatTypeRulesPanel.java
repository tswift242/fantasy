package football.core.graphics;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JLabel;
import javax.swing.JPanel;

import football.stats.StatType;

/*
 * Panel displaying a number of text boxes for modifying scoring rules associated
 * with a given stat type.
 */

public final class StatTypeRulesPanel<T extends Enum<T> & StatType> extends JPanel
{
	private static final long serialVersionUID = -6296168608062180190L;
	// allow no more than this many categories to appear in a single row
	private static final int MAX_CATEGORIES_PER_ROW = 8;
	//TODO: create map of text fields to categories, or create obj which extends JTextField
	//private Map<JTextField,T> textFieldMap;
	private List<RuleTextField<T>> ruleTextFields;

	public StatTypeRulesPanel(Class<T> statType) {
		// get categories for given stat type
		T[] categories = statType.getEnumConstants();

		// switch to grid bag layout
		this.setLayout(new GridBagLayout());
		// set up constraints
		GridBagConstraints c = new GridBagConstraints();
		c.gridx = 0;
		c.gridy = 0;
		c.anchor = GridBagConstraints.CENTER;
		//TODO: make parameter?
		int padding = 2;
		c.insets = new Insets(padding, padding, padding, padding);

		// for StatType's with many categories, arrange the categories into
		// multiple even-sized rows
		int numCategories = categories.length;
		int numRows = 1;
		// multiply by two to account for the label of each text field
		int numCols = 2 * numCategories;
		if(numCategories > MAX_CATEGORIES_PER_ROW) {
			//TODO: see if some of this math cancels out and we can simplify and remove numRows
			numRows = (int)Math.ceil((double)numCategories / MAX_CATEGORIES_PER_ROW);
			numCols = (2 * (int)Math.ceil((double)numCategories / numRows));
		}

		// create label and text field for every category and add
		// them to the panel
		ruleTextFields = new ArrayList<RuleTextField<T>>();
		for(T category : categories) {
			if(c.gridx == numCols) {
				c.gridx = 0;
				c.gridy++;
			}
			this.add(new JLabel(category.toString() + ": "), c);
			c.gridx++;
			//TODO: set text to be default rule value
			RuleTextField<T> ruleField = new RuleTextField<T>(category, "0.0");
			this.add(ruleField, c);
			ruleTextFields.add(ruleField);
			c.gridx++;
		}
	}

	public List<RuleTextField<T>> getRuleTextFields() {
		return ruleTextFields;
	}
}
