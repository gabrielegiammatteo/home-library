/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.trash;

import java.util.Calendar;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItem;
import org.gcube.common.homelibrary.home.workspace.folder.FolderItemType;

/**
 * @author valentina
 *
 */


public interface WorkspaceTrashItem extends FolderItem{
	
	/**
	 * Delete Permanently an item in the trash folder
	 * @throws InternalErrorException
	 */
	void deletePermanently() throws InternalErrorException;
	
	/**
	 * Restore an item in the trash folder
	 * @throws InternalErrorException
	 */
	void restore() throws InternalErrorException;

	/**
	 * Get original parent Id to restore the item
	 * @return
	 */
	String getOriginalParentId();
	
	/**
	 * Get original path
	 * @return
	 */
	String getOriginalPath();

	/**
	 * Get the name of the user who deleted the item
	 * @return
	 */
	String getDeleteUser();

	/**
	 * Get the date when the item was deleted
	 * @return
	 */
	Calendar getDeleteDate();

	/**
	 * Return true if the trash item was a folder
	 * @return
	 */
	boolean isFolder();


}