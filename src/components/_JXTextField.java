/**
 * 
 */
package components;

import interfaces._GUI;

import java.awt.Color;

import org.jdesktop.swingx.JXTextField;

/**
 * @author Alvin Gonzales
 *
 */
public class _JXTextField extends JXTextField implements _GUI{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4043305898086459835L;

	/**
	 * 
	 */
	public _JXTextField() {
		initGUI();
	}

	/**
	 * @param promptText
	 */
	public _JXTextField(String promptText) {
		super(promptText);
		initGUI();
	}

	/**
	 * @param promptText
	 * @param promptForeground
	 */
	public _JXTextField(String promptText, Color promptForeground) {
		super(promptText, promptForeground);
		initGUI();
	}

	/**
	 * @param promptText
	 * @param promptForeground
	 * @param promptBackground
	 */
	public _JXTextField(String promptText, Color promptForeground, Color promptBackground) {
		super(promptText, promptForeground, promptBackground);
		initGUI();
	}

	@Override
	public void initGUI() {
		this.setEditable(false);
	}

}
