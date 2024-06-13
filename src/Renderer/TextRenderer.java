package Renderer;

import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

public class TextRenderer extends DefaultTableCellRenderer {

	private static final long serialVersionUID = 1L;

	public TextRenderer(int alignment) {
		this.setHorizontalAlignment(SwingConstants.CENTER);
	}

	public TextRenderer(int alignment, String tooltip) {
		this.setHorizontalAlignment(SwingConstants.CENTER);
		this.setToolTipText(tooltip);
	}

	public static TextRenderer getTextRenderer(int alignment) {
		return new TextRenderer(SwingConstants.CENTER);
	}

	public static TextRenderer getTextRenderer(int alignment, String tooltip) {
		return new TextRenderer(SwingConstants.CENTER, tooltip);
	}

}