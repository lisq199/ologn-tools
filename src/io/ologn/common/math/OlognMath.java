package io.ologn.common.math;

/**
 * Math related utilities
 * @author lisq199
 */
public class OlognMath {
	
	/**
	 * Check if a is between b and c (exclusive).
	 * @param a
	 * @param b
	 * @param c
	 * @param inclusive
	 * @return whether a is between b and c
	 */
	public static boolean isBetween(long a, long b, long c,
			boolean inclusive) {
		if (inclusive) {
			return c >= b ? a >= b && a <= c : a >= c && a <= b;
		} else {
			return c > b ? a > b && a < c : a > c && a < b;
		}
	}
	
	/**
	 * Check if a is between b and c (exclusive).
	 * @param a
	 * @param b
	 * @param c
	 * @param inclusive
	 * @return whether a is between b and c
	 */
	public static boolean isBetween(double a, double b, double c,
			boolean inclusive) {
		if (inclusive) {
			return c >= b ? a >= b && a <= c : a >= c && a <= b;
		} else {
			return c > b ? a > b && a < c : a > c && a < b;
		}
	}
	
}
