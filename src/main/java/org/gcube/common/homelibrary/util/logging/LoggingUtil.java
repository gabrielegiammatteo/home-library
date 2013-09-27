/**
 * 
 */
package org.gcube.common.homelibrary.util.logging;

import java.io.File;
import java.io.IOException;

import org.apache.log4j.Appender;
import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.Level;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class LoggingUtil {
	
	protected static Appender errorAppender;
	
	/**
	 * @param loggerFolder the logger folder.
	 * @param fileName the logger file name.
	 */
	public synchronized static void setupErrorAppender(File loggerFolder, String fileName)
	{
		File logFile = new File(loggerFolder,fileName);
		try{
			FileAppender faTrace = new FileAppender(new PatternLayout("%d{yyyy.MM.dd HH:mm:ss} %-5p [%c] [%t] - %m%n"), logFile.getAbsolutePath());
			faTrace.setThreshold(Level.ERROR);
			faTrace.activateOptions();
			errorAppender = faTrace;
		}catch(Exception e)
		{
			e.printStackTrace();
		}
		
	}
	
	/**
	 * Add a logger to error appender.
	 * @param logger the logger to add.
	 */
	public synchronized static void addErrorAppender(Logger logger)
	{
		if (errorAppender!=null){
			logger.addAppender(errorAppender);
		}
	}

	/**
	 * @param loggerFolder the logger folder.
	 * @param loggerName the logger name. 
	 * @return the configured logger.
	 */
	public static Logger getLogger(File loggerFolder, String loggerName)
	{
		Logger logger = Logger.getLogger(loggerName);
		logger.setLevel(Level.ALL);
		File logFile = new File(loggerFolder,loggerName+".log");
		try{
			FileAppender faTrace = new FileAppender(getSimpleLayout(), logFile.getAbsolutePath());
			faTrace.setThreshold(Level.ALL);
			faTrace.activateOptions();
			logger.addAppender(faTrace);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return logger;
	}
	
	/**
	 * @param loggerFolder the logger folder.
	 * @param fileName the logger file name.
	 * @param loggerName the logger name.
	 * @return the configured logger.
	 */
	public static Logger getLoggerMultiAppender(File loggerFolder, String fileName, String loggerName)
	{
		Logger logger = Logger.getLogger(loggerName);
		logger.setLevel(Level.ALL);
		File logFile = new File(loggerFolder,fileName+".log");
		try{
			MultiLoggerFileAppender faTrace = new MultiLoggerFileAppender(new PatternLayout("[%t] %d{yyyy.MM.dd HH:mm:ss} %-5p - %m%n"), logFile.getAbsolutePath());
			faTrace.setThreshold(Level.ALL);
			faTrace.activateOptions();
			logger.addAppender(faTrace);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return logger;
	}

	/**
	 * Get a logger with thread information in output.
	 * @param loggerFolder the logger folder.
	 * @param loggerName the logger name.
	 * @return the configured logger.
	 */
	public static Logger getLoggerThread(File loggerFolder, String loggerName)
	{
		Logger logger = Logger.getLogger(loggerName);
		logger.setLevel(Level.ALL);
		File logFile = new File(loggerFolder,loggerName+".log");
		try{
			FileAppender faTrace = new FileAppender(new PatternLayout("%t %d{yyyy.MM.dd HH:mm:ss} %-5p - %m%n"), logFile.getAbsolutePath());
			faTrace.setThreshold(Level.ALL);
			faTrace.activateOptions();
			logger.addAppender(faTrace);
		}catch(Exception e)
		{
			e.printStackTrace();
		}

		return logger;
	}

	/**
	 * @param loggerFolder the logger folder.
	 * @param loggerName the logger name. 
	 * @return the configured logger.
	 * @throws IOException if an error occurs.
	 */
	public static Logger getSimpleUniqueFileLogger(File loggerFolder, String loggerName) throws IOException
	{
		Logger logger = LogManager.exists(loggerName);

		if (logger == null){

			logger = Logger.getLogger(loggerName);

			logger.setLevel(Level.ALL);

			File loggingFile = new File(loggerFolder,loggerName+".log");

			FileAppender fa = new FileAppender(getSimpleLayout(), loggingFile.getAbsolutePath());
			fa.setThreshold(Level.ALL);

			logger.addAppender(fa);

			return logger;

		}

		return logger;
	}

	/**
	 * @return logger simple layout.
	 */
	public static Layout getSimpleLayout()
	{
		return new PatternLayout("%d{yyyy.MM.dd HH:mm:ss} %-5p - %m%n");
	}
	
	/**
	 * 
	 */
	public static void reconfigureLogging()
	{
		ConsoleAppender ca = new ConsoleAppender(new PatternLayout());
		ca.setThreshold(Level.ERROR);
		ca.activateOptions();
		BasicConfigurator.resetConfiguration();
		BasicConfigurator.configure(ca);
	}

}
