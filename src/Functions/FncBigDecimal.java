package Functions;

import java.math.BigDecimal;
import java.text.DecimalFormat;

public class FncBigDecimal {

	public static String format(BigDecimal amount) {
		try {
			return new DecimalFormat("###,###,###,##0.00").format(amount.doubleValue());
		} catch (NullPointerException e) {
			return "0.00";
		}
	}
	
	public static BigDecimal zeroValue() {
		return new BigDecimal("0.00");
	}
}
