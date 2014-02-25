/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.search;

import java.util.Calendar;

import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;

/**
 * @author gioia
 *
 */
public interface SearchItem {

	/**
	 * 
	 * @return the item id
	 */
	public String getId();
	
	/**
	 * 
	 * @return the item name
	 */
	public String getName();
	
	/**
	 * 
	 * @return the item creation date
	 */	
	public Calendar getCreationDate();
	
	/**
	 * 
	 * @return the last modified date
	 */
	public Calendar getLastModified();
	
	/**
	 * 
	 * @return the owner of the item
	 */
	public String getOwner();
	
	/**
	 * 
	 * @return the item type
	 */
	public WorkspaceItemType getType();
	
	/**
	 * 
	 * @return true if the Folder is a VRE folder
	 */
	public boolean isVreFolder();
	
	/**
	 * 
	 * @return true if the item is shared
	 */
	public boolean isShared();
}
