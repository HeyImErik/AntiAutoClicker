package net.heyimerik.aac.util;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

public class DateUtil {
	public static String getFileDate(boolean exactTime, String timeZone) {
		DateFormat format = new SimpleDateFormat("MM-dd-YYYY");
		int month = NumberUtil.getInt(format.format(Calendar.getInstance().getTime()).split("-")[0]);
		int year = NumberUtil.getInt(format.format(Calendar.getInstance().getTime()).split("-")[2]);
		int day = NumberUtil.getInt(format.format(Calendar.getInstance().getTime()).split("-")[1]);

		String date = day + "-" + month + "-" + year;
		if (exactTime) {
			GregorianCalendar calendar = new GregorianCalendar();
			calendar.setTimeZone(TimeZone.getTimeZone(timeZone == null ? "CEST" : timeZone));
			format = new SimpleDateFormat("HH_mm_ss", Locale.ENGLISH);

			date = date + "_" + format.format(calendar.getTime());
		}
		return date;
	}

	public static String getDate(boolean exactTime, String timeZone, String dayFormat, String hourFormat) {
		DateFormat format = new SimpleDateFormat("MM dd YYYY");
		int month = NumberUtil.getInt(format.format(Calendar.getInstance().getTime()).split(" ")[0]);
		int year = NumberUtil.getInt(format.format(Calendar.getInstance().getTime()).split(" ")[2]);
		int day = NumberUtil.getInt(format.format(Calendar.getInstance().getTime()).split(" ")[1]);

		DateFormat formatter = new SimpleDateFormat("MMMM", Locale.ENGLISH);
		GregorianCalendar calendar = new GregorianCalendar();
		calendar.setTimeZone(TimeZone.getTimeZone(timeZone == null ? TimeZone.getDefault().getDisplayName() : timeZone));
		calendar.set(5, 1);
		calendar.set(2, month - 1);

		String date = dayFormat.toLowerCase().replace("dd", Integer.toString(day)).replace("mm", Integer.toString(month)).replace("yyyy", Integer.toString(year));
		if (exactTime) {
			formatter = new SimpleDateFormat(hourFormat);

			date = date + " " + formatter.format(calendar.getTime());
		}
		return StringUtil.trim(date, true);
	}
}
