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

	public String getId();
	
	public String getName();
	
	public Calendar getCreationDate();
	
	public WorkspaceItemType getType();
}
