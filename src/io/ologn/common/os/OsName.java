package io.ologn.common.os;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * An enum for OS names to make it easier to verify OSes.<br>
 * Inspired by: <a href="http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/">
 * link</a>
 * @author lisq199
 */
public enum OsName {
	
	WINDOWS("Windows", "windows"),
	OS_X("OS X", "os x"),
	/**
	 * UNIX here means all the Unix-like OSes including Linux. 
	 * OS X is not considered to be UNIX here.
	 */
	UNIX("Unix", "nix", "nux", "aix", "bsd"),
	OTHER("Other") {
		@Override
		public boolean isCurrent() {
			return getCurrent() == OTHER;
		}
	};
	
	/**
	 * An array containing all the "mainstream" {@link OsName}s.
	 */
	public static final OsName[] MAINSTREAM_OS_NAMES = getMainstreamOsNames();
	
	private final String toString;
	private final String[] namesToCheck;
	
	/**
	 * Constructor.
	 * @param toString
	 * @param namesToCheck
	 */
	OsName(String toString, String... namesToCheck) {
		this.toString = toString;
		this.namesToCheck = namesToCheck;
	}
	
	/**
	 * Get the current {@link OsName}.
	 * @return
	 */
	public static OsName getCurrent() {
		for (OsName osName : MAINSTREAM_OS_NAMES) {
			if (osName.isCurrent()) {
				return osName;
			}
		}
		return OTHER;
	}
	
	/**
	 * Check if the current OS is one or one of multiple 
	 * specified {@link OsName}s.
	 * @param osNames
	 * @return
	 */
	public static boolean currentIs(OsName... osNames) {
		for (OsName osName : osNames) {
			if (osName != null && osName.isCurrent()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if this is the current OS
	 * @return
	 */
	public boolean isCurrent() {
		for (String name : namesToCheck) {
			if (OlognOs.getOsName().toLowerCase().contains(name)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public String toString() {
		return toString;
	}
	
	/**
	 * Get the "mainstream" {@link OsName}s by removing OTHER.
	 * @return
	 */
	private static OsName[] getMainstreamOsNames() {
		List<OsName> osNames = new LinkedList<OsName>(Arrays.asList(values()));
		osNames.remove(OTHER);
		return osNames.toArray(new OsName[0]);
	}
	
}
