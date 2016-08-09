package io.ologn.common.os;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.Scanner;

import org.apache.commons.lang3.SystemUtils;

/**
 * Utilities for operating systems<br>
 * Inspired by: <a href="http://www.mkyong.com/java/how-to-detect-os-in-java-systemgetpropertyosname/">
 * link</a>
 * @author lisq199
 */
public class OlognOs {
	
	public static String getUserDesktop() {
		return new File(SystemUtils.USER_HOME, "Desktop").getAbsolutePath();
	}
	
	/**
	 * System property sun.arch.data.model.<br>
	 * This is not recommended by Oracle, and in Oracle's official 
	 * website, they literally said "shame on you" if you want 
	 * to write platform dependent code.<br>
	 * For more info, check out <a href="http://www.oracle.com/technetwork/java/hotspotfaq-138619.html#64bit_detection">
	 * Frequently Asked Questions About the Java HotSpot VM</a>
	 */
	public static String getSunArchDataModel() {
		return System.getProperty("sun.arch.data.model");
	}
	
	/**
	 * If the current OS is 32-bit (Shame on me!).
	 * @return
	 */
	public static boolean is32Bit() {
		return getSunArchDataModel() != null
				&& getSunArchDataModel().contains("32");
	}
	
	/**
	 * If the current OS is 64-bit (Shame on me!).
	 * @return
	 */
	public static boolean is64Bit() {
		return getSunArchDataModel() != null
				&& getSunArchDataModel().contains("64");
	}
	
	/**
	 * Execute one or multiple commands and print the result 
	 * to a PrintStream.<br>
	 * Inspired by: <a href="http://stackoverflow.com/questions/5711084/java-runtime-getruntime-getting-output-from-executing-a-command-line-program#answers-header">
	 * link</a>
	 * @param ps A PrintStream to print the result. If you don't 
	 * want the result printed, assign it to null.
	 * @param cmds
	 * @throws IOException 
	 */
	public static void executeCommands(PrintStream ps, String... cmds)
			throws IOException {
		if (cmds.length == 0) {
			// There are no commands to be executed
			return;
		}
		Runtime rt = Runtime.getRuntime();
		Process proc = null;
		/*
		 * If there is only one command, use rt.exec(String). 
		 * Else, use rt.exec(String[]).
		 */
		proc = cmds.length == 1 ? rt.exec(cmds[0]) : rt.exec(cmds);
		if (ps == null) {
			return;
		}
		try (
			// stdout
			InputStream stdoutStream = proc.getInputStream();
			Scanner stdoutScanner = new Scanner(stdoutStream);
			// stderr
			InputStream stderrStream = proc.getErrorStream();
			Scanner stderrScanner = new Scanner(stderrStream)
		) {
			// Print the result from stdout
			ps.println("<stdout>");
			while (stdoutScanner.hasNextLine()) {
				ps.println(stdoutScanner.nextLine());
			}
			ps.println("</stdout>");
			// Print the result from stderr
			ps.println("<stderr>");
			while (stderrScanner.hasNextLine()) {
				ps.println(stderrScanner.nextLine());
			}
			ps.println("</stderr>");
		}
	}
	
}
