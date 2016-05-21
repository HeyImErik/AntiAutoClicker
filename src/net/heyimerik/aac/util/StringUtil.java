package net.heyimerik.aac.util;

public class StringUtil {
	public static String trim(String string, boolean doubles) {
		if (doubles) while (string.contains("  "))
			string = string.replace("  ", " ");

		return string.trim();
	}
}
