/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.groups;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

public interface Group {
	
	/**
	 * Return the group name.
	 * @return the group name.
	 */
	public String getName();
	
	/**
	 * Return the users of a given group.
	 * @return the users of a given group.
	 */
	public List<String> getUsers();
	
	/**
	 * Delete a user
	 * @param user
	 * @throws InternalErrorException
	 */
	public void deleteUser(String user) throws InternalErrorException;
	
	/**
	 * Delete a list of users
	 * @param users
	 * @throws InternalErrorException
	 */
	public void deleteUsers(List<String> users) throws InternalErrorException;
	
	/**
	 * Add a single user
	 * @param user
	 * @throws InternalErrorException
	 */
	public void addUser(String user) throws InternalErrorException;

	/**
	 * Add a list of users
	 * @param users
	 * @throws InternalErrorException
	 */
	public void addUsers(List<String> users) throws InternalErrorException;


}
