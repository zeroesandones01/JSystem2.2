package Renderer;

import java.text.Format;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class FormatRenderer extends DefaultTableCellRenderer {
	
	private static final long serialVersionUID = 1L;
	private Format formatter;

	/*
	 * Use the specified formatter to format the Object
	 */
	public FormatRenderer(Format formatter) {
		this.formatter = formatter;
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}

	/*
	 * Use the specified formatter to format the Object
	 */
	public FormatRenderer(Format formatter, int alignment) {
		this.formatter = formatter;
		this.setHorizontalAlignment(alignment);
	}

	public void setValue(Object value) {
		// Format the Object before setting its value in the renderer
		try {
			if (value != null)
				value = formatter.format(value);
		} catch (IllegalArgumentException e) {
			//e.printStackTrace();
		}

		super.setValue(value);
	}

}
