package io.ologn.common.math;

/**
 * Math related utilities
 * @author lisq199
 */
public class OlognMath {
	
	/**
	 * Check if an int is a prime number.<br>
	 * Source of algorithm: <a href="http://www.mkyong.com/java/how-to-determine-a-prime-number-in-java/">link</a>
	 * @param n
	 * @return
	 */
	public static boolean isPrime(int n) {
		if (n == 2) {
			return true;
		}
		//check if n is a multiple of 2
		if (n < 2 || n % 2 == 0) {
			return false;
		}
		//if not, then just check the odds
		for (int i = 3; i * i <= n; i += 2) {
			if(n % i == 0) {
				return false;
			}
		}
		return true;
	}
	
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
