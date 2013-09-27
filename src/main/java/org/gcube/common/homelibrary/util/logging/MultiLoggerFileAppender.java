/**
 * 
 */
package org.gcube.common.homelibrary.util.logging;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.apache.log4j.FileAppender;
import org.apache.log4j.Layout;
import org.apache.log4j.spi.LoggingEvent;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class MultiLoggerFileAppender extends FileAppender {
	
	Map<String, List<LoggingEvent>> loggerEvents = new LinkedHashMap<String, List<LoggingEvent>>();
		

	/**
	 * 
	 */
	public MultiLoggerFileAppender() {
	}

	/**
	 * Create a multi logger appender.
	 * @param layout the logger layout.
	 * @param filename the destination file name.
	 * @param append true to append.
	 * @param bufferedIO true to use buffered io.
	 * @param bufferSize the buffer size.
	 * @throws IOException if an error occurs.
	 */
	public MultiLoggerFileAppender(Layout layout, String filename,	boolean append, boolean bufferedIO, int bufferSize)
			throws IOException {
		super(layout, filename, append, bufferedIO, bufferSize);
	}

	/**
	 * Create a multi logger appender.
	 * @param layout the logger layout.
	 * @param filename the destination file name.
	 * @param append true to append.
	 * @throws IOException if an error occurs.
	 */
	public MultiLoggerFileAppender(Layout layout, String filename,	boolean append) throws IOException {
		super(layout, filename, append);

	}

	/**
	 * Create a multi logger appender.
	 * @param layout the logger layout.
	 * @param filename the destination file name.
	 * @throws IOException if an error occurs.
	 */
	public MultiLoggerFileAppender(Layout layout, String filename)	throws IOException {
		super(layout, filename);

	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public synchronized void append(LoggingEvent event) {
		String loggerName = event.getThreadName();
		
		List<LoggingEvent> events;
		if (loggerEvents.containsKey(loggerName)) events = loggerEvents.get(loggerName);
		else{
			events = new LinkedList<LoggingEvent>();
			loggerEvents.put(loggerName, events);
		}
		
		events.add(event);
		
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void close() {
		for (List<LoggingEvent> events : loggerEvents.values()){
			for (LoggingEvent event:events) super.append(event);
		}
		
		super.close();
		
	}

}
