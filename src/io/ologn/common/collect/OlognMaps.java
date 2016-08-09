package io.ologn.common.collect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Utilities for java.util.Map objects.
 * @author lisq199
 */
public class OlognMaps {
	
	/**
	 * Sort a map by its entries with a specified Comparator.
	 * @param map
	 * @param c
	 * @return
	 */
	public static <K, V> Map<K, V> sortByEntry(
			Map<K, V> map, Comparator<Map.Entry<K, V>> c) {
		List<Map.Entry<K, V>> list =
				new ArrayList<Map.Entry<K, V>>(map.entrySet());
		list.sort(c);
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> e : list) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}
	
	/**
	 * Sort a map by its values with a specified Comparator
	 * @param map
	 * @param c
	 * @return
	 */
	public static <K, V> Map<K, V> sortByValue(
			Map<K, V> map, Comparator<V> c) {
		return sortByEntry(map,
				(e1, e2) -> c.compare(e1.getValue(), e2.getValue()));
	}
	
	/**
	 * Sort a Map by its values with the default Comparator. The type of 
	 * the values of the Map has to be Comparable.
	 * @param map
	 * @param naturalOrder
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map, boolean naturalOrder) {
		Comparator<V> c = (a, b) -> a.compareTo(b);
		return sortByValue(map, naturalOrder ? c : c.reversed());
	}
	
	/**
	 * Sort a Map by its keys with a specified Comparator.<br>
	 * Note: Since a TreeMap is used, the returned map will stay 
	 * sorted forever
	 * @param map
	 * @param c
	 * @return
	 */
	public static <K, V> Map<K, V> sortByKey(Map<K, V> map,
			Comparator<K> c) {
		Map<K, V> result = new TreeMap<K, V>(c);
		result.putAll(map);
		return result;
	}
	
	/**
	 * Sort a Map by its keys with the default Comparator. The type of 
	 * the keys of the Map has to be Comparable.<br>
	 * Note: Since a TreeMap is used, the returned map will stay 
	 * sorted forever
	 * @param map
	 * @param naturalOrder
	 * @return
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(
			Map<K, V> map, boolean naturalOrder) {
		Comparator<K> c = (a, b) -> a.compareTo(b);
		return sortByKey(map, naturalOrder ? c : c.reversed());
	}
	
	/**
	 * Invert a map
	 * @param map
	 * @return a new map whose keys are the values of the original 
	 * map, and the values are the keys of the original map.
	 */
	public static <K, V> Map<V, K> invert(Map<K, V> map) {
		Map<V, K> result = new HashMap<V, K>();
		map.forEach((k, v) -> result.put(v, k));
		return result;
	}
	
	/**
	 * Get a Map with Integer keys from a List
	 * @param list
	 * @return
	 */
	public static <V> Map<Integer, V> fromList(List<V> list) {
		Map<Integer, V> result = new TreeMap<Integer, V>(Integer::compare);
		int i = 0;
		for (V v : list) {
			result.put(i, v);
			i++;
		}
		return result;
	}
	
	/**
	 * Increase the value of a key by n. The type of the value must be 
	 * Integer.
	 * @param map
	 * @param key
	 * @param n
	 */
	public static <K> void increment(Map<K, Integer> map, K key, int n) {
		if (!map.containsKey(key)) {
			return;
		}
		map.put(key, map.get(key) + n);
	}

}
