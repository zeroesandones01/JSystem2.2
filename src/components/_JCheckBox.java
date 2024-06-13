package components;

import javax.swing.Action;
import javax.swing.Icon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import Functions.FncGlobal;

public class _JCheckBox extends JCheckBox {

	private static final long serialVersionUID = 1L;

	public _JCheckBox() {
		initGUI(null);
	}

	public _JCheckBox(Icon icon) {
		super(icon);
		initGUI(null);
	}

	public _JCheckBox(String text) {
		super(text);
		initGUI(text);
	}

	public _JCheckBox(Action a) {
		super(a);
		initGUI(null);
	}

	public _JCheckBox(Icon icon, boolean selected) {
		super(icon, selected);
		initGUI(null);
	}

	public _JCheckBox(String text, boolean selected) {
		super(text, selected);
		initGUI(text);
	}

	public _JCheckBox(String text, Icon icon) {
		super(text, icon);
		initGUI(text);
	}

	public _JCheckBox(String text, Icon icon, boolean selected) {
		super(text, icon, selected);
		initGUI(text);
	}

	public void initGUI(String text) {
		//this.setForeground(FncLookAndFeel.systemColor);
		this.setBorder(FncGlobal.lineBorder);
		this.setHorizontalTextPosition(SwingConstants.TRAILING);
		//this.setHorizontalAlignment(SwingConstants.TRAILING);
		
		JLabel rightLabel = new JLabel(text, SwingConstants.TRAILING);
		rightLabel.setSize(this.getSize());
		//rightLabel.setHorizontalAlignment(SwingConstants.RIGHT);
		//rightLabel.setHorizontalTextPosition(SwingConstants.RIGHT);
        //this.add(rightLabel);
	}

}
