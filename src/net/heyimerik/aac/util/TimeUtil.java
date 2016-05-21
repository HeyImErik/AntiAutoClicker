package net.heyimerik.aac.util;

import java.util.concurrent.TimeUnit;

public class TimeUtil {
	public static String convert(long timeSeconds) {
		if (timeSeconds < 0) { return "Permanent"; }
		if (timeSeconds == 0) { return "Now"; }

		long years = (long) Math.floor(timeSeconds >= TimeUnit.DAYS.toSeconds(365) ? timeSeconds / TimeUnit.DAYS.toSeconds(365) : 0);
		timeSeconds -= years * TimeUnit.DAYS.toSeconds(365);
		long months = (long) Math.floor(timeSeconds >= (TimeUnit.DAYS.toSeconds(365) / 12L) ? timeSeconds / (TimeUnit.DAYS.toSeconds(365) / 12L) : 0);
		timeSeconds -= months * (TimeUnit.DAYS.toSeconds(365) / 12L);
		long days = (long) Math.floor(timeSeconds >= TimeUnit.DAYS.toSeconds(1) ? timeSeconds / TimeUnit.DAYS.toSeconds(1) : 0);
		timeSeconds -= days * (TimeUnit.DAYS.toSeconds(1));
		long hours = (long) Math.floor(timeSeconds >= TimeUnit.HOURS.toSeconds(1) ? timeSeconds / TimeUnit.HOURS.toSeconds(1) : 0);
		timeSeconds -= hours * (TimeUnit.HOURS.toSeconds(1));
		long minutes = (long) Math.floor(timeSeconds >= TimeUnit.MINUTES.toSeconds(1) ? timeSeconds / TimeUnit.MINUTES.toSeconds(1) : 0);
		timeSeconds -= minutes * (TimeUnit.MINUTES.toSeconds(1));

		long seconds = timeSeconds;

		String out = "";
		if (years > 0) out += years + (years > 1 ? " years" : " year") + ", ";
		if (months > 0) out += months + (months > 1 ? " months" : " month") + ", ";
		if (days > 0) out += days + (days > 1 ? " days" : " day") + ", ";
		if (hours > 0) out += hours + (hours > 1 ? " hours" : " hour") + ", ";
		if (minutes > 0) out += minutes + (minutes > 1 ? " minutes" : " minute") + ", ";
		if (seconds > 0) out += seconds + (seconds > 1 ? " seconds" : " second") + ", ";

		return out.substring(0, out.length() - 2);
	}

	public static void main(String[] args) {
		System.out.println(convert(125123123L));
	}
}
