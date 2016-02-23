package io.ologn.common.color;

import io.ologn.common.function.TriFunction;

/**
 * Enum for CSS color string types, namely HEX, RGB, RGBA, HSL, and HSLA. 
 * It also has a lot of utility methods for identifying and converting CSS 
 * color strings. CSS does not support HSV, but there are methods for 
 * converting HSV colors.<br>
 * Assumed range:<br>
 * 1. The alpha of any type is a float in [0, 1].<br>
 * 2. Any component of RGB is an int in [0, 255].<br>
 * 3. H in HSL or HSV is an int in [0, 360]. S and L in HSL, 
 * and S and V in HSV is an int in [0, 100].<br>
 * Note:<br>
 * 1. This enum is only meant to manipulate CSS color strings. It may not 
 * work well with java.awt.Color objects.<br>
 * 2. Please provide valid CSS color strings as parameters. This enum does 
 * not reliably check the validity of the syntax.
 * @author lisq199
 */
public enum CssColorType {
	HEX("#", "") {
		@Override
		protected int[] getRgbArrayInternal(String color) {
			String c = color.trim();
			int[] rgb = new int[3];
			for (int i = 0; i < rgb.length; i++) {
				int index = i * 2 + 1;
				String val = c.substring(index, index + 2);
				rgb[i] = Integer.parseInt(val, 16);
			}
			return rgb;
		}

		@Override
		protected float getAlphaInternal(String color) {
			return 1;
		}

		@Override
		public String convert(String color) {
			int[] rgbArray = getRgb(color);
			return "#" + Integer.toHexString(rgbArray[0])
					+ Integer.toHexString(rgbArray[1])
					+ Integer.toHexString(rgbArray[2]);
		}
	},
	RGB("rgb", "rgba") {
		@Override
		protected int[] getRgbArrayInternal(String color) {
			return getIntArrayFromColorWithParen(color);
		}

		@Override
		protected float getAlphaInternal(String color) {
			return 1;
		}

		@Override
		public String convert(String color) {
			return createRgb(getRgb(color));
		}
	},
	RGBA("rgba", "") {
		@Override
		protected int[] getRgbArrayInternal(String color) {
			return getIntArrayFromColorWithParen(color);
		}

		@Override
		protected float getAlphaInternal(String color) {
			return parseColorWithParen(color, 3);
		}

		@Override
		public String convert(String color) {
			return createRgba(getAlpha(color), getRgb(color));
		}
	},
	HSL("hsl", "hsla") {
		@Override
		protected int[] getRgbArrayInternal(String color) {
			return getRgbArrayFromHslString(color);
		}
		
		@Override
		protected int[] getHslArrayInternal(String color) {
			return getIntArrayFromColorWithParen(color);
		}

		@Override
		protected float getAlphaInternal(String color) {
			return 1;
		}

		@Override
		public String convert(String color) {
			return createHsl(getHsl(color));
		}
	},
	HSLA("hsla", "") {
		@Override
		protected int[] getHslArrayInternal(String color) {
			return getIntArrayFromColorWithParen(color);
		}
		
		@Override
		protected int[] getRgbArrayInternal(String color) {
			return getRgbArrayFromHslString(color);
		}

		@Override
		protected float getAlphaInternal(String color) {
			return parseColorWithParen(color, 3);
		}

		@Override
		public String convert(String color) {
			return createHsla(getAlpha(color), getHsl(color));
		}
	};
	
	/**
	 * A String that the current type of CSS color string should start with 
	 */
	private String startsWith;

	/**
	 * A String that the current type of CSS color string should start without
	 */
	private String startsWithout;
	
	CssColorType(String startsWith, String startsWithout) {
		this.startsWith = startsWith;
		this.startsWithout = startsWithout;
	}
	
	protected abstract int[] getRgbArrayInternal(String color);
	
	protected int[] getHslArrayInternal(String color) {
		return rgbToHsl(getRgbArrayInternal(color));
	}
	
	protected abstract float getAlphaInternal(String color);
	
	/**
	 * Convert a CSS color string to the current CssColorType. For example, to 
	 * convert a CSS color string of any type called 'color' to HSLA, 
	 * use: {@code CssColorType.HSLA.convert(color);}<br>
	 * Note: Converting from a type with alpha to one without alpha will 
	 * lose the alpha information.
	 * @param color
	 * @return
	 */
	public abstract String convert(String color);
	
	/**
	 * Check if the current CssColorType is the type of a CSS color string
	 * @param color
	 * @return
	 */
	public boolean isTypeOf(String color) {
		String lower = color.toLowerCase();
		if (!startsWithout.isEmpty() && lower.startsWith(startsWithout)) {
			return false;
		}
		return lower.startsWith(startsWith);
	}
	
