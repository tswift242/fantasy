package football.core.graphics;

import javax.swing.JTextField;

import static com.google.common.base.Preconditions.checkNotNull;

import football.stats.StatType;

/*
 * JTextField for specifying the value of a scoring rule for a given category
 */

public class RuleTextField<T extends Enum<T> & StatType> extends JTextField
{
	// visually allows up to pattern of -dd.dd/dd
	private static final int RULE_FIELD_NUM_COLS = 5;
	public static final String TEXT_FIELD_PROPERTY = "textField";

	private final T category;

	public RuleTextField(T category, String initText) {
		super(initText, RULE_FIELD_NUM_COLS);
		//TODO: play with this
		setHorizontalAlignment(JTextField.RIGHT);
		// add reference to this text field in its document
		getDocument().putProperty(TEXT_FIELD_PROPERTY, this);

		checkNotNull(category, "category is null");
		this.category = category;
	}

	public T getCategory() {
		return category;
	}
}
