/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.usermanager;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.exceptions.ItemNotFoundException;

/**
 * @author valentina
 *
 */
public interface UserManager {
	
	/**
	 * Create a new user (the password will be automatically generated)
	 * @param name of the new user
	 * @return true if it has been successfully created
	 * @throws InternalErrorException
	 */
	public boolean createUser(String name) throws InternalErrorException;
	
	/**
	 * Create a new user with password
	 * @param name of the new user
	 * @param pass of the new user
	 * @return true if it has been successfully created
	 * @throws InternalErrorException
	 */
	public boolean createUser(String name, String pass) throws InternalErrorException;
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
	 * @param groupname: name of the new group.
	 * @return a GCubeGroup 
	 * @throws InternalErrorException
	 */
	public GCubeGroup getGroup(String groupname) throws InternalErrorException;

	/**
	 * create a new group 
	 * @param groupName: name of the new group.
	 * @return true if it has been created
	 * @throws InternalErrorException
	 */
	public GCubeGroup createGroup(String groupName) throws InternalErrorException;

	/**
	 * Delete a group
	 * @param user: name of a group or user
	 * @return true if it has been successfully deleted
	 * @throws InternalErrorException
	 */
	public boolean deleteAuthorizable(String user) throws InternalErrorException;

	/**
	 * Associate a user with a scope group 
	 * 
	 * @param scope: VRE name
	 * @param username: an existing user
	 * @param administrator: the admin of the VRE/scope
	 * @return true if the uses has been successfully associated to the VRE group
	 * @throws InternalErrorException
	 * @throws ItemNotFoundException
	 */
	public boolean associateUserToGroup(String scope, String username, String administrator) throws InternalErrorException, ItemNotFoundException;
	
	/**
	 * Remove a user from a scope group 
	 * 
	 * @param scope: VRE name
	 * @param username: an existing user
	 * @param administrator: the admin of the VRE/scope
	 * @return true if the uses has been successfully removed to the VRE group
	 * @throws InternalErrorException
	 * @throws ItemNotFoundException
	 */
	public boolean removeUserFromGroup(String scope, String username, String administrator) throws InternalErrorException , ItemNotFoundException;
	
	/**
	 * Set Administrator to a folder
	 * 
	 * @param scope: VRE name
	 * @param username: an existing user
	 * @return
	 * @throws InternalErrorException
	 * @throws ItemNotFoundException
	 */
	public boolean setAdministrator(String scope, String username) throws InternalErrorException , ItemNotFoundException;
	
	/**
	 * Remove Administrator from a folder
	 * 
	 * @param scope: VRE name
	 * @param username: an existing user
	 * @return
	 * @throws InternalErrorException
	 * @throws ItemNotFoundException
	 */
	public boolean removeAdministrator(String scope, String username) throws InternalErrorException , ItemNotFoundException;



}
