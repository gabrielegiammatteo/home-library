/**
 * 
 */
package org.gcube.portlets.user.homelibrary.internaltest;

import java.util.Enumeration;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.gcube.portlets.user.homelibrary.home.HomeLibrary;
import org.globus.util.log4j.PatternLayout;

/**
 * @author fedy2
 *
 */
public class TestLog4J {

	/**
	 * @param args not used.
	 * @throws Exception if an error occurs. 
	 */
	@SuppressWarnings("unchecked")
	public static void main(String[] args) throws Exception {
		
		
		Logger root = Logger.getRootLogger();
		Enumeration appenders = root.getAllAppenders();
		while(appenders.hasMoreElements()){
			Appender appender = (Appender) appenders.nextElement();
			System.out.println("Name "+appender.getName());
			System.out.println("Class "+appender.getClass().getCanonicalName());
			System.out.println();
		}
		
		ConsoleAppender ca = new ConsoleAppender(new PatternLayout());
		ca.setThreshold(Level.ERROR);
		ca.activateOptions();
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure(ca);
		
		HomeLibrary.getHomeManagerFactory();
		
		
		
		Logger logger = Logger.getLogger("TestLog4J");
		

		logger.fatal("fatal");
		logger.error("error");
		logger.info("info");
		logger.debug("debug");
		logger.trace("trace");

	}

}
