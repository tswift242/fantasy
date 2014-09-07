package football.core.graphics;

import javax.swing.JTextField;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

import static com.google.common.base.Preconditions.checkNotNull;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import football.stats.StatType;

/*
 * JTextField for specifying the value of a scoring rule for a given category
 */

public class RuleTextField<T extends Enum<T> & StatType> extends JTextField
{
	private static final Logger logger = LoggerFactory.getLogger(RuleTextField.class.getName());

	// visually allows up to pattern of -dd.dd/dd
	private static final int RULE_FIELD_NUM_COLS = 5;
	public static final String TEXT_FIELD_PROPERTY = "textField";

	private final T category;

	public RuleTextField(T category, String initText) {
		super(new RuleDocument(), initText, RULE_FIELD_NUM_COLS);
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

	private static class RuleDocument extends PlainDocument
	{
		private static final String defaultContent = "0.0";

		// set document content to defaultContent (0.0) if the user clears all content
		@Override
		protected void postRemoveUpdate(DefaultDocumentEvent e) {
			super.postRemoveUpdate(e);
			if(getLength() == 0) {
				try {
					logger.info("Document is empty. Setting content to 0.0");
					insertString(0, defaultContent, null);
				} catch(BadLocationException ex) {
					logger.error(ex.toString());
				}
			}
		}
	}
}
