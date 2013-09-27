/**
 * 
 */
package org.gcube.common.homelibrary.home.data;

import java.util.List;

import org.gcube.common.homelibrary.home.data.application.ApplicationDataArea;
import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.WorkspaceFolder;


/**
 * Manage home application data.
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */

public interface ApplicationsArea {
	
	/**
	 * Return the application root folder.
	 * 
	 * @param applicationClass
	 * @return the application root folder.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	WorkspaceFolder getApplicationRoot(String applicationName) throws InternalErrorException;
	
	
	WorkspaceFolder getApplicationShareRoot(String applicationName) throws InternalErrorException;
	
	/**
	 * Retrieve the list of all application data areas.
	 * @return the application name list.
	 * @throws InternalErrorException if an error occurs.
	 */
	List<String> listApplicationNames() throws InternalErrorException;


	/**
	 * @param applicationName
	 * @return
	 * @throws InternalErrorException
	 */
	WorkspaceFolder getApplicationUserRoot(String applicationName)
			throws InternalErrorException;

	
}
