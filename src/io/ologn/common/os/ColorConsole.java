package io.ologn.common.os;

import java.io.PrintStream;

/**
 * Enum for ANSI color escape codes.<br>
 * Note:<br>
 * 1. This does not work in Eclipse right now.<br>
 * 2. This enum is intended to be very simple. For a more advanced 
 * version, check out {@link AnsiSgrPrinter}.<br>
 * For example, to print some text in red, use 
 * {@code RED.println("red text")}.
 * @author lisq199
 */
public enum ColorConsole {
	
	BLACK("\u001B[30m"),
	RED("\u001B[31m"),
	GREEN("\u001B[32m"),
	YELLOW("\u001B[33m"),
	BLUE("\u001B[34m"),
	PURPLE("\u001B[35m"),
	CYAN("\u001B[36m"),
	WHITE("\u001B[37m");
	
	public static final String RESET = "\u001B[0m";
	
	private String escapeCode;
	
	ColorConsole (String escapeCode) {
		this.escapeCode = escapeCode;
	}
	
	/**
	 * Get the escape code of the current color.
	 * @return
	 */
	public String getEscapeCode() {
		return escapeCode;
	}
	
	@Override
	public String toString() {
		return getEscapeCode();
	}
	
	/**
	 * Create a String with the current color.
	 * @param s
	 * @return
	 */
	public String createColoredString(String s) {
		return this.getEscapeCode() + s + RESET;
	}
	
	/**
	 * Print a colored String with the current color to a 
	 * PrintStream using print() method
	 * @param ps
	 * @param s
	 */
	public void print(PrintStream ps, String s) {
		ps.print(createColoredString(s));
	}
	
	/**
	 * Print a colored String with the current color to  
	 * System.out using print() method
	 * @param s
	 */
	public void print(String s) {
		print(System.out, s);
	}

	/**
	 * Print a colored String with the current color to a 
	 * PrintStream using println() method
	 * @param ps
	 * @param s
	 */
	public void println(PrintStream ps, String s) {
		ps.println(createColoredString(s));
	}
	
	/**
	 * Print a colored String with the current color to  
	 * System.out using println() method
	 * @param s
	 */
	public void println(String s) {
		println(System.out, s);
	}

}
