/**
 * 
 */
package components;

import java.awt.Cursor;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JButton;

/**
 * @author Alvin Gonzales
 *
 */
public class _JButton extends JButton {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4058828837662411066L;
	
	private String additional_info = null;

	/**
	 * 
	 */
	public _JButton() {
		initThis();
	}

	/**
	 * @param text
	 */
	public _JButton(String text) {
		super(text);
		initThis();
	}

	/**
	 * @param a
	 */
	public _JButton(Action a) {
		super(a);
		initThis();
	}

	/**
	 * @param icon
	 */
	public _JButton(Icon icon) {
		super(icon);
		initThis();
	}

	/**
	 * @param text
	 * @param icon
	 */
	public _JButton(String text, Icon icon) {
		super(text, icon);
		initThis();
	}
	
	protected void initThis() {
		setActionCommand(getText());
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
	}

	public String getAdditionalInfo() {
		return additional_info;
	}

	public void setAdditionalInfo(String additional_info) {
		this.additional_info = additional_info;
	}

}
