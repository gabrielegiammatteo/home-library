package org.gcube.common.homelibrary.util;

import java.io.PrintStream;
import java.text.SimpleDateFormat;

import org.apache.log4j.Logger;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class IndentedVisitor {
	
	/**
	 * String used for indentation.
	 */
	protected static final String DEFAULT_INDENTATION_STRING = "\t";
	
	/**
	 * Date to string conversion format. 
	 */
	protected SimpleDateFormat sdf;

	protected String indentationLevel = "";
	protected String indentationChar;
	protected PrintStream os;
	protected Logger logger;

	/**
	 * Create a default visitor with output to console.
	 */
	public IndentedVisitor() {
		this("", DEFAULT_INDENTATION_STRING, System.out, null);
	}

	/**
	 * @param logger the visitor logger.
	 */
	public IndentedVisitor(Logger logger) {
		this("", DEFAULT_INDENTATION_STRING, null, logger);
	}

	/**
	 * @param indentationLevel the indentation level.
	 * @param indentationChar the indentation char.
	 * @param os the output stream.
	 * @param logger the visitor logger.
	 */
	public IndentedVisitor(String indentationLevel, String indentationChar,	PrintStream os, Logger logger) {
		this.indentationLevel = indentationLevel;
		this.indentationChar = indentationChar;
		this.os = os;
		this.logger = logger;
		sdf = new SimpleDateFormat("dd-MM-yyyy hh:mm:ss");
	}

	/**
	 * 
	 */
	public void reset()
	{
		indentationLevel = "";
	}
	
	protected void println() {
		println("");
	}

	protected void println(String s) {
		if (os!=null) os.println(indentationLevel+s);
		if (logger!=null) logger.trace(indentationLevel+s);
	}

	protected void indent() {
		indentationLevel += indentationChar;
	}

	protected void outdent() {
		indentationLevel = indentationLevel.substring(indentationChar.length());
	}

}