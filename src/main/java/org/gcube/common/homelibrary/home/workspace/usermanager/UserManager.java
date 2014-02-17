/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.usermanager;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */
public interface UserManager {
	
	/**
	 * Create a new user 
	 * @param name of the new user
     * @param vre to which the user belongs
	 * @return true if it has been created
	 * @throws InternalErrorException
	 */
	public boolean createUser(String name) throws InternalErrorException;
	
	/**
	 * Get users
	 * @return a list of users
	 * @throws InternalErrorException
	 */
	public List<String> getUsers() throws InternalErrorException;
	
	
	/**
	 * Get groups
	 * @return a list of groups
	 * @throws InternalErrorException
	 */
	public List<GCubeGroup> getGroups() throws InternalErrorException;

	/**
	 * Return a group by the name
	 * @param name of the new group.
	 * @return a group.
	 * @throws InternalErrorException
	 */
	public GCubeGroup getGroup(String groupname) throws InternalErrorException;

	/**
	 * create a new group 
	 * @param name of the new group.
	 * @return true if it has been created
	 * @throws InternalErrorException
	 */
	public GCubeGroup createGroup(String groupName) throws InternalErrorException;

	/**
	 * Delete a group
	 * @param groupName
	 * @return true if it has been deleted
	 * @throws InternalErrorException
	 */
	public boolean deleteAuthorizable(String groupName) throws InternalErrorException;

	/**
	 * Associate a user with a scope group 
	 * @param scope
	 * @param username
	 * @return
	 * @throws InternalErrorException
	 */
	public boolean associateUserToGroup(String scope, String username) throws InternalErrorException;
	
	/**
	 * Remove a user from a scope group 
	 * @param scope
	 * @param username
	 * @return
	 * @throws InternalErrorException
	 */
	public boolean removeUserFromGroup(String scope, String username) throws InternalErrorException;
	



}
