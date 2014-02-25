/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.search;

import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author gioia
 *
 */
public interface SearchFolderItem extends SearchItem {

	public FolderItemType getFolderItemType();
	
	public String getMimeType();
	
	public long getSize();

	
}
