package io.ologn.common.type;

import java.util.Comparator;

/**
 * Utilities for java.util.Comparator objects
 * @author lisq199
 */
public class OlognComparators {
	
	/**
	 * Get a Comparator from a Comparable type.<br>
	 * Note: the type argument is what's important.
	 * @return
	 */
	public static <T extends Comparable<? super T>> Comparator<T>
			getFromComparable() {
		return (a, b) -> a.compareTo(b);
	}

}
