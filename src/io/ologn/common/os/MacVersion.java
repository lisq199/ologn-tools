package io.ologn.common.os;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * An enum for verifying Mac versions easily. This enum 
 * exists because some tests involve screenshots of  
 * UI elements and they are different in every version.<br>
 * Note:<br>
 * 1. All the versions are in chronological order in 
 * order to compare which one is older or newer.<br>
 * 2. This enum only contains OS X versions that support 
 * Intel CPUs.
 * @author lisq199
 */
public enum MacVersion implements OsVersion<MacVersion> {
	
	OS_X_10_4("OS X 10.4", "Tiger", "10.4"),
	OS_X_10_5("OS X 10.5", "Leopard", "10.5"),
	OS_X_10_6("OS X 10.6", "Snow Leopard", "10.6"),
	OS_X_10_7("OS X 10.7", "Lion", "10.7"),
	OS_X_10_8("OS X 10.8", "Mountain Lion", "10.8"),
	OS_X_10_9("OS X 10.9", "Mavericks", "10.9"),
	OS_X_10_10("OS X 10.10", "Yosemite", "10.10"),
	OS_X_10_11("OS X 10.11", "El Capitan", "10.11"),
	OTHER("Other", "Other", null) {
		@Override
		public boolean isCurrent() {
			return getCurrent() == OTHER;
		}
	};
	
	private final String toString;
	public final String codeName;
	private final String osVersion;
	
	/**
	 * Constructor
	 * @param toString
	 * @param codeName
	 * @param osVersion
	 */
	MacVersion(String toString, String codeName, String osVersion) {
		this.toString = toString;
		this.codeName = codeName;
		this.osVersion = osVersion;
	}
	
	/**
	 * Get the current {@link MacVersion}.
	 * @return
	 */
	public static MacVersion getCurrent() {
		if (!OsName.OS_X.isCurrent()) {
			// Return null if the current OS is not OS X.
			return null;
		}
		// Only check mainstream versions to avoid stack overflow
		for (MacVersion version : getMainstreamVersions()) {
			if (version.isCurrent()) {
				return version;
			}
		}
		return OTHER;
	}
	
	/**
	 * Check if the current OS X version is one or one of 
	 * multiple specified {@link MacVersion}s.
	 * @param versions
	 * @return
	 */
	public static boolean currentIs(MacVersion... versions) {
		for (MacVersion version : versions) {
			if (version != null && version.isCurrent()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if this is the current {@link MacVersion}.
	 * @return
	 */
	@Override
	public boolean isCurrent() {
		if (!OsName.OS_X.isCurrent()) {
			return false;
		}
		/*
		 * Use startsWith instead of equals or contains to deal 
		 * with minor releases such as 10.10.1.
		 */
		return OlognOs.getOsVersion().toLowerCase().startsWith(osVersion);
	}
	
	/**
	 * Check if this was released before the specified 
	 * {@link MacVersion}.
	 * @param o
	 * @param strictly
	 * @return
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean before(MacVersion o, boolean strictly)
			throws UnsupportedOperationException {
		if (this == OTHER || o == OTHER) {
			throw new UnsupportedOperationException("OTHER version of "
					+ "OS X cannot be compared.");
		}
		return strictly ? compareTo(o) < 0 : compareTo(o) <= 0;
	}
	
	/**
	 * Check if this was released after the specified 
	 * {@link MacVersion}.
	 * @param o
	 * @param strictly
	 * @return
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean after(MacVersion o, boolean strictly)
			throws UnsupportedOperationException {
		if (this == OTHER || o == OTHER) {
			throw new UnsupportedOperationException("OTHER version of "
					+ "OS X cannot be compared.");
		}
		return strictly ? compareTo(o) > 0 : compareTo(o) >= 0;
	}
	
	@Override
	public String toString() {
		return toString;
	}
	
	/**
	 * Get the "mainstream" {@link MacVersion}s by removing OTHER.
	 * @return
	 */
	public static MacVersion[] getMainstreamVersions() {
		List<MacVersion> versions = new LinkedList<MacVersion>(
				Arrays.asList(values()));
		versions.remove(OTHER);
		return versions.toArray(new MacVersion[0]);
	}
	
}
