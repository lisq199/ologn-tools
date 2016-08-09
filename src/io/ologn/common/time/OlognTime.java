package io.ologn.common.time;

import java.time.Instant;
import java.time.format.DateTimeFormatter;

/**
 * Other utilities for anything time related
 * @author lisq199
 */
public class OlognTime {
	
	public static String getIso8601FromUnixTime(long epochSecond) {
		Instant instant = Instant.ofEpochSecond(epochSecond);
		return DateTimeFormatter.ISO_INSTANT.format(instant);
	}

}
