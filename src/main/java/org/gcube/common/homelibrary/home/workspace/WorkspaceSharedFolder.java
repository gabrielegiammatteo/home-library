/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace;

import java.util.List;

import org.gcube.common.homelibrary.home.User;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.accessmanager.ACLType;
import org.gcube.common.homelibrary.home.workspace.exceptions.InsufficientPrivilegesException;


/**
 * @author gioia
 *
 */
public interface WorkspaceSharedFolder extends WorkspaceFolder {
	
	/**
	 * @return the list of users. 
	 * @throws InternalErrorException
	 */
	List<String> getUsers() throws InternalErrorException;
	
	/**
	 * @param user
	 * @throws InsufficientPrivilegesException
	 * @throws InternalErrorException
	 */
	void addUser(String user) throws InsufficientPrivilegesException,
	InternalErrorException;
	
	/**
	 * @return a new {@link WorkspaceFolder}
	 * @throws InternalErrorException
	 */
	WorkspaceFolder unShare() throws InternalErrorException;
	
	/**
	 * @param user
	 * @return the shared folder name set by the user, null if the user doen't exist.
	 * @throws InternalErrorException
	 */
	String getName(String user) throws InternalErrorException;

	/**
	 * Set ACLs on shared folder
	 * @param users
	 * @param privilege
	 * @throws InternalErrorException
	 */
	void setACL(List<String> users, ACLType privilege)
			throws InternalErrorException;

}
