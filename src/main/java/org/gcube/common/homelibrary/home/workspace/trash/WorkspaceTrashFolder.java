/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.trash;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */
public interface WorkspaceTrashFolder {
		
		public List<String> emptyTrash() throws InternalErrorException;
		
		public List<String> restoreAll() throws InternalErrorException;
	}
