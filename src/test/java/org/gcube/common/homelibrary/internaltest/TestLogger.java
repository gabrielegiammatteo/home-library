/**
 * 
 */
package org.gcube.common.homelibrary.internaltest;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.SimpleLayout;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class TestLogger {

	/**
	 * @param args not used
	 * @throws Exception if an error occurs. 
	 */
	public static void main(String[] args) throws Exception {
		Logger logger = Logger.getLogger("test");
		logger.setLevel(Level.ALL);
		
		FileAppender fa = new FileAppender(new SimpleLayout(),"test.log");
		fa.setThreshold(Level.ERROR);
		fa.activateOptions();
		logger.addAppender(fa);
		
		logger.trace("trace");
		logger.debug("debug");
		logger.info("info");
		logger.warn("warn");
		logger.error("error");
		logger.fatal("fatal");
		logger.fatal("fatal e", new Exception("test"));
		

	}

}
