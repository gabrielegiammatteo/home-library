/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.usermanager;

import java.util.List;
import java.util.UUID;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */
public interface UserManager {
	
	/**
	 * Add a user 
	 * @param name of the new user.
	 * @return true if it has been created
	 * @throws InternalErrorException
	 */
	public boolean createUser(String name, String pass) throws InternalErrorException;
	
	/**
	 * Get users
	 * @return a list of users
	 * @throws InternalErrorException
	 */
	public List<String> getUsers() throws InternalErrorException;;
	
	
	/**
	 * Get groups
	 * @return a list of groups
	 * @throws InternalErrorException
	 */
	public List<GCubeGroup> getGroups() throws InternalErrorException;;

	/**
	 * Return a group by the name
	 * @param name of the new group.
	 * @return a group.
	 * @throws InternalErrorException
	 */
	public GCubeGroup getGroup(String group) throws InternalErrorException;;

	/**
	 * create a new group 
	 * @param name of the new group.
	 * @return true if it has been created
	 * @throws InternalErrorException
	 */
	public boolean createGroup(String groupName) throws InternalErrorException;

	/**
	 * Delete a group
	 * @param groupName
	 * @return true if it has been deleted
	 * @throws InternalErrorException
	 */
	public boolean deleteAuthorizable(String groupName) throws InternalErrorException;
	
	


}
