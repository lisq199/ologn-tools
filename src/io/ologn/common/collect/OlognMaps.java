package io.ologn.common.collect;

import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Utilities for java.util.Map objects.
 * @author lisq199
 */
public class OlognMaps {
	
	/**
	 * Sort a map by its values with a specified Comparator.
	 * @param map
	 * @param c
	 * @return
	 */
	public static <K, V> Map<K, V> sortByValue(
			Map<K, V> map, Comparator<Map.Entry<K, V>> c) {
		List<Map.Entry<K, V>> list =
				new LinkedList<Map.Entry<K, V>>(map.entrySet());
		list.sort(c);
		Map<K, V> result = new LinkedHashMap<K, V>();
		for (Map.Entry<K, V> e : list) {
			result.put(e.getKey(), e.getValue());
		}
		return result;
	}
	
	/**
	 * Sort a Map by its values with the default Comparator. The type of 
	 * the values of the Map has to be Comparable.
	 * @param map
	 * @return
	 */
	public static <K, V extends Comparable<? super V>> Map<K, V> sortByValue(
			Map<K, V> map) {
		return sortByValue(map,
				(e1, e2) -> e1.getValue().compareTo(e2.getValue()));
	}

}
