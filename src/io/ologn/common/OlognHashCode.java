package io.ologn.common;

import java.util.Arrays;
import java.util.function.BiFunction;

/**
 * For easily generating hash codes.<br>
 * Source of algorithm: <a href="http://developer.android.com/reference/java/lang/Object.html">link</a>
 * <br>
 * The methods are pretty self-explanatory.<br>
 * Typical usage: {@code OlognHashCode.init().addInt(a).addArray(b).get();}
 * @author lisq199
 */
public class OlognHashCode {
	
	protected int result;
	protected int step;
	
	protected OlognHashCode() {
		result = 37;
		step = 31;
	}
	
	protected OlognHashCode(int start, int step) {
		this.result = start;
		this.step = step;
	}
	
	/**
	 * Get the final result
	 * @return
	 */
	public int get() {
		return result;
	}
	
	public OlognHashCode addBoolean(boolean f) {
		return addHash(f ? 1 : 0);
	}
	
	public OlognHashCode addByte(byte f) {
		return addHash(f);
	}
	
	public OlognHashCode addChar(char f) {
		return addHash(f);
	}
	
	public OlognHashCode addShort(short f) {
		return addHash(f);
	}
	
	public OlognHashCode addInt(int f) {
		return addHash(f);
	}
	
	public OlognHashCode addLong(long f) {
		return addHash((int) (f ^ (f >>> 32)));
	}
	
	public OlognHashCode addFloat(float f) {
		return addHash(Float.floatToIntBits(f));
	}
	
	public OlognHashCode addDouble(double f) {
		return addLong(Double.doubleToLongBits(f));
	}
	
	public OlognHashCode addArray(boolean[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(byte[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(char[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(short[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(int[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(long[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(float[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(double[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	public OlognHashCode addArray(Object[] f) {
		return addHash(Arrays.hashCode(f));
	}
	
	/**
	 * For deep primitive arrays and Object arrays.<br>
	 * Uses Arrays.deepHashCode() instead of Arrays.hashCode()
	 * @param f
	 * @return
	 */
	public OlognHashCode addDeepArray(Object[] f) {
		return addHash(Arrays.deepHashCode(f));
	}
	
	public OlognHashCode addObject(Object f) {
		return addHash(f == null ? 0 : f.hashCode());
	}
	
	/**
	 * If all the default methods don't work for you, you can always 
	 * add your own hash with this method.
	 * @param hash
	 * @return
	 */
	public OlognHashCode addHash(int hash) {
		result = step * result + hash;
		return this;
	}
	
	@Override
	public int hashCode() {
		return init()
				.addInt(result)
				.addInt(step)
				.get();
	}
	
	@Override
	public boolean equals(Object obj) {
		return defaultEquals(this, obj,
				(a, b) -> a.result == b.result
				&& a.step == b.step);
	}
	
	/**
	 * Initialize
	 * @return
	 */
	public static OlognHashCode init() {
		return new OlognHashCode();
	}
	
	/**
	 * Initialize with specified start and step values
	 * @param start the value the resulting hash code starts with
	 * @param step the value that is going to be multiplied each time 
	 * a new field is added.
	 * @return
	 */
	public static OlognHashCode init(int start, int step) {
		if (start == 0 || step == 0) {
			throw new IllegalArgumentException(
					"start and step must be non-zero");
		}
		return new OlognHashCode(start, step);
	}
	
	/**
	 * Default implementation of equals method.
	 * @param t the original object. Most of the time, it will be "this".
	 * @param obj the object that will be compared against
	 * @param check a function that takes 2 objects of type T and 
	 * returns a boolean. This is where the comparisons are made.
	 * @return
	 */
	public static <T>boolean defaultEquals(T t, Object obj,
			BiFunction<T, T, Boolean> check) {
		if (obj == null || !t.getClass().isAssignableFrom(obj.getClass())) {
			return false;
		}
		@SuppressWarnings("unchecked")
		final T other = (T) obj;
		return check.apply(t, other);
	}

}
