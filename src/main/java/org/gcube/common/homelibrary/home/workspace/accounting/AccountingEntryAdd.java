/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.accounting;

import org.gcube.common.homelibrary.home.workspace.WorkspaceItemType;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author Valentina Marioli valentina.marioli@isti.cnr.it
 *
 */
public interface AccountingEntryAdd extends AccountingEntry{
	
	WorkspaceItemType getItemType();
	
	FolderItemType getFolderItemType();
	
	String getItemName();
	
	String mimeType();
}
