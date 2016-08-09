package io.ologn.common.time;

import java.util.Calendar;
import java.util.Date;

import org.apache.commons.lang3.ArrayUtils;

/**
 * Utilities for java.util.Date objects
 * @author lisq199
 */
public class OlognDates {
	
	public static String getIso8601(Date date) {
		return OlognTime.getIso8601FromUnixTime(date.getTime() / 1000l);
	}
	
	/**
	 * Get year as an int from a Date object
	 * @param date
	 * @return
	 */
	public static int getYear(Date date) {
		Calendar c = Calendar.getInstance();
		c.setTime(date);
		return c.get(Calendar.YEAR);
	}
	
	/**
	 * Check if multiple Date objects are all in the same year
	 * @param dates
	 * @return
	 */
	public static boolean sameYear(Date... dates) {
		if (dates.length < 2) {
			throw new IllegalArgumentException(
					"There has to be at least 2 parameters");
		}
		if (ArrayUtils.contains(dates, null)) {
			return false;
		}
		for (int i = 0; i < dates.length - 1; i++) {
			if (getYear(dates[i]) != getYear(dates[i + 1])) {
				return false;
			}
		}
		return true;
	}
	
}
