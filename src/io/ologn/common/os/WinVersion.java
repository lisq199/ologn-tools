package io.ologn.common.os;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * An enum for verifying Windows versions easily. This enum 
 * exists because some tests involve screenshots of Windows 
 * UI elements and they are different in every version.<br>
 * Note: All the versions are in chronological order in 
 * order to compare which one is older or newer.
 * @author lisq199
 */
public enum WinVersion implements OsVersion<WinVersion> {
	
	/**
	 * Released to public on 2001-10-25.
	 */
	WIN_XP("Windows XP", "windows xp", null),
	/**
	 * Released to manufacturer on 2003-04-24. 
	 * Never released to public.
	 */
	WIN_2003("Windows Server 2003", "2003", null),
	/**
	 * Released to public on 2007-01-30.
	 */
	WIN_VISTA("Windows Vista", "vista", null),
	/**
	 * Released to public on 2008-02-27.
	 */
	WIN_2008("Windows Server 2008", "2008", "r2"),
	/**
	 * Released to public on 2009-10-22.
	 */
	WIN_2008_R2("Windows Server 2008 R2", "2008 r2", null),
	/**
	 * Windows 7 was released on 2009-10-22, the same day 
	 * Windows 2008 R2 was released. I'm arbitrarily making 
	 * it "newer" than Windows 2008 R2
	 */
	WIN_7("Windows 7", "windows 7", null),
	/**
	 * Released to public on 2012-09-04.
	 */
	WIN_2012("Windows Server 2012", "2012", "r2"),
	/**
	 * Released to public on 2012-10-26.
	 */
	WIN_8("Windows 8", "windows 8", "8.1"),
	/**
	 * Released to public on 2013-10-17.
	 */
	WIN_8_1("Windows 8.1", "windows 8.1", null),
	/**
	 * Released to public on 2013-10-18.
	 */
	WIN_2012_R2("Windows Server 2012 R2", "2012 r2", null),
	/**
	 * Released to public on 2015-07-29.
	 */
	WIN_10("Windows 10", "windows 10", null),
	/*
	 * TODO Add Windows 10.
	 * It's pretty much guaranteed that os.name on Windows 10 
	 * will be "Windows 10", but it may take some time before 
	 * Java supports it.
	 */
	/**
	 * OTHER means the PC is still running Windows, but it's 
	 * not one of the versions above. If the PC is not running 
	 * Windows, its WindowsVersion is null.
	 */
	OTHER("Other", null, null) {
		@Override
		public boolean isCurrent() {
			return getCurrent() == OTHER;
		}
	};
	
	/**
	 * An array containing all the Windows Servers.
	 */
	public static final WinVersion[] SERVER_VERSIONS = {
		WIN_2003, WIN_2008, WIN_2008_R2, WIN_2012, WIN_2012_R2
	};
	
	private final String toString;
	private final String inName;
	private final String notInName;
	
	/**
	 * Constructor.
	 * @param toString
	 * @param inName
	 * @param notInName
	 */
	WinVersion(String toString, String inName, String notInName) {
		this.toString = toString;
		this.inName = inName;
		this.notInName = notInName;
	}
	
	/**
	 * Get the current {@link WinVersion}.
	 * @return
	 */
	public static WinVersion getCurrent() {
		if (!OsName.WINDOWS.isCurrent()) {
			// Return null if the current OS is not Windows.
			return null;
		}
		// Only check mainstream versions to avoid stack overflow
		for (WinVersion version : getMainstreamVersions()) {
			if (version.isCurrent()) {
				return version;
			}
		}
		return OTHER;
	}
	
	/**
	 * Check if the current Windows version is one or one of 
	 * multiple specified {@link WinVersion}s.
	 * @param versions
	 * @return
	 */
	public static boolean currentIs(WinVersion... versions) {
		for (WinVersion version : versions) {
			if (version != null && version.isCurrent()) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Check if this is the current {@link WinVersion}.
	 * @return
	 */
	@Override
	public boolean isCurrent() {
		if (!OsName.WINDOWS.isCurrent() || inName == null) {
			return false;
		}
		String osNameLower = OlognOs.getOsName().toLowerCase();
		boolean isCurrent = osNameLower.contains(inName);
		if (notInName != null) {
			isCurrent = isCurrent && !osNameLower.contains(notInName);
		}
		return isCurrent;
	}
	
	/**
	 * Check if this was released before the specified 
	 * {@link WinVersion}.
	 * @param o
	 * @param strictly
	 * @return
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean before(WinVersion o, boolean strictly)
			throws UnsupportedOperationException {
		if (this == OTHER || o == OTHER) {
			throw new UnsupportedOperationException("OTHER version of "
					+ "Windows cannot be compared.");
		}
		return strictly ? compareTo(o) < 0 : compareTo(o) <= 0;
	}
	
	/**
	 * Check if this was released after the specified 
	 * {@link WinVersion}.
	 * @param o
	 * @param strictly
	 * @return
	 * @throws UnsupportedOperationException
	 */
	@Override
	public boolean after(WinVersion o, boolean strictly)
			throws UnsupportedOperationException {
		if (this == OTHER || o == OTHER) {
			throw new UnsupportedOperationException("OTHER version of "
					+ "Windows cannot be compared.");
		}
		return strictly ? compareTo(o) > 0 : compareTo(o) >= 0;
	}
	
	@Override
	public String toString() {
		return toString;
	}
	
	/**
	 * Get the "mainstream" {@link WinVersion}s by removing OTHER.
	 * @return
	 */
	public static WinVersion[] getMainstreamVersions() {
		List<WinVersion> versions = new LinkedList<WinVersion>(
				Arrays.asList(values()));
		versions.remove(OTHER);
		return versions.toArray(new WinVersion[0]);
	}
	
}
