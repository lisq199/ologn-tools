package io.ologn.common.os;

import java.io.PrintStream;

/**
 * For specifying styles when printing to console.<br>
 * For example, to print "Hello World" with blue foreground and white 
 * background, and with the word "World" blinking, use:<br>
 * {@code init().addCode(FG_BLUE, BG_WHITE).addString("Hello")
 * .addCode(BLINK_SLOW).addString(" World").addReset().println();}
 * <br>
 * Note:<br>
 * 1. This class does not work in Eclipse currently.<br>
 * 2. This class does not add reset automatically.
 * @author lisq199
 */
public class AnsiSgrPrinter {
	
	public static final int
			RESET = 0,
			BOLD = 1,
			FAINT = 2,
			UNDERLINE_SINGLE = 4,
			BLINK_SLOW = 5,
			BLINK_RAPID = 6,
			IMAGE_NEGATIVE = 7,
			CONCEAL = 8,
			CROSSED_OUT = 9,
			NORMAL_INTENSITY = 22,
			UNDERLINE_NONE = 24,
			BLINK_OFF = 25,
			IMAGE_POSITIVE = 27,
			REVEAL = 28,
			NOT_CROSSED_OUT = 29,
			FG_BLACK = 30,
			FG_RED = 31,
			FG_GREEN = 32,
			FG_YELLOW = 33,
			FG_BLUE = 34,
			FG_MAGENTA = 35,
			FG_CYAN = 36,
			FG_WHITE = 37,
			FG_DEFAULT = 39,
			BG_BLACK = 40,
			BG_RED = 41,
			BG_GREEN = 42,
			BG_YELLOW = 43,
			BG_BLUE = 44,
			BG_MAGENTA = 45,
			BG_CYAN = 46,
			BG_WHITE = 47,
			BG_DEFAULT = 49;
	
	public static final String ESC = "\033[";
	public static final char END = 'm';
	public static final char SEPARATOR = ';';
	public static final String RESET_STRING = ESC + RESET + END;
	
	protected StringBuilder builder;
	
	protected AnsiSgrPrinter() {
		this.builder = new StringBuilder();
	}
	
	/**
	 * Add a String with the current style to the output
	 * @param s
	 * @return
	 */
	public AnsiSgrPrinter addString(String s) {
		builder.append(s);
		return this;
	}
	
	/**
	 * Add one or multiple SGR codes. There are 
	 * pre-defined codes in this class, but you can always 
	 * add your own.
	 * @param codes
	 * @return
	 */
	public AnsiSgrPrinter addCode(int... codes) {
		boolean first = true;
		for (int code : codes) {
			if (first) {
				builder.append(ESC);
				first = false;
			} else {
				builder.append(SEPARATOR);
			}
			builder.append(code);
		}
		builder.append(END);
		return this;
	}
	
	/**
	 * Add an SGR reset code.
	 * @return
	 */
	public AnsiSgrPrinter addReset() {
		return addCode(RESET);
	}
	
	@Override
	public String toString() {
		return builder.toString();
	}
	
	/**
	 * Print the current result to a PrintStream with print()
	 */
	public void print(PrintStream ps) {
		ps.print(this);
	}
	
	/**
	 * Print the current result to System.out with print()
	 */
	public void print() {
		print(System.out);
	}

	/**
	 * Print the current result to a PrintStream with println()
	 */
	public void println(PrintStream ps) {
		ps.println(this);
	}

	/**
	 * Print the current result to System.out with println()
	 */
	public void println() {
		println(System.out);
	}
	
	/**
	 * Initialize
	 * @return
	 */
	public static AnsiSgrPrinter init() {
		return new AnsiSgrPrinter();
	}

}
