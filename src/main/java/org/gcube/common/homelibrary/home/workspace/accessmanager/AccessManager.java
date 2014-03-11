/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.accessmanager;

import java.util.List;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */
public interface AccessManager {
	
//	/**
//	 * Get the permissions bound to a particular node.
//	 * @param absPath
//	 * @throws InternalErrorException
//	 */
//	public Map<String, List<String>> getACL(String absPath) throws InternalErrorException;
	/**
	 * Get the permissions which are effective for a particular node.
	 * @param absPath
	 * @throws InternalErrorException
	 */
	public Map<String, List<String>> getEACL(String absPath) throws InternalErrorException;
	
	/**
	 * Allow users to only read files.
	 * @param users
	 * @param absPath
	 * @throws InternalErrorException
	 */
	public void setReadOnlyACL(List<String> users, String absPath) throws InternalErrorException;
	/**
	 * 
	 * Allow users to create, edit and delete files of everyone in the share.
	 * @param users
	 * @param absPath
	 * @throws InternalErrorException
	 */
	public void setWriteOwnerACL(List<String> users, String absPath) throws InternalErrorException;
	/**
	 * Allow users to create, edit and delete files of everyone in the share.
	 * @param users
	 * @param absPath
	 * @throws InternalErrorException
	 */
	public void setWriteAllACL(List<String> users, String absPath) throws InternalErrorException;
	/**
	 * All privileges.
	 * @param users
	 * @param absPath
	 * @throws InternalErrorException
	 */
	public void setAdminACL(List<String> users, String absPath) throws InternalErrorException;

//	/**
//	 * Modify the permissions for a node
//	 * @param users
//	 * @param resourcePath
//	 * @param privilegesList
//	 * @param order
//	 * @throws InternalErrorException
//	 */
//	void modifyAce(List<String> users, String resourcePath,
//			List<String> privilegesList, String order)
//			throws InternalErrorException;
	/**
	 * Delete old Aces
	 * @param resourcePath
	 * @param principalNamesToDelete
	 * @return
	 * @throws InternalErrorException
	 * @throws PathNotFoundException 
	 */
	void deleteAces(String resourcePath, List<String> users)
			throws InternalErrorException;

}
