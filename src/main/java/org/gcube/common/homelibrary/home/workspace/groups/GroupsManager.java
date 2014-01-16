/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.groups;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */
public interface GroupsManager {
	
	/**
	 * Return a list of groups
	 * @return the name of the group.
	 */
	public List<Group> getGroups() throws InternalErrorException;;
	
	/**
	 * Return a group by the name
	 * @param name of the new group.
	 * @return a group.
	 */
	public Group getGroup(String group) throws InternalErrorException;;
		
	/**
	 * Add a group 
	 * @param name of the new group.
	 * @param a list of users.
	 * @return a group id.
	 */
	public String addGroup(String groupName, List<String> users) throws InternalErrorException;

	/**
	 * @param groupName
	 * @throws InternalErrorException
	 */
	void removeGroup(String groupName) throws InternalErrorException;;

}
