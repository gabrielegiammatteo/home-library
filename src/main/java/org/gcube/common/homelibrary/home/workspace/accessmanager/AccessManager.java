/**
 * 
 */
package org.gcube.common.homelibrary.home.workspace.accessmanager;

import java.util.List;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;

/**
 * @author valentina
 *
 */
public interface AccessManager {
	
	public List<String> getACL(String absPath) throws InternalErrorException;
	
	public List<String> getEACL(String absPath) throws InternalErrorException;
	
	public void modifyAce(String principalId, String absPath, List<String> privileges, String order) throws InternalErrorException;
}
