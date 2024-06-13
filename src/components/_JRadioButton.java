package components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JRadioButton;

public class _JRadioButton extends JRadioButton {

	private static final long serialVersionUID = 1L;
	
	private String actionCommand = "";

	public _JRadioButton() {
		
	}

	public _JRadioButton(Icon icon) {
		super(icon);
	}

	public _JRadioButton(Action a) {
		super(a);
	}

	public _JRadioButton(String text) {
		super(text);
	}

	public _JRadioButton(Icon icon, boolean selected) {
		super(icon, selected);
	}

	public _JRadioButton(String text, boolean selected) {
		super(text, selected);
	}

	public _JRadioButton(String text, Icon icon) {
		super(text, icon);
	}

	public _JRadioButton(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
	}
	
	/**
	 * @return the actionCommand
	 */
	public String getActionCommand() {
		return actionCommand;
	}

	/**
	 * @param actionCommand the actionCommand to set
	 */
	public void setActionCommand(String actionCommand) {
		this.actionCommand = actionCommand;
	}

}
