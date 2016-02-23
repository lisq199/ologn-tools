package io.ologn.common.type;

import java.time.Duration;
import java.util.Arrays;

/**
 * Utilities for java.time.Duration objects.
 * @author lisq199
 */
public class OlognDurations {
	
	/**
	 * Get the average of a Duration array
	 * @param m
	 * @return
	 */
	public static Duration average(Duration... m) {
		if (m.length == 0) {
			throw new IllegalArgumentException("average of empty array");
		}
		// Set the average to the first element in the array
		Duration avg = Duration.ZERO.plus(m[0]);
		for (int i = 1; i < m.length; i++) {
			// i is also the number of elements already counted
			avg = avg.multipliedBy(i).plus(m[i]).dividedBy(i + 1);
		}
		return avg;
	}

	/**
	 * Get the median of a Duration array. This method does not modify 
	 * the original array
	 * @param m
	 * @param sorted whether the array is already sorted
	 * @return
	 */
	public static Duration median(Duration[] m, boolean sorted) {
		if (m.length == 0) {
			throw new IllegalArgumentException("median of empty array");
		}
		Duration[] mm;
		if (sorted) {
			mm = m;
		} else {
			mm = m.clone();
			Arrays.parallelSort(mm);
		}
		int mid = mm.length / 2;
		if (mm.length % 2 == 1) {
			return mm[mid];
		} else {
			return average(mm[mid - 1], mm[mid]);
		}
	}

}
