package net.heyimerik.aac.util;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

public class NumberUtil {
	public static int getInt(String in) {
		try {
			return Integer.parseInt(in);
		} catch (Exception localException) {
			return -1;
		}
	}

	public static String format(double d) {
		DecimalFormat format = new DecimalFormat("#.#", DecimalFormatSymbols.getInstance(Locale.ENGLISH));

		return format.format(d);
	}
}
