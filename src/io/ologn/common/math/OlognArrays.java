package io.ologn.common.math;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Array related utilitis
 * @author lisq199
 */
public class OlognArrays {
	
	/**
	 * Get the median of an array. This method does not modify the original 
	 * array.
	 * @param m
	 * @param sorted whether the array is already sorted
	 * @return
	 */
	public static int median(int[] m, boolean sorted) {
		if (m.length == 0) {
			throw new IllegalArgumentException("median of empty array");
		}
		int[] mm = m.clone();
		if (!sorted) {
			Arrays.sort(m);
		}
		int mid = mm.length / 2;
		if (mm.length % 2 == 1) {
			return mm[mid];
		} else {
			return (mm[mid - 1] + mm[mid]) / 2;
		}
	}
	
	/**
	 * Get the median of an array. This method does not modify the original 
	 * array.
	 * @param m
	 * @param sorted whether the array is already sorted
	 * @return
	 */
	public static long median(long[] m, boolean sorted) {
		if (m.length == 0) {
			throw new IllegalArgumentException("median of empty array");
		}
		long[] mm = m.clone();
		if (!sorted) {
			Arrays.sort(m);
		}
		int mid = mm.length / 2;
		if (mm.length % 2 == 1) {
			return mm[mid];
		} else {
			return (mm[mid - 1] + mm[mid]) / 2;
		}
	}
	
	/**
	 * Get the median of an array. This method does not modify the original 
	 * array.
	 * @param m
	 * @param sorted whether the array is already sorted
	 * @return
	 */
	public static float median(float[] m, boolean sorted) {
		if (m.length == 0) {
			throw new IllegalArgumentException("median of empty array");
		}
		float[] mm = m.clone();
		if (!sorted) {
			Arrays.sort(m);
		}
		int mid = mm.length / 2;
		if (mm.length % 2 == 1) {
			return mm[mid];
		} else {
			return (mm[mid - 1] + mm[mid]) / 2;
		}
	}
	
	/**
	 * Get the median of an array. This method does not modify the original 
	 * array.
	 * @param m
	 * @param sorted whether the array is already sorted
	 * @return
	 */
	public static double median(double[] m, boolean sorted) {
		if (m.length == 0) {
			throw new IllegalArgumentException("median of empty array");
		}
		double[] mm = m.clone();
		if (!sorted) {
			Arrays.sort(m);
		}
		int mid = mm.length / 2;
		if (mm.length % 2 == 1) {
			return mm[mid];
		} else {
			return (mm[mid - 1] + mm[mid]) / 2;
		}
	}
	
	/**
	 * Get the mode of an array.
	 * @param m
	 * @return
	 */
	public static <T> List<T> mode(T[] m) {
		if (m.length == 0) {
			throw new IllegalArgumentException("mode of empty array");
		}
		List<T> modes = new ArrayList<T>();
		Map<T, Integer> countMap = new HashMap<T, Integer>();
		int maxCount = -1;
		for (T t : m) {
			int count = 1;
			if (countMap.containsKey(t)) {
				count = countMap.get(t) + 1;
			}
			countMap.put(t, count);
			if (count > maxCount) {
				maxCount = count;
			}
		}
		final int maxCount2 = maxCount;
		countMap.entrySet().stream()
				.filter(e -> e.getValue() == maxCount2)
				.forEach(e -> modes.add(e.getKey()));
		return modes;
	}

}
