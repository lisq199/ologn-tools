package io.ologn.common.math;

import java.math.BigInteger;

/**
 * Math related utilities
 * @author lisq199
 */
public class OlognMath {
	
	/**
	 * Check if a is between b and c
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
	 * Check if a is between b and c
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
	
	/**
	 * Factorial with BigInteger (arbitrary-precision)
	 * @param n
	 * @return
	 */
	public static BigInteger factorial(int n) {
		if (n < 0) {
			throw new IllegalArgumentException("factorial of negative number");
		}
		BigInteger result = BigInteger.ONE;
		for (int i = 1; i <= n; i++) {
			result = result.multiply(BigInteger.valueOf(i));
		}
		return result;
	}
	
}
