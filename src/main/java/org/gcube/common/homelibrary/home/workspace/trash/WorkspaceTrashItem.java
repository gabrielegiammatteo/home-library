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
	 * @return
	 */
	String getOriginalParentId();
	
	/**
	 * @return
	 */
	String getOriginalPath();

	/**
	 * @return
	 */
	String getDeleteUser();

	/**
	 * @return
	 */
	Calendar getDeleteDate();

	/**
	 * @return
	 */
	boolean isFolder();


}