/**
 * 
 */
package org.gcube.common.homelibrary.util.accesslog;

import org.gcube.application.framework.accesslogger.model.AccessLogEntry;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public class HLAccessLogEntry extends AccessLogEntry {

	protected String message;

	/**
	 * Create an access log entry.
	 * @param type the entry type.
	 * @param message the entry message.
	 */
	public HLAccessLogEntry(HLAccessLogEntryType type, String message) {
		super(type.toString());
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getLogMessage() {
		return message;
	}

}
