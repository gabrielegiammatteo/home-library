/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.trash;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;

/**
 * @author valentina
 *
 */


public interface WorkspaceTrashItem extends FolderItem {
	
	void deletePermanently() throws InternalErrorException;
	
	void restore() throws InternalErrorException;
}