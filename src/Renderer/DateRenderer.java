package Renderer;

import java.text.DateFormat;
import java.text.Format;

/*
 *	Use a formatter to format the cell Object
 */
public class DateRenderer extends FormatRenderer {

	private static final long serialVersionUID = 1L;

	/*
	 * Use the specified formatter to format the Object
	 */
	public DateRenderer(Format formatter) {
		super(formatter);
	}

	/*
	 * Use the specified formatter to format the Object
	 */
	public DateRenderer(Format formatter, int alignment) {
		super(formatter, alignment);
	}







	/*
	 * Use the default date/time formatter for the default locale
	 */
	public static DateRenderer getDateRenderer() {
		return new DateRenderer(DateFormat.getDateInstance());
	}

	/*
	 * Use the default date/time formatter for the default locale
	 */
	public static DateRenderer getDateTimeRenderer() {
		return new DateRenderer(DateFormat.getDateTimeInstance());
	}

	/*
	 * Use the default date/time formatter for the default locale
	 */
	public static DateRenderer getDateTimeRenderer(String format) {
		return new DateRenderer(DateFormat.getDateTimeInstance());
	}

	/*
	 * Use the default time formatter for the default locale
	 */
	public static DateRenderer getTimeRenderer() {
		return new DateRenderer(DateFormat.getTimeInstance());
	}

}
