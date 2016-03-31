package io.ologn.common.color;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.ArrayUtils;

import io.ologn.common.OlognHashCode;
import io.ologn.common.math.LinearScale;

/**
 * As the name suggests, this class is for storing categories of colors. 
 * Internally, colors are stored as CSS color strings (of any style), 
 * because they are designed to be used in HTML. The objects are 
 * supposed to be immutable once they are created.
 * @author lisq199
 *
 */
public class ColorCategory {
	
	/**
	 * Source: <a href="https://github.com/mbostock/d3/wiki/Ordinal-Scales">
	 * link</a>
	 */
	public static final String[] D3_CATEGORY10_STRINGS = {
		"#1f77b4", "#ff7f0e", "#2ca02c", "#d62728", "#9467bd",
		"#8c564b", "#e377c2", "#7f7f7f", "#bcbd22", "#17becf"
	};
	
	/**
	 * Source: <a href="https://github.com/mbostock/d3/wiki/Ordinal-Scales">
	 * link</a>
	 */
	public static final String[] D3_CATEGORY20_STRINGS = {
		"#1f77b4", "#aec7e8", "#ff7f0e", "#ffbb78", "#2ca02c",
		"#98df8a", "#d62728", "#ff9896", "#9467bd", "#c5b0d5",
		"#8c564b", "#c49c94", "#e377c2", "#f7b6d2", "#7f7f7f",
		"#c7c7c7", "#bcbd22", "#dbdb8d", "#17becf", "#9edae5"
	};

	/**
	 * Source: <a href="https://github.com/mbostock/d3/wiki/Ordinal-Scales">
	 * link</a>
	 */
	public static final String[] D3_CATEGORY20B_STRINGS = {
		"#393b79", "#5254a3", "#6b6ecf", "#9c9ede", "#637939",
		"#8ca252", "#b5cf6b", "#cedb9c", "#8c6d31", "#bd9e39",
		"#e7ba52", "#e7cb94", "#843c39", "#ad494a", "#d6616b",
		"#e7969c", "#7b4173", "#a55194", "#ce6dbd", "#de9ed6"
	};

	/**
	 * Source: <a href="https://github.com/mbostock/d3/wiki/Ordinal-Scales">
	 * link</a>
	 */
	public static final String[] D3_CATEGORY20C_STRINGS = {
		"#3182bd", "#6baed6", "#9ecae1", "#c6dbef", "#e6550d",
		"#fd8d3c", "#fdae6b", "#fdd0a2", "#31a354", "#74c476",
		"#a1d99b", "#c7e9c0", "#756bb1", "#9e9ac8", "#bcbddc",
		"#dadaeb", "#636363", "#969696", "#bdbdbd", "#d9d9d9"
	};
	
	public static final ColorCategory D3_CATEGORY10 = initWithColorStrings(
			D3_CATEGORY10_STRINGS);
	public static final ColorCategory D3_CATEGORY20 = initWithColorStrings(
			D3_CATEGORY20_STRINGS);
	public static final ColorCategory D3_CATEGORY20B = initWithColorStrings(
			D3_CATEGORY20B_STRINGS);
	public static final ColorCategory D3_CATEGORY20C = initWithColorStrings(
			D3_CATEGORY20C_STRINGS);
	
	public static final ColorCategory RED_TO_GREEN = initWithHueRange(
			0, 120, 100, 50, 1);
	public static final ColorCategory ORANGERED_TO_GREEN = initWithHueRange(
			16, 120, 100, 50, 1);
	
	public static final String JS_RANDOM_COLOR =
			"'rgb(' + (Math.floor(Math.random() * 256)) + ',' + "
			+ "(Math.floor(Math.random() * 256)) + ',' + "
			+ "(Math.floor(Math.random() * 256)) + ')'";
	
	/**
	 * The array storing all the color strings
	 */
	protected String[] colorStrings;
	
	protected ColorCategory() {}
	
	protected ColorCategory(String[] colorStrings) {
		this();
		this.colorStrings = colorStrings.clone();
	}
	
	/**
	 * Get all the colors in the current ColorCategory as a String array
	 * @return
	 */
	public String[] getColorStrings() {
		return colorStrings.clone();
	}
	
	/**
	 * Get the number of colors stored
	 * @return
	 */
	public int size() {
		return colorStrings.length;
	}
	
	/**
	 * Get the color at the nth position. Note: n can be bigger than the 
	 * number of color stored, because the index will be n % size.
	 * @param n
	 * @return
	 */
	public String getColor(long n) {
		// cast is safe because the result is less than size(), 
		// which is an int.
		int index = (int) (n % size());
		// Just in case. Normally this will not happen.
		if (index < 0) {
			index += size();
		}
		return new String(colorStrings[index]);
	}
	
	/**
	 * Get the color at a position. The position is the result of 
	 * applying the specified LinearScale to the specified number.
	 * @param n
	 * @param scale the scale will be ignored if it's set to null
	 * @return
	 */
	public String getColor(double n, LinearScale scale) {
		long index = Math.round(scale == null ? n : scale.apply(n));
		return getColor(index);
	}
	
	/**
	 * Get a LinearScale that scales the numbers to work with the 
	 * size of the current ColorCategory.
	 * @param domainMin the lower bound of the domain
	 * @param domainMax the upper bound of the domain
	 * @return
	 */
	public LinearScale getLinearScale(double domainMin, double domainMax) {
		return LinearScale.init()
				.setDomain(domainMin, domainMax)
				.setRange(0, this.size() - 1);
	}

	/**
	 * @return another ColorCategory object where the order of colors is 
	 * in reverse. This method does not modify the original object.
	 */
	public ColorCategory reverse() {
		String[] colors = this.getColorStrings();
		ArrayUtils.reverse(colors);
		return initWithColorStrings(colors);
	}
	
	@Override
	public int hashCode() {
		return OlognHashCode.init()
				.addArray(colorStrings)
				.get();
	}
	
	@Override
	public boolean equals(Object obj) {
		return OlognHashCode.equals(this, obj,
				(a, b) -> Arrays.equals(a.colorStrings, b.colorStrings));
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder("ColorCategory[");
		for (String color : getColorStrings()) {
			builder.append(color).append(", ");
		}
		builder.append("]");
		return builder.toString();
	}
	
	/**
	 * Initialize a ColorCategory object with a specified String 
	 * array containing all the color strings.
	 * @param colorStrings
	 * @return
	 */
	public static ColorCategory initWithColorStrings(String[] colorStrings) {
		return new ColorCategory(colorStrings);
	}
	
	/**
	 * Initialize a ColorCategory object with HSL color Strings with hue 
	 * ranging from start to finish. start doesn't have to be smaller than 
	 * finish. For example, if start = 5 and finish = 3, the resulting 
	 * ColorCategory will have "hsla(5,s,l,a)", "hsla(4,s,l,a)" and 
	 * "hsla(3,s,l,a)". 
	 * @param start
	 * @param finish
	 * @return
	 */
	public static ColorCategory initWithHueRange(int start, int finish,
			int s, int l, float a) {
		List<String> colors = new ArrayList<String>();
		if (start < finish) {
			for (int i = start; i < finish; i++) {
				colors.add(CssColorType.createHsla(a, i, s, l));
			}
		} else {
			for (int i = start; i > finish; i--) {
				colors.add(CssColorType.createHsla(a, i, s, l));
			}
		}
		return initWithColorStrings(colors.toArray(new String[0]));
	}
	
}
