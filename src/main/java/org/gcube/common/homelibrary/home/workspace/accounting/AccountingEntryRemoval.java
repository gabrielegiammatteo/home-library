/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.accounting;

import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author gioia
 *
 */
public interface AccountingEntryRemoval extends AccountingEntry {

	WorkspaceItemType getItemType();
	
	FolderItemType getFolderItemType();
	
	String getItemName();
	
	String mimeType();
}
