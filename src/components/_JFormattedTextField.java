package components;

import java.text.DecimalFormat;
import java.text.Format;

import javax.swing.JFormattedTextField;
import javax.swing.SwingConstants;

public class _JFormattedTextField extends JFormattedTextField {

	private static final long serialVersionUID = 1L;

	public _JFormattedTextField() {
		super(new DecimalFormat("###,###,###,##0.00"));
		initGUI();
	}

	public _JFormattedTextField(Object value) {
		super(value);
		initGUI();
	}

	public _JFormattedTextField(Format format) {
		super(format);
		initGUI();
	}

	public _JFormattedTextField(AbstractFormatter formatter) {
		super(formatter);
		initGUI();
	}

	public _JFormattedTextField(AbstractFormatterFactory factory) {
		super(factory);
		initGUI();
	}

	public _JFormattedTextField(AbstractFormatterFactory factory, Object currentValue) {
		super(factory, currentValue);
		initGUI();
	}
	
	private void initGUI() {
		getDocument().putProperty("source", this);
		
		setHorizontalAlignment(SwingConstants.RIGHT);
		setEditable(false);
	}

}
