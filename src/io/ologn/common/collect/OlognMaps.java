package io.ologn.common.collect;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.LinkedHashMap;
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
				new ArrayList<Map.Entry<K, V>>(map.entrySet());
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
			Map<K, V> map, boolean ascending) {
		Comparator<Map.Entry<K, V>> c = null;
		if (ascending) {
			c = (e1, e2) -> e1.getValue().compareTo(e2.getValue());
		} else {
			c = (e1, e2) -> e2.getValue().compareTo(e1.getValue());
		}
		return sortByValue(map, c);
	}

}
