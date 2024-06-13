package Renderer;

import java.text.DecimalFormat;
import java.text.Format;
import java.text.NumberFormat;
import java.util.Currency;
import java.util.Locale;

import javax.swing.SwingConstants;

public class NumberRenderer extends FormatRenderer {
	
	private static final long serialVersionUID = 1L;

	public NumberRenderer(Format formatter) {
		super(formatter, SwingConstants.RIGHT);
	}

	public NumberRenderer(Format formatter, int alignment) {
		super(formatter, alignment);
	}

	/*
	 * Use the default currency formatter for the default locale
	 */
	public static NumberRenderer getCurrencyRenderer() {
		for(Locale local : NumberFormat.getAvailableLocales()){
			System.out.printf("%-25s %-25s %n", local.getCountry(), local.getLanguage() );
		}
		NumberFormat fr = NumberFormat.getCurrencyInstance(new Locale("en", "PH"));
		try {
			fr.setCurrency(Currency.getInstance("x"));
		} catch (IllegalArgumentException e) {
		}
		return new NumberRenderer(fr);
	}

	/*
	 * Use the default decimal formatter for the default locale
	 */
	public static NumberRenderer getMoneyRenderer() {
		/*DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(true);
		df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance());
		return new NumberRenderer(df);*/
		return new NumberRenderer(new DecimalFormat("###,###,###,##0.00"));
	}

	/*
	 * Use the default decimal formatter for the default locale
	 */
	public static NumberRenderer getMoneyRenderer2() {
		/*DecimalFormat df = new DecimalFormat();
		df.setDecimalSeparatorAlwaysShown(true);
		df.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance());
		return new NumberRenderer(df);*/
		return new NumberRenderer(new DecimalFormat("###,###,###,##0.0000"));
	}

	/*
	 * Use the default integer formatter for the default locale
	 */
	public static NumberRenderer getIntegerRenderer() {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		return new NumberRenderer(nf);
	}

	/*
	 * Use the default integer formatter for the default locale
	 */
	public static NumberRenderer getIntegerRenderer(int alignment) {
		NumberFormat nf = NumberFormat.getIntegerInstance();
		nf.setGroupingUsed(false);
		return new NumberRenderer(nf, alignment);
	}

	/*
	 * Use the default percent formatter for the default locale
	 */
	public static NumberRenderer getPercentRenderer() {
		return new NumberRenderer(NumberFormat.getPercentInstance());
	}
}
