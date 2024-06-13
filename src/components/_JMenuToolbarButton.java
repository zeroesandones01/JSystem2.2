package components;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.Icon;
import javax.swing.JButton;

import Functions.RGBGrayFilter;

public class _JMenuToolbarButton extends JButton {

	private static final long serialVersionUID = 1L;

	public _JMenuToolbarButton(Icon icon) {
		super(icon);
		initThis(icon);
	}

	private void initThis(Icon icon) {
		setVisible(false);
		//setBorderPainted(false);
		setBorder(BorderFactory.createLineBorder(Color.GRAY));
		setContentAreaFilled(false); 
		setOpaque(false);
		setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));

		setRolloverIcon(icon);
		setRolloverEnabled(true);
		setIcon(RGBGrayFilter.getDisabledIcon(this, icon));
	}

}