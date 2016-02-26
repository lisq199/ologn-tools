package io.ologn.common.collect;

import java.util.ArrayList;
import java.util.Comparator;
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
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map, boolean ascending) {
		Comparator<V> c = null;
		if (ascending) {
			c = (v1, v2) -> v1.compareTo(v2);
		} else {
			c = (v1, v2) -> v2.compareTo(v1);
		}
		return sortByValue(map, c);
	}
	
	/**
	 * Sort a Map by its keys with a specified Comparator.<br>
	 * Note: Since a TreeMap is used, the returned map will stay 
	 * sorted forever
	 * @param map
	 * @param c
	 * @return
	 */
	public static <K, V> Map<K, V> sortByKey(Map<K, V> map, Comparator<K> c) {
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
	 * @param ascending
	 * @return
	 */
	public static <K extends Comparable<? super K>, V> Map<K, V> sortByKey(
			Map<K, V> map, boolean ascending) {
		Comparator<K> c = null;
		if (ascending) {
			c = (k1, k2) -> k1.compareTo(k2);
		} else {
			c = (k1, k2) -> k2.compareTo(k1);
		}
		return sortByKey(map, c);
	}

}
