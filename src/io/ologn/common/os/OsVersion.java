package io.ologn.common.os;

/**
 * An interface for enums of OS versions.
 * @author sli
 */
public interface OsVersion<T> {

	/**
	 * Check if this is the current {@link OsVersion}.
	 * @return
	 */
	public boolean isCurrent();
	
	/**
	 * Check if this was released before the specified 
	 * {@link OsVersion}.
	 * @param o
	 * @param strictly
	 * @return
	 */
	public boolean before(T o, boolean strictly);
	
	/**
	 * Overloaded version of {@link #before(T, boolean)} 
	 * that checks if this was released strictly before the specified 
	 * {@link OsVersion}.
	 * @param o
	 * @return
	 */
	public default boolean before(T o) {
		return before(o, true);
	}
	
	/**
	 * Check if this was released after the specified 
	 * {@link OsVersion}.
	 * @param o
	 * @param strictly
	 * @return
	 */
	public boolean after(T o, boolean strictly);
	
	/**
	 * Overloaded version of {@link #after(OsVersion, boolean)} 
	 * that checks if this was released strictly after the specified 
	 * {@link OsVersion}.
	 * @param o
	 * @return
	 */
	public default boolean after(T o) {
		return after(o, true);
	}
	
}