	/**
	 * Get the CssColorType of a CSS color string. If the syntax of the 
	 * color string is not valid or the type somehow cannot be determined, 
	 * return null.<br>
	 * Note: If the return value is not null, it doesn't mean the original 
	 * string is syntactically valid.
	 * @param color
	 * @return
	 */
	public static CssColorType getType(String color) {
		for (CssColorType type : CssColorType.values()) {
			if (type.isTypeOf(color)) {
				return type;
			}
		}
		return null;
	}
	
	/**
	 * Check if a CSS color string is one of the specified types
	 * @param color
	 * @param types
	 * @return
	 */
	public static boolean isType(String color, CssColorType... types) {
		for (CssColorType type : types) {
			if (type.isTypeOf(color)) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Create an RGB CSS color string
	 * @param rgb please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static String createRgb(int... rgb) {
		if (rgb.length < 3) {
			throw new IllegalArgumentException();
		}
		return "rgb(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ")";
	}
	
	/**
	 * Create an RGBA CSS color string
	 * @param alpha
	 * @param rgb please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static String createRgba(float alpha, int... rgb) {
		if (rgb.length < 3) {
			throw new IllegalArgumentException();
		}
		return "rgba(" + rgb[0] + "," + rgb[1] + "," + rgb[2] + ","
				+ alpha + ")";
	}
	
	/**
	 * Create an HSL CSS color string
	 * @param hsl please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static String createHsl(int... hsl) {
		if (hsl.length < 3) {
			throw new IllegalArgumentException();
		}
		return "hsl(" + hsl[0] + "," + hsl[1] + "%," + hsl[2] + "%)";
	}
	
	/**
	 * Create an HSLA CSS color string
	 * @param alpha
	 * @param hsl please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static String createHsla(float alpha, int... hsl) {
		if (hsl.length < 3) {
			throw new IllegalArgumentException();
		}
		return "hsla(" + hsl[0] + "," + hsl[1] + "%," + hsl[2] + "%,"
				+ alpha + ")";
	}
	
	/**
	 * Get an int array containing the extracted RGB values of a given CSS 
	 * color string of any type.
	 * @param color
	 * @return
	 */
	public static int[] getRgb(String color) {
		return getType(color).getRgbArrayInternal(color);
	}
	
	/**
	 * Get an int array containing the extracted HSL values of a given CSS 
	 * color string of any type.
	 * @param color
	 * @return
	 */
	public static int[] getHsl(String color) {
		return getType(color).getHslArrayInternal(color);
	}

	/**
	 * Get the alpha value from a CSS color string of any type
	 * @param color
	 * @return
	 */
	public static float getAlpha(String color) {
		return getType(color).getAlphaInternal(color);
	}
	
	/**
	 * Convert HSL to RGB.<br>
	 * Source of algorithm: <a href="http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c">link</a>
	 * @param hsl please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static int[] hslToRgb(int... hsl) {
		if (hsl.length < 3) {
			throw new IllegalArgumentException();
		}
		float r, g, b;
		float h = hsl[0] / 360f;
		float s = hsl[1] / 100f;
		float l = hsl[2] / 100f;
		if (s == 0) {
			r = g = b = 0;
		} else {
			TriFunction<Float, Float, Float, Float> hue2rgb = (p, q, t) -> {
				if (t < 0) t += 1;
	            if (t > 1) t -= 1;
	            if (t < 1f / 6f) return p + (q - p) * 6 * t;
	            if (t < 1f / 2f) return q;
	            if (t < 2f / 3f) return p + (q - p) * (2f / 3f - t) * 6;
				return p;
			};
			float q = l < 0.5f ? l * (1 + s) : l + s - l * s;
			float p = 2 * l - q;
			r = hue2rgb.apply(p, q, h + 1f / 3f);
			g = hue2rgb.apply(p, q, h);
			b = hue2rgb.apply(p, q, h - 1f / 3f);
		}
		return new int[] {Math.round(r * 255), Math.round(g * 255),
				Math.round(b * 255)};
	}
	
	/**
	 * Convert RGB to HSL.<br>
	 * Source of algorithm: <a href="http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c">link</a>
	 * @param rgb please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static int[] rgbToHsl(int... rgb) {
		if (rgb.length < 3) {
			throw new IllegalArgumentException();
		}
		float r = rgb[0] / 255f;
		float g = rgb[1] / 255f;
		float b = rgb[2] / 255f;
		float max = Math.max(r, g);
		max = Math.max(max, b);
		float min = Math.min(r, g);
		min = Math.min(min, b);
		float h = 0, s, l = (max + min) / 2;
		if (max == min) {
			h = s = 0;
		} else {
			float d = max - min;
			s = l > 0.5f ? d / (2 - max - min) : d / (max + min);
			if (max == r) {
				h = (g - b) / d + (g < b ? 6 : 0);
			} else if (max == g) {
				h = (b - r) / d + 2;
			} else { // max == b
				h = (r - g) / d + 4;
			}
			h /= 6;
		}
		return new int[] {Math.round(h * 360), Math.round(s * 100),
				Math.round(l * 100)};
	}
	
	/**
	 * Convert RGB to HSV.
	 * Source of algorithm: <a href="http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c">link</a>
	 * @param rgb please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static int[] rgbToHsv(int... rgb) {
		if (rgb.length < 3) {
			throw new IllegalArgumentException();
		}
		float r = rgb[0] / 255f;
		float g = rgb[1] / 255f;
		float b = rgb[2] / 255f;
		float max = Math.max(r, g);
		max = Math.max(max, b);
		float min = Math.min(r, g);
		min = Math.min(min, b);
		float h, s, v = max;
		float d = max - min;
		s = (max == 0) ? 0 : d / max;
		if (max == min) {
			h = 0;
		} else {
			if (max == r) {
				h = (g - b) / d + (g < b ? 6 : 0);
			} else if (max == g) {
				h = (b - r) / d + 2;
			} else { // max == b
				h = (r - g) / d + 4;
			}
			h /= 6;
		}
		return new int[] {Math.round(h * 360), Math.round(s * 100),
				Math.round(v * 100)};
	}
	
	/**
	 * Convert HSV to RGB.
	 * Source of algorithm: <a href="http://axonflux.com/handy-rgb-to-hsl-and-rgb-to-hsv-color-model-c">link</a>
	 * @param hsv please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static int[] hsvToRgb(int... hsv) {
		if (hsv.length < 3) {
			throw new IllegalArgumentException();
		}
		float h = hsv[0] / 360f;
		float s = hsv[1] / 100f;
		float v = hsv[2] / 100f;
		float r, g, b;
		int i = (int) Math.floor(h * 6);
		float f = h * 6 - i;
		float p = v * (1 - s);
		float q = v * (1 - f * s);
		float t = v * (1 - (1 - f) * s);
		switch (i % 6) {
		case 0:
			r = v;
			g = t;
			b = p;
			break;
		case 1:
			r = q;
			g = v;
			b = p;
			break;
		case 2:
			r = p;
			g = v;
			b = t;
			break;
		case 3:
			r = p;
			g = q;
			b = v;
			break;
		case 4:
			r = t;
			g = p;
			b = v;
			break;
		case 5:
			r = v;
			g = p;
			b = q;
			break;
		default:
			// This will never happen. It's here to silence a warning.
			r = g = b = 0;
			break;
		}
		return new int[] {Math.round(r * 255), Math.round(g * 255),
				Math.round(b * 255)};
	}
	
	/**
	 * Convert HSV to HSL
	 * @param hsv please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static int[] hsvToHsl(int... hsv) {
		return rgbToHsl(hsvToRgb(hsv));
	}
	
	/**
	 * Convert HSL to HSV
	 * @param hsv please provide 3 parameters or an array of length 3. 
	 * Extra parameters will be ignored.
	 * @return
	 */
	public static int[] hslToHsv(int... hsl) {
		return rgbToHsv(hslToRgb(hsl));
	}

	/**
	 * For internal use only. 
	 * Get the values of color strings with parenthesis, namely RGB, RGBA, 
	 * HSL, and HSLA.
	 * @param color
	 * @param n
	 * @return
	 */
	protected static float parseColorWithParen(String color, int n) {
		String s = color.trim();
		int leftParen = s.indexOf("(");
		int rightParen = s.indexOf(")");
		s = s.substring(leftParen + 1, rightParen);
		s = s.split(",")[n].trim();
		if (s.endsWith("%")) {
			s = s.substring(0, s.length() - 1);
		}
		return Float.parseFloat(s);
	}
	
	/**
	 * For internal use only. 
	 * Extract the first 3 int values of a color string with parenthesis. 
	 * For example, applying this method to "hsla(100, 100%, 50%, 0.5)" 
	 * will return [100, 100, 50].
	 * @param color
	 * @return
	 */
	protected static int[] getIntArrayFromColorWithParen(String color) {
		int[] rgbArray = new int[3];
		for (int i = 0; i < rgbArray.length; i++) {
			rgbArray[i] = (int) parseColorWithParen(color, i);
		}
		return rgbArray;
	}
	
	/**
	 * For internal use only. 
	 * Get the RGB values as an array from an HSL or HSLA string.
	 * @param color
	 * @return
	 */
	protected static int[] getRgbArrayFromHslString(String color) {
		return hslToRgb(getIntArrayFromColorWithParen(color));
	}
	
	/**
	 * For internal use only. 
	 * Get the HSL values as an array from an RGB or RGBA string.
	 * @param color
	 * @return
	 */
	protected static int[] getHslArrayFromRgbString(String color) {
		return rgbToHsl(getIntArrayFromColorWithParen(color));
	}
	
}
