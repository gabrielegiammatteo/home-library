/**
 * 
 */
package org.gcube.common.homelibrary.unittest.workspace;

import java.util.List;
import java.util.Map;

import org.gcube.common.homelibrary.home.exceptions.InternalErrorException;
import org.gcube.common.homelibrary.home.workspace.Workspace;

/**
 * @author Federico De Faveri defaveri@isti.cnr.it
 *
 */
public interface WorkspaceFactory {
	
	/**
	 * Setup the workspace are before each test.
	 * @return the under testing workspace.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public Workspace getWorkspace() throws InternalErrorException;
	
	/**
	 * Clean the workspace after each test.
	 * @throws InternalErrorException if an internal error occurs.
	 */
	public void cleanWorkspace() throws InternalErrorException;

	/**
	 * @return
	 * @throws InternalErrorException 
	 */
	public Map<String, Workspace> getTestWorkspaces() throws InternalErrorException; 
	
}
