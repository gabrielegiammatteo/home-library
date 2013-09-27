/**
 * 
 */
package org.gcube.common.homelibrary.util.accesslog;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public enum HLAccessLogEntryType {
	/**
	 * When a folder item is created.
	 */
	HL_FOLDER_ITEM_CREATED,
	
	/**
	 * When a folder item is removed.
	 */
	HL_FOLDER_ITEM_REMOVED,
	
	/**
	 * When a folder item is imported.
	 */
	HL_FOLDER_ITEM_IMPORTED,
	
	/**
	 * When one item is sent to one or more users.
	 */
	HL_ITEM_SENT,
	
	/**
	 * When a new workspace is created. 
	 */
	HL_WORKSPACE_CREATED;

}
